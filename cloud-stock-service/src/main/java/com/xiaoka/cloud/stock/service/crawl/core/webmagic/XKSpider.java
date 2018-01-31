package com.xiaoka.cloud.stock.service.crawl.core.webmagic;

import org.apache.commons.lang3.SerializationUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Do something
 *
 * @author gancao 2018/1/3 14:32
 * @see [相关类/方法]
 * @since [版本号]
 */
public class XKSpider extends Spider {

	private ReentrantLock newUrlLock = new ReentrantLock();

	private Condition newUrlCondition = newUrlLock.newCondition();

	private final AtomicLong pageCount = new AtomicLong(0);

	private static final int emptySleepTime = 30000;

	public XKSpider(PageProcessor pageProcessor) {
		super(pageProcessor);
	}

	public static XKSpider create(PageProcessor pageProcessor) {
		return new XKSpider(pageProcessor);
	}

	@Override
	public void run() {
		checkRunningStat();
		initComponent();
		logger.info("Spider {} started!",getUUID());
		while (!Thread.currentThread().isInterrupted() && stat.get() == STAT_RUNNING) {
			final Request request = scheduler.poll(this);
			if (request == null) {
				if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
					break;
				}
				// wait until new url added
				waitNewUrl();
			} else {
				threadPool.execute(() -> {
					try {
						processRequest(request);
						onSuccess(request);
					} catch (Exception e) {
						onError(request);
						logger.error("process request " + request + " error", e);
					} finally {
						pageCount.incrementAndGet();
						signalNewUrl();
					}
				});
			}
		}
		stat.set(STAT_STOPPED);
		// release some resources
		if (destroyWhenExit) {
			close();
		}
		logger.info("Spider {} closed! {} pages downloaded.", getUUID(), pageCount.get());
	}

	private void waitNewUrl() {
		newUrlLock.lock();
		try {
			//double check
			if (threadPool.getThreadAlive() == 0 && exitWhenComplete) {
				return;
			}
			newUrlCondition.await(emptySleepTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.warn("waitNewUrl - interrupted, error {}", e);
		} finally {
			newUrlLock.unlock();
		}
	}

	private void signalNewUrl() {
		try {
			newUrlLock.lock();
			newUrlCondition.signalAll();
		} finally {
			newUrlLock.unlock();
		}
	}

	private void checkRunningStat() {
		while (true) {
			int statNow = stat.get();
			if (statNow == STAT_RUNNING) {
				throw new IllegalStateException("Spider is already running!");
			}
			if (stat.compareAndSet(statNow, STAT_RUNNING)) {
				break;
			}
		}
	}

	private void processRequest(Request request) {
		Page page = downloader.download(request, this);
		if (page.isDownloadSuccess()){
			onDownloadSuccess(request, page);
		} else {
			onDownloaderFail(request);
		}
	}

	private void onDownloadSuccess(Request request, Page page) {
		if (site.getAcceptStatCode().contains(page.getStatusCode())){
			pageProcessor.process(page);
			extractAndAddRequests(page, spawnUrl);
			if (!page.getResultItems().isSkip()) {
				for (Pipeline pipeline : pipelines) {
					pipeline.process(page.getResultItems(), this);
				}
			}
		} else {
			logger.info("page status code error, page {} , code: {}", request.getUrl(), page.getStatusCode());
		}
		/**
		 * 原生Spider的sleepTime是固定的不能设置随机，此处暂时设置固定随机时间，后面如果需要将WebMagic重新组件化这块再重新设计
		 */
		sleep(new Random().nextInt(3000) + 5000);//设置随机的休眠时间5s-8s);
	}

	private void onDownloaderFail(Request request) {
		if (site.getCycleRetryTimes() == 0) {
			sleep(site.getSleepTime());
		} else {
			// for cycle retry
			doCycleRetry(request);
		}
	}

	private void doCycleRetry(Request request) {
		Object cycleTriedTimesObject = request.getExtra(Request.CYCLE_TRIED_TIMES);
		if (cycleTriedTimesObject == null) {
			addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, 1));
		} else {
			int cycleTriedTimes = (Integer) cycleTriedTimesObject;
			cycleTriedTimes++;
			if (cycleTriedTimes < site.getCycleRetryTimes()) {
				addRequest(SerializationUtils.clone(request).setPriority(0).putExtra(Request.CYCLE_TRIED_TIMES, cycleTriedTimes));
			}
		}
		sleep(site.getRetrySleepTime());
	}
}

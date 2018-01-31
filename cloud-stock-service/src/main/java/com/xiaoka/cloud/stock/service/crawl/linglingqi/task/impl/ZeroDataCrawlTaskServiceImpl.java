package com.xiaoka.cloud.stock.service.crawl.linglingqi.task.impl;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.core.downloader.XKHttpClientDownloader;
import com.xiaoka.cloud.stock.service.crawl.core.processor.ZeroSevenProcessor;
import com.xiaoka.cloud.stock.service.crawl.core.util.ZeroSevenLoginUtil;
import com.xiaoka.cloud.stock.service.crawl.core.webmagic.XKSpider;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.ZeroDataCrawlTaskService;
import com.xiaoka.freework.container.context.ContainerContext;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.platform.api.tool.sms.message.SendMessageService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Do something
 *
 * @author gancao 2017/12/28 17:48
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
//@EnableScheduling
public class ZeroDataCrawlTaskServiceImpl implements ZeroDataCrawlTaskService {

	private static final Logger logger = LoggerFactory.getLogger(ZeroDataCrawlTaskService.class);
	private volatile boolean isRun;

	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	SendMessageService sendMessageService;

	//@Scheduled(fixedRate = 1000 * 60 * 60)   //每1小时执行一次
	@Override
	public void crawlTask() {
		if (isRun) {
			throw new ApiException("-1", "正在执行007数据爬取，请勿重复任务");
		}
		List<ZeroAccountEntity> zeroAccountEntityList = zeroChooseService.getZeroProxyAccountEntityList();
		if (CollectionUtils.isEmpty(zeroAccountEntityList)) {
			return;
		}
		ExecutorService executorService = Executors.newFixedThreadPool(zeroAccountEntityList.size());
		isRun = true;
		zeroAccountEntityList.forEach(entity -> executorService.execute(() -> this.crawlTask(entity)));
		executorService.shutdown();
		try {
			executorService.awaitTermination(16, TimeUnit.HOURS);//16小时后结束
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		isRun = false;
	}

	@Override
	public void crawlTask(ZeroAccountEntity accountEntity) {
		executeAccount(accountEntity, null);
	}

	private void executeAccount(ZeroAccountEntity accountEntity, String realIp){
		logger.info("分配的帐号:{}", Jackson.mobile().writeValueAsString(accountEntity));
		List<ZeroCarModelChooseEntity> chooseEntityList = this.getNoSuccessCarModelListByPhone(accountEntity.getPhone());
		/**
		 * WebMagic的Spider使用了线程所以不能用线程变量，只能用多例的方式
		 */
		ZeroSevenProcessor zeroSevenProcessor = (ZeroSevenProcessor)ContainerContext.get().getContext().getBean("zeroSevenProcessor");
		if (CollectionUtils.isNotEmpty(chooseEntityList)) {
			logger.info("该帐号待爬取车型数量:{}", chooseEntityList.size());
			Header[] headers = ZeroSevenLoginUtil.singleInstance().getHeaders(false, accountEntity);
			isRun = true;
			for (ZeroCarModelChooseEntity chooseEntity : chooseEntityList) {
				try {
					if (!isValidTime()) {
						//当前车型爬取时间无效
						break;
					}
					if (Objects.nonNull(chooseEntity.getAmount()) && chooseEntity.getAmount() > 2 && zeroChooseService.countSubGroup(chooseEntity.getId()) > 0) {
						//该车型已爬取3次，状态进行变更
						chooseEntity.setSuccessStatus(1);
					} else {
						if (ArrayUtils.isEmpty(headers)) {
							headers = ZeroSevenLoginUtil.singleInstance().getHeaders(false, accountEntity);
						}
						if (ArrayUtils.isEmpty(headers)){
							this.sendEmailMessage(accountEntity, 1);//帐号或者IP异常
							break;
						}
						/*if (!checkAccount(headers)){
							this.sendEmailMessage(accountEntity, 2);//帐号没有付费
							//帐号付费信息是否有效,帐号无效结束循环
							logger.error("当前帐号:{}尚未付费", accountEntity.getPhone());
							break;
						}*/
						this.executeCarModelTask(accountEntity, chooseEntity, headers, realIp, zeroSevenProcessor);
						chooseEntity.setAmount(Objects.nonNull(chooseEntity.getAmount()) ? chooseEntity.getAmount() + 1 : 1);
						if (!Objects.equals(1, chooseEntity.getSuccessStatus()) && zeroChooseService.isComplete(chooseEntity.getId())) {
							//判断车型是否已完成
							logger.info("cId:{}车型爬取已完成", chooseEntity.getId());
							chooseEntity.setSuccessStatus(1);
						} else {
							logger.info("cId:{}车型爬取未完成", chooseEntity.getId());
						}
					}
					zeroChooseService.updateZeroCarModelChooseEntity(chooseEntity);
					//随机休眠3-5分钟
					long time = new Random().nextInt(1000*60*2) + 1000*60*3;
					logger.info("车型完成随机休眠的时间:{}", time);
					Thread.sleep(time);
				} catch (Exception e) {
					logger.error("执行车型爬取失败", e);
				}
			}
			this.complete(accountEntity);//爬取结束邮件通知
		}else {
			logger.error("暂无可爬取的车型");
		}
	}

	private void executeCarModelTask(ZeroAccountEntity entity, ZeroCarModelChooseEntity chooseEntity, Header[] headers, String realIp, ZeroSevenProcessor zeroSevenProcessor) {
		if (Objects.nonNull(headers)) {
			logger.info("登录成功开始执行数据抓取:抓取帐号{}", Jackson.mobile().writeValueAsString(entity));
			logger.info("开始爬取车型:{}", Jackson.mobile().writeValueAsString(chooseEntity));
			zeroSevenProcessor.init(entity, headers, chooseEntity);
			String ip = StringUtils.isNotBlank(entity.getIp()) && Objects.nonNull(entity.getPort()) ? entity.getIp()+ ":" + entity.getPort() : null;
			XKHttpClientDownloader httpClientDownloader = new XKHttpClientDownloader(entity.getPhone(), ip, realIp);
			if (StringUtils.isNotBlank(entity.getIp()) && Objects.nonNull(entity.getPort())) {
				httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(new Proxy(entity.getIp(), entity.getPort())));
			}
			Spider spider = XKSpider.create(zeroSevenProcessor);
			spider.setDownloader(httpClientDownloader);
			spider.addUrl("https://www.007vin.com/cars/show?brand=maserati");
			spider.thread(1);
			spider.run();
		} else {
			logger.error("帐号:{},请求header头为空", Jackson.mobile().writeValueAsString(entity));
		}
	}

	private List<ZeroCarModelChooseEntity> getNoSuccessCarModelListByPhone(String phone) {
		ZeroCarModelChooseEntity param = new ZeroCarModelChooseEntity();
		param.setPhone(phone);
		//未执行的
		param.setSuccessStatus(0);
		param.setIsValid(1);
		return zeroChooseService.getZeroCarModelChooseEntityList(param);
	}

	/**
	 * 007的有效数据爬取时间为早上6点到晚上10点
	 *
	 * @return
	 */
	private static boolean isValidTime() {
		Calendar cal = Calendar.getInstance();
		int hour = cal.get(Calendar.HOUR_OF_DAY);
		logger.info("当前任务时间:{}", hour);
		return hour > 5 && hour < 23;
	}

	private void sendEmailMessage(ZeroAccountEntity accountEntity, int type){
		String errMailContent = "帐号:" + accountEntity.getPhone();
		if (StringUtils.isNotBlank(accountEntity.getIp()) && Objects.nonNull(accountEntity.getPort())){
			errMailContent += ",代理IP:" + accountEntity.getIp() + ":" + accountEntity.getPort();
		}
		if (type == 1){
			errMailContent += ",帐号或者IP异常请检查";
		}else if(type == 2){
			errMailContent += ",帐号尚未付费请及时续费";
		}
		String emailPath = "gancao@xiaokakeji.com," +
				"sabo@xiaokakeji.com," +
				"chuhan@xiaokakeji.com,"+
				"gangbeng@xiaokakeji.com";
		String titleStr = "007爬取失败！";
		String level = "紧急";
		boolean result = sendMessageService.sendMail(emailPath, titleStr, level, errMailContent, null);
		if (!result){
			logger.error("邮件发送失败");
		}
	}

	@Override
	public void complete(ZeroAccountEntity accountEntity){
		String errMailContent = "SOA爬取已结束，爬取帐号:"+Jackson.mobile().writeValueAsString(accountEntity)+",请检查数据判断是否需要重新执行SOA方法";
		String emailPath = "gancao@xiaokakeji.com," +
				"sabo@xiaokakeji.com," +
				"chuhan@xiaokakeji.com,"+
				"gangbeng@xiaokakeji.com";
		String titleStr = "007爬取已结束！";
		String level = "紧急";
		boolean result = sendMessageService.sendMail(emailPath, titleStr, level, errMailContent, null);
		if (!result){
			logger.error("邮件发送失败");
		}
	}

}

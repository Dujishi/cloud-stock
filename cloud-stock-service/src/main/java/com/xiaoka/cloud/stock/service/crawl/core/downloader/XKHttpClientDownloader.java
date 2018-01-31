package com.xiaoka.cloud.stock.service.crawl.core.downloader;

import com.xiaoka.freework.help.log.DistributedLogger;
import com.xiaoka.freework.utils.Utils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.HttpClientDownloader;

import java.util.HashMap;
import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2018/1/4 13:29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class XKHttpClientDownloader extends HttpClientDownloader {

	private String source;
	private String ip;
	private String realIp;

	private static final String LOG_TYPE_CRAWL = "crawl";
	private static final String LOG_TYPE_URL = "url";//爬取的地址
	private static final String LOG_TYPE_IP = "ip";//爬取ip
	private static final String LOG_TYPE_REAL_IP = "realIp";//真实ip
	private static final String LOG_TYPE_SOURCE = "source";//爬取来源
	private static final String LOG_TYPE_EXEC_TIME= "execTime";//请求执行时间

	public XKHttpClientDownloader(String source, String ip, String realIp) {
		this.source = source;
		this.ip = ip;
		this.realIp = realIp;
	}

	@Override
	public Page download(Request request, Task task) {
		long start = System.currentTimeMillis();
		Page page =  super.download(request, task);
		logTrace(request, start);
		return page;
	}

	private void logTrace(Request request, long start){
		Map<String, Object> fields = new HashMap<>();
		fields.put(LOG_TYPE_URL, request.getUrl());
		fields.put(LOG_TYPE_IP, this.ip);
		fields.put(LOG_TYPE_REAL_IP, this.realIp);
		fields.put(LOG_TYPE_SOURCE, this.source);
		fields.put(LOG_TYPE_EXEC_TIME, System.currentTimeMillis() - start);
		Utils.get(DistributedLogger.class).log(LOG_TYPE_CRAWL, fields);
	}
}

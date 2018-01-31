package com.xiaoka.cloud.stock.service.crawl.core.downloader;

import org.apache.http.impl.client.CloseableHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.downloader.HttpClientGenerator;
import us.codecraft.webmagic.downloader.HttpUriRequestConverter;
import us.codecraft.webmagic.proxy.ProxyProvider;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Do something
 *
 * @author gancao 2018/1/4 14:05
 * @see [相关类/方法]
 * @since [版本号]
 */
public class BaseHttpClientDownloader extends HttpClientDownloader {

	Logger logger = LoggerFactory.getLogger(getClass());

	Map<String, CloseableHttpClient> httpClients = new ConcurrentHashMap<>();

	HttpClientGenerator httpClientGenerator = new HttpClientGenerator();

	HttpUriRequestConverter httpUriRequestConverter = new HttpUriRequestConverter();

	ProxyProvider proxyProvider;

	boolean responseHeader = true;

	CloseableHttpClient getHttpClient(Site site) {
		if (site == null) {
			return httpClientGenerator.getClient(null);
		}
		String domain = site.getDomain();
		CloseableHttpClient httpClient = httpClients.get(domain);
		if (httpClient == null) {
			synchronized (this) {
				httpClient = httpClients.get(domain);
				if (httpClient == null) {
					httpClient = httpClientGenerator.getClient(site);
					httpClients.put(domain, httpClient);
				}
			}
		}
		return httpClient;
	}
}

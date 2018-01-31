package com.xiaoka.cloud.stock.service.crawl.core.processor;

import com.xiaoka.cloud.stock.service.crawl.core.webmagic.XKSpider;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Do something
 *
 * @author gancao 2018/1/3 18:35
 * @see [相关类/方法]
 * @since [版本号]
 */
public class BaiduPageProcessor implements PageProcessor {
	@Override
	public void process(Page page) {
		System.out.println(format(new Date(), "yyyy-MM-dd HH:mm:ss"));
		System.out.println(page.getUrl().get());
		if (page.getUrl().get().equals("http://news.baidu.com/")){
			page.addTargetRequest("http://news.baidu.com/guonei");
		}else if (page.getUrl().get().contains("guonei")){
			page.addTargetRequest("http://news.baidu.com/guoji");
		}else if (page.getUrl().get().contains("guoji")){
			page.addTargetRequest("http://news.baidu.com/mil");
		}
	}

	@Override
	public Site getSite() {
		int sleepTime = new Random().nextInt(3000) + 5000;//设置随机的休眠时间5s-8s
		Site site = Site.me().setRetryTimes(3).setSleepTime(sleepTime).setTimeOut(10000);
		System.out.println(site.getSleepTime());
		return site;
	}

	public static void main(String[] args) {
		XKSpider.create(new BaiduPageProcessor()).addUrl("http://news.baidu.com/").thread(1).run();
	}

	private String format(Date date, String pattern) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(pattern);
		return dateFormat.format(date);
	}
}

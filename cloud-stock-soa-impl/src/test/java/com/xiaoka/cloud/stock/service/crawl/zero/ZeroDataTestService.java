package com.xiaoka.cloud.stock.service.crawl.zero;

import com.xiaoka.cloud.stock.soa.api.crawl.ZeroDataCrawlerSoaService;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Collections;

/**
 * Do something
 *
 * @author gancao 2017/12/18 10:40
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml",
		"classpath*:spring/tool-service.xml"
})
public class ZeroDataTestService extends ContainerTest{

	@Resource
	private ZeroDataCrawlerSoaService zeroDataCrawlerSoaService;

	@Test
	public void zeroData(){
		zeroDataCrawlerSoaService.crawlAllData(Collections.singletonList(1));
	}

}

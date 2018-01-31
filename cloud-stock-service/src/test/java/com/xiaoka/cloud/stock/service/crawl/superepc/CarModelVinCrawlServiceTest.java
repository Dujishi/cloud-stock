package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by sabo on 20/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml"
})
public class CarModelVinCrawlServiceTest extends ContainerTest {

	@Resource
	CarModelVinCrawlService carModelVinCrawlService;

	@Test
	public void crawlCarModelVin() throws Exception {
		carModelVinCrawlService.crawlCarModelVin("LVSHJCAL9FE204966");
	}

}
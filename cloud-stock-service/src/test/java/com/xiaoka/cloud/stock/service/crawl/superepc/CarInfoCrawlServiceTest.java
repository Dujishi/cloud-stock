package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by sabo on 18/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml"
})
public class CarInfoCrawlServiceTest extends ContainerTest{

	@Resource
	CarInfoCrawlService carInfoCrawlService;

	@Test
	public void crawlAllCarModel() throws Exception {
		carInfoCrawlService.crawlAllCarModel(-1);
	}

	@Test
	public void crawlCarBrandAndSeriesAndMakes(){
		carInfoCrawlService.crawlCarBrandAndSeriesAndMakes();
	}

}
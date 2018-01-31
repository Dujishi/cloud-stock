package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by sabo on 21/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml"
})
public class ReplacePartCrawlServiceTest extends ContainerTest{

	@Resource
	ReplacePartCrawlService replacePartCrawlService;

	@Test
	public void crawlReplacePart() throws Exception {
		replacePartCrawlService.crawlReplacePart("30520-PWA-003");
	}

}
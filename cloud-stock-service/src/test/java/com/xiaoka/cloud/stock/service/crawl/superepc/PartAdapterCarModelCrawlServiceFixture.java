package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml"
})
public class PartAdapterCarModelCrawlServiceFixture extends ContainerTest {

	@Resource
	PartAdapterCarModelCrawlService partAdapterCarModelCrawlService;

	@Test
	public void crawlPartAdapterCarModelTest() {
		List<String> codes = Lists.newArrayList();
		codes.add("DS73 F02523 AF");
		codes.add("DS7G 6337 BBA");
		codes.add("DS73 F243W06 AF35B8");
		partAdapterCarModelCrawlService.crawlPartAdapterCarModel(codes);

	}
}

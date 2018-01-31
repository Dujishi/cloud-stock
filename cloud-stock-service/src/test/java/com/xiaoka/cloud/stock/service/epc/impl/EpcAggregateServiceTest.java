package com.xiaoka.cloud.stock.service.epc.impl;

import com.xiaoka.cloud.stock.service.epc.EpcAggregateService;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2018/1/17 16:31
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
		"classpath*:spring/tool-service.xml",
		"classpath*:spring/cloud-stock-elk-test.xml"
})
public class EpcAggregateServiceTest extends ContainerTest {

	@Resource
	private EpcAggregateService epcAggregateService;

	@Test
	public void aggregateSuperEpcByToday(){
		long amount =  epcAggregateService.aggregateSuperEpcByToday();
		System.out.println(amount);
	}

	@Test
	public void aggregateZeroEpcByToday(){
		Map<Integer, Long> map =  epcAggregateService.aggregateZeroEpcByToday();
		System.out.println(map);
	}
}

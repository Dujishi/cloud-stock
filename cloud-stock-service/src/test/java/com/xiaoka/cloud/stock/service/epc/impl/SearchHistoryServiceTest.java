package com.xiaoka.cloud.stock.service.epc.impl;

import com.xiaoka.cloud.stock.service.epc.EpcSearchHistoryService;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * Do something
 *
 * @author gancao 2017/11/22 17:06
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds-test.xml",
		"classpath:spring/cloud-stock-orm-test.xml",
		"classpath:spring/cloud-stock-context-test.xml",
		"classpath:spring/cloud-stock-soa-reference-test.xml",
		"classpath:spring/memcache-test.xml",
		"classpath:spring/mongo-test.xml"
})
public class SearchHistoryServiceTest extends ContainerTest {

	@Resource
	private EpcSearchHistoryService epcSearchHistoryService;

	@Test
	public void insert(){
		epcSearchHistoryService.insertSearchHistory(1, "LVSFCFAE48F260881", null);
	}

}

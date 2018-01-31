package com.xiaoka.cloud.stock.service.stock;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.service.stock.vo.CloudStockPageVo;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhouze
 * @date 2017/11/22
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
public class CloudStockServiceTest extends ContainerTest {

	@Resource
	CloudStockService cloudStockService;

	@Test
	public void searchStockPartsByOEList() throws Exception {
		List<String> codes = Lists.newArrayList();
		//codes.add("WP1AG2921ELA11593");
		codes.add("1L3T 14A094 GA");
		codes.add("4M5P7890UC");
		List<CloudStockPageVo> cloudStockPageVos = cloudStockService.searchStockPartsByOEList(codes, 32825);

		Assert.assertNotNull(cloudStockPageVos);

	}

}
package com.xiaoka.cloud.stock.service.region.impl;

import com.xiaoka.cloud.stock.service.order.impl.TestUtil;
import com.xiaoka.cloud.stock.service.region.RegionService;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context-order.xml"
})
public class RegionServiceImplTest extends ContainerTest{

	@Resource RegionService regionService;

	@Test
	public void getProvinceList() throws Exception {
		System.out.println(TestUtil.success(regionService.getProvinceList(null)));
	}

	@Test
	public void getCityList() throws Exception {
		System.out.println(TestUtil.success(regionService.getCityListByProvName("浙江省", null)));
	}

	@Test
	public void getCityDistrictList() throws Exception {
		System.out.println(TestUtil.success(regionService.getCityDistrictListByProvAndCityName("浙江省", "杭州市")));
	}

}
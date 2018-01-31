package com.xiaoka.cloud.stock.service.epc.impl;

import com.xiaoka.cloud.stock.service.epc.EpcSourceEnum;
import com.xiaoka.cloud.stock.service.epc.VinSampleService;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.Arrays;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context-vin-sample.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
})
public class VinSampleServiceImplTest extends ContainerTest{

	@Resource
	VinSampleService vinSampleService;

	@Test
	public void createVinSample() throws Exception {
		vinSampleService.createVinSample(Arrays.asList(38));
	}

	@Test
	public void checkVinSample() throws Exception {
		vinSampleService.checkVinSample();
	}

}
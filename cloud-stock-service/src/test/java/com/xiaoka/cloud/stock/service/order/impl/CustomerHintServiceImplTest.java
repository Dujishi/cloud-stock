package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.service.order.CustomerHintService;
import com.xiaoka.cloud.stock.service.order.dto.CustomerHintDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context-order.xml"
})
public class CustomerHintServiceImplTest extends ContainerTest{

	@Resource
	CustomerHintService customerHintService;

	@Test
	public void getCustomerHintList() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		List<CustomerHintDto> customerHintDtoList = customerHintService.getCustomerHintList(cloudSupplierUserDto, "意汽");

		System.out.println(TestUtil.success(customerHintDtoList));

	}

}
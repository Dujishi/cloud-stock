package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.core.order.entity.CsIndentPartEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.core.util.dto.ApiResult;
import com.xiaoka.cloud.stock.service.order.BigDecimalUtil;
import com.xiaoka.cloud.stock.service.order.IndentService;
import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.IndentPartDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context-order.xml"
})
public class IndentServiceImplTest extends ContainerTest{

	@Resource
	IndentService indentService;

	@Test
	public void getIndentList() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		List<IndentDto> indentDtoList = indentService.getIndentList(cloudSupplierUserDto);
		System.out.println(TestUtil.success(indentDtoList));
	}

	@Test
	public void getIndentDetail() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);

		IndentDto indentDto = indentService.getIndentDetail(cloudSupplierUserDto, "PHD100000");
		System.out.println(Jackson.mobile().writeValueAsString(indentDto));
	}

	@Test
	public void saveIndentDetail() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		List<IndentDto> indentDtoList = indentService.getIndentList(cloudSupplierUserDto);
		IndentDto indentDtoParam = new IndentDto();
//		indentDtoParam.setId(indentDtoList.get(0).getId());
		indentDtoParam.setIndentNo(indentDtoList.get(0).getIndentNo());
		indentDtoParam.setName("奥迪");
		indentDtoParam.setCustomerName("荣意汽修");
		indentDtoParam.setContact("张经理");
		indentDtoParam.setContactPhone("13588888888");
		indentDtoParam.setVin("WP1AG2921ELA11593");
		indentDtoParam.setProvince("浙江省");
		indentDtoParam.setCity("杭州市");
		indentDtoParam.setDistrict("西湖区");
		indentDtoParam.setAddress("文一西路512号");
		indentDtoParam.setCarModel("保时捷卡宴");
		indentDtoParam.setDiscountPrice(BigDecimalUtil.scaleOf(new BigDecimal(0)));
		indentDtoParam.setIndentPartDtoList(new ArrayList<>());

		IndentDto indentDtoInDb = indentService.getIndentDetail(cloudSupplierUserDto, indentDtoParam.getIndentNo());
		List<IndentPartDto> indentPartDtoInDbList = indentDtoInDb.getIndentPartDtoList();
		for(IndentPartDto indentPartDtoInDb : indentPartDtoInDbList){
			IndentPartDto indentPartDto = new IndentPartDto();
			indentPartDto.setId(indentPartDtoInDb.getId());
			indentPartDto.setUnitPrice(indentPartDtoInDb.getUnitPrice());
			indentPartDto.setAmount(indentPartDtoInDb.getAmount());
			indentDtoParam.getIndentPartDtoList().add(indentPartDto);
		}
		indentDtoParam.getIndentPartDtoList().remove(0);
		System.out.println(Jackson.mobile().writeValueAsString(indentDtoParam));
		IndentDto indentDto = indentService.saveIndentDetail(cloudSupplierUserDto, indentDtoParam);
		System.out.println(Jackson.mobile().writeValueAsString(indentDtoParam));
	}

	@Test
	public void addIndentPart() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		List<IndentDto> indentDtoList = indentService.getIndentList(cloudSupplierUserDto);
		IndentDto indentDto = indentDtoList.get(0);

		IndentPartDto indentPartDto = new IndentPartDto();
		indentPartDto.setIndentNo(indentDto.getIndentNo());
		indentPartDto.setOeNo("OE236");
		indentPartDto.setPartName("电瓶");
		indentPartDto.setOriginPlace("广州");
		indentPartDto.setManufacturer("瓦尔塔");
		indentPartDto.setPartDepot("杭州仓");
		indentPartDto.setUnitPrice(BigDecimalUtil.scaleOf(new BigDecimal(3000.00)));
		indentPartDto.setAmount(BigDecimalUtil.scaleOf(new BigDecimal(1)));

		System.out.println(Jackson.mobile().writeValueAsString(indentPartDto));
		IndentPartDto indentPartDtoResult = indentService.addIndentPart(cloudSupplierUserDto, indentPartDto);
		System.out.println(Jackson.mobile().writeValueAsString(indentPartDtoResult));
	}

	@Test
	public void addIndent() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setName("sabo");
		cloudSupplierUserDto.setSupplierId(11111);
		IndentDto indentDto = indentService.addIndent(cloudSupplierUserDto, null);
		System.out.println(TestUtil.success(indentDto));
	}

	@Test
	public void deleteIndent() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		indentService.deleteIndent(cloudSupplierUserDto, "PHD100000");
		System.out.println(TestUtil.success(null));
	}

	@Test
	public void updateIndentName() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		indentService.updateIndentName(cloudSupplierUserDto, "PHD100000", "7777");
		System.out.println(TestUtil.success(null));
	}



}
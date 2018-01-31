package com.xiaoka.cloud.stock.service.epc.impl;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.service.epc.EpcQueryService;
import com.xiaoka.cloud.stock.service.epc.dto.CarBrandDto;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelWebDto;
import com.xiaoka.cloud.stock.service.epc.dto.CarSeriesDto;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierUserService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sabo on 18/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml",
		"classpath:spring/tool-service.xml",
		"classpath*:spring/cloud-stock-elk-test.xml"
})
public class EpcQueryServiceTest extends ContainerTest {

	@Resource
	private EpcQueryService epcQueryService;
	@Resource
	private CloudSupplierUserService cloudSupplierUserService;

	@Test
	public void testGetCarPartCategoryDto() throws Exception {
		epcQueryService.getCarPartCategoryDto(null, 35, null);

	}

	@Test
	public void testGetModelByModelName() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(1);
		List<CarBrandDto> data = epcQueryService.getModelByModelName("丰田RAV4", cloudSupplierUserDto);
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getBrandFirstLetter() {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(1);
		List<CarBrandDto> data = epcQueryService.getBrandFirstLetter(cloudSupplierUserDto);
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getBrandByFirstLetter() {
		List<CarBrandDto> data = epcQueryService.getBrandByFirstLetter("A");
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getSeriesByBrand() {
		List<CarSeriesDto> data = epcQueryService.getSeriesByBrand(13, null);
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getModelYearBySeries() {
		List<CarModelWebDto> data = epcQueryService.getModelYearBySeries(13, "进口宝马1系");
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getModelBySeries() {
		List<CarModelWebDto> data = epcQueryService.getModelBySeries(13, "进口宝马1系", "2016");
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getBrandByModelName() {
		List<CarBrandDto> data = epcQueryService.getBrandByModelName("A4L");
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getModelYearByModelName() {
		List<CarModelWebDto> data = epcQueryService.getModelYearByModelName("A4L", 10, null);
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getModelBySearch() {
		List<CarModelWebDto> data = epcQueryService.getModelBySearch("A4L", 10, "2017");
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getAllBrand() {
		List<CarBrandDto> data = epcQueryService.getAllBrand();
		System.out.println(JSON.toJSONString(data));
	}

	@Test
	public void getAuthorisedByFirstLetter() {
		List<CarBrandDto> data = epcQueryService.getAuthorisedByFirstLetter(null, 1);
		System.out.println(JSON.toJSONString(data));
		List<CarBrandDto> data1 = epcQueryService.getAuthorisedByFirstLetter("B", 1);
		System.out.println(JSON.toJSONString(data1));
	}

	@Test
	public void setBrandAuth() {
		cloudSupplierUserService.setBrandAuth(1, Arrays.asList(1,2,3,4,5,6,21,22,23,24,25,26), "ceshi");
	}


}
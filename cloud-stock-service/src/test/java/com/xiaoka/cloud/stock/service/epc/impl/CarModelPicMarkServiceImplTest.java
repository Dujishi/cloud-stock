package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicMarkEntity;
import com.xiaoka.cloud.stock.service.epc.CarModelPicMarkService;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkItem;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabo on 27/11/2017.
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
public class CarModelPicMarkServiceImplTest extends ContainerTest {

	@Resource
	CarModelPicMarkService carModelPicMarkService;

	@Test
	public void getCarModelPicMarksByModelId() throws Exception {
		List<CarModelPicMarkDto> carModelPicMarkDtoList = carModelPicMarkService.getCarModelPicMarksByModelId(0, 1);
		System.out.println(Jackson.mobile().writeValueAsString(carModelPicMarkDtoList));
	}

	@Test
	public void getCarModelPicMark() throws Exception {
		CarModelPicMarkDto carModelPicMarkDto = carModelPicMarkService.getCarModelPicMark(1, "110A", "110A", null, null);
		System.out.println(Jackson.mobile().writeValueAsString(carModelPicMarkDto));
	}

	@Test
	public void createByCarModelPartEntity() throws Exception {
		CarModelPartEntity carModelPartEntity = new CarModelPartEntity();
		carModelPartEntity.setModelId(4058);
		carModelPartEntity.setPicNum("11_3173");
		carModelPartEntity.setPicName("11_3173");
		carModelPartEntity.setPicPath("40_宝马_宝马/201611/EPC图片/11_3173.png");
		carModelPicMarkService.insertByCarModelPartEntity(carModelPartEntity);
	}


	@Test
	public void insertByCarModelPartEntityTask() throws Exception {
		carModelPicMarkService.insertByCarModelPartEntityTask(1, 10, 1);
	}

	@Test
	public void updateCarModelPicMark() throws Exception {
		CarModelPicMarkDto carModelPicMarkDto = new CarModelPicMarkDto();
		carModelPicMarkDto.setModelId(1);
		carModelPicMarkDto.setPicNum("110A");
		carModelPicMarkDto.setPicName("110A");
		carModelPicMarkDto.setRawPicHeight(453);
		carModelPicMarkDto.setRawPicWidth(680);


		List<CarModelPicMarkItem> carModelPicMarkItemList = new ArrayList<>();
		carModelPicMarkDto.setCarModelPicMarkItemList(carModelPicMarkItemList);
		//11000,11018,11023,11026,11111,11114,11128,11140,11141,11142,11160,11170,11190,14312,14314,14318,15370,15385,15525,15335,15355,36000,153AD,BOLT0A
		CarModelPicMarkItem carModelPicMarkItem = new CarModelPicMarkItem();
		carModelPicMarkItem.setPicSequence("11000");
		carModelPicMarkItem.setRecHeight(39);
		carModelPicMarkItem.setRecWidth(56);
		carModelPicMarkItem.setMarkedPicHeight(453);
		carModelPicMarkItem.setMarkedPicWidth(680);
		carModelPicMarkItem.setxAxis(536d);
		carModelPicMarkItem.setyAxis(622d);
		carModelPicMarkItemList.add(carModelPicMarkItem);

		carModelPicMarkItem = new CarModelPicMarkItem();
		carModelPicMarkItem.setPicSequence("11018");
		carModelPicMarkItem.setRecHeight(39);
		carModelPicMarkItem.setRecWidth(56);
		carModelPicMarkItem.setMarkedPicHeight(453);
		carModelPicMarkItem.setMarkedPicWidth(680);
		carModelPicMarkItem.setxAxis(536d);
		carModelPicMarkItem.setyAxis(622d);
		carModelPicMarkItemList.add(carModelPicMarkItem);

		carModelPicMarkItem = new CarModelPicMarkItem();
		carModelPicMarkItem.setPicSequence("11023");
		carModelPicMarkItem.setRecHeight(44);
		carModelPicMarkItem.setRecWidth(28);
		carModelPicMarkItem.setMarkedPicHeight(453);
		carModelPicMarkItem.setMarkedPicWidth(680);
		carModelPicMarkItem.setxAxis(527d);
		carModelPicMarkItem.setyAxis(711d);
		carModelPicMarkItemList.add(carModelPicMarkItem);


		carModelPicMarkDto.setUpdateBy("sabo");

		carModelPicMarkService.updateCarModelPicMark(carModelPicMarkDto);
	}

	@Test
	public void queryCarModelPicMarks() {
		List<CarModelPicMarkEntity> param          = Lists.newArrayList();
		CarModelPicMarkEntity          carModelEntity = new CarModelPicMarkEntity();
		carModelEntity.setModelId(1);
		carModelEntity.setPicNum("117A");
		carModelEntity.setPicName("117A");
		param.add(carModelEntity);
		List<CarModelPicMarkDto> results = carModelPicMarkService.queryCarModelPicMarks(param);


		Assert.assertNotNull(results);

	}

	@Test
	public void getCarModelPicMarksByModelIdAndSubAssembly(){
		List<CarModelPicMarkDto> carModelPicMarkDtoList = carModelPicMarkService.getCarModelSubAssemblyPicMarksByModelId(3556,"空调及气囊","空调压缩机");
		Assert.assertNotNull(carModelPicMarkDtoList);
	}

}
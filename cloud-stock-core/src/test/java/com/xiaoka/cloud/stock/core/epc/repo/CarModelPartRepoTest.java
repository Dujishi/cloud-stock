package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sabo on 21/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class CarModelPartRepoTest extends ContainerTest{

	@Resource
	CarModelPartRepo carModelPartRepo;

	@Test
	public void select() throws Exception {
		CarModelPartEntity carModelPartEntity = new CarModelPartEntity();
		carModelPartEntity.setModelId(430);
		List<CarModelPartEntity> carModelPartEntityList = carModelPartRepo.select(carModelPartEntity);
		System.out.println(Jackson.mobile().writeValueAsString(carModelPartEntityList));
	}

	@Test
	public void insert() throws Exception {
		CarModelPartEntity carModelPartEntity = new CarModelPartEntity();
		carModelPartEntity.setModelId(430);
		carModelPartEntity.setPartCode("test");
		carModelPartEntity.setPicNum("001");
		carModelPartEntity.setPicPath("test_pic");
		carModelPartEntity.setPicSequence("test_seq");
		carModelPartEntity.setStandardPartId(1234);
		carModelPartRepo.insert(carModelPartEntity);
	}

	@Test
	public void batchInsert() throws Exception {
		List<CarModelPartEntity> carModelPartEntityList = new ArrayList<>();
		CarModelPartEntity carModelPartEntity = new CarModelPartEntity();
		carModelPartEntity.setModelId(100);
		carModelPartEntity.setPartCode("test");
		carModelPartEntity.setPicNum("001");
		carModelPartEntity.setPicPath("test_pic");
		carModelPartEntity.setPicSequence("test_seq");
		carModelPartEntity.setStandardPartId(1234);

		carModelPartEntity = new CarModelPartEntity();
		carModelPartEntity.setModelId(100);
		carModelPartEntity.setPartCode("test");
		carModelPartEntity.setPicNum("001");
		carModelPartEntity.setPicPath("test_pic");
		carModelPartEntity.setPicSequence("test_seq");
		carModelPartEntity.setStandardPartId(1234);

		carModelPartEntityList.add(carModelPartEntity);
		carModelPartRepo.batchInsert(carModelPartEntityList);
	}

}
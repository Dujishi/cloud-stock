package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sabo on 20/11/2017.
 */
public class SuperEPCUtilTest {
	@Test
	public void distinctList() throws Exception {
		List<CarModelEntity> carModelEntityList = new ArrayList<>();

		CarModelEntity carModelEntity = new CarModelEntity();
		carModelEntity.setBrandId(38);
		carModelEntity.setBrandName("丰田");
		carModelEntity.setMakeId(41);
		carModelEntity.setMakeName("丰田");
		carModelEntityList.add(carModelEntity);

		carModelEntity = new CarModelEntity();
		carModelEntity.setBrandId(38);
		carModelEntity.setBrandName("丰田");
		carModelEntity.setMakeId(53);
		carModelEntity.setMakeName("广州丰田");
		carModelEntityList.add(carModelEntity);

		carModelEntity = new CarModelEntity();
		carModelEntity.setBrandId(38);
		carModelEntity.setBrandName("丰田");
		carModelEntity.setMakeId(125);
		carModelEntity.setMakeName("一汽丰田");
		carModelEntityList.add(carModelEntity);

		carModelEntity = new CarModelEntity();
		carModelEntity.setBrandId(38);
		carModelEntity.setBrandName("丰田");
		carModelEntity.setMakeId(125);
		carModelEntity.setMakeName("一汽丰田");
		carModelEntityList.add(carModelEntity);

		List<CarModelEntity> brandGroup = SuperEPCUtil.distinctList(carModelEntityList, CarModelEntity::getBrandId);
		Assert.assertEquals(1, brandGroup.size());
		List<CarModelEntity> makeGroup = SuperEPCUtil.distinctList(carModelEntityList, CarModelEntity::getBrandId, CarModelEntity::getMakeId);
		Assert.assertEquals(3, makeGroup.size());
	}

}
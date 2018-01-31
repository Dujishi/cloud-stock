package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

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
public class CarModelRepoTest extends ContainerTest{

	@Resource
	CarModelRepo carModelRepo;

	@Test
	public void selectGroupByModelId() throws Exception {
		List<CarModelEntity> carModelEntityList0 = carModelRepo.selectGroupByModelId(0, 2, null);
		List<CarModelEntity> carModelEntityList1 = carModelRepo.selectGroupByModelId(1, 2, null);
		Assert.assertEquals(carModelEntityList0.size(), carModelEntityList1.size());
		Assert.assertEquals(carModelEntityList0.get(0).getId(), carModelEntityList1.get(0).getId());
		Assert.assertEquals(2, carModelEntityList0.size());
	}

	@Test
	public void countDistinctModelId() throws Exception {
		Integer count = carModelRepo.countDistinctModelId();
		System.out.println("========" + count);
	}

}
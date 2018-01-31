package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicMarkEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by sabo on 25/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class CarModelPicMarkRepoTest extends ContainerTest{

	@Resource
	CarModelPicMarkRepo carModelPicMarkRepo;

	@Test
	public void select() throws Exception {
		CarModelPicMarkEntity param = new CarModelPicMarkEntity();
		carModelPicMarkRepo.select(param);
	}

	@Test
	public void insert() throws Exception {
		carModelPicMarkRepo.insert(null);
	}

}
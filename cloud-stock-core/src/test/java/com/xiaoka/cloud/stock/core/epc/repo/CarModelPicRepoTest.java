package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by suqin on 21/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class CarModelPicRepoTest extends ContainerTest{

	@Resource
	CarModelPicRepo carModelPicRepo;

	@Test
	public void selectGroupByModelId() throws Exception {
		List<CarModelPicEntity> carModelPicEntities = carModelPicRepo.selectGroupPicByModelId(7998);
		System.out.println(Jackson.mobile().writeValueAsString(carModelPicEntities));
	}

}
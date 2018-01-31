package com.xiaoka.cloud.stock.core.crawl;

import com.xiaoka.cloud.stock.core.crawl.repo.ZeroCarGroupChooseRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroCarModelChooseRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroCarSubGroupChooseRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarGroupChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarSubGroupChooseEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/12/18 14:09
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class ZeroTest extends ContainerTest {

	@Resource
	ZeroCarModelChooseRepo zeroCarModelChooseRepo;
	@Resource
	ZeroCarGroupChooseRepo zeroCarGroupChooseRepo;
	@Resource
	ZeroCarSubGroupChooseRepo zeroCarSubGroupChooseRepo;

	@Test
	public void selectCarModel(){
		ZeroCarModelChooseEntity param = new ZeroCarModelChooseEntity();
		param.setIsValid(1);
		List<ZeroCarModelChooseEntity> chooseEntityList = zeroCarModelChooseRepo.select(param);
		System.out.println(chooseEntityList);
	}

	@Test
	public void selectGroup(){
		ZeroCarGroupChooseEntity param = new ZeroCarGroupChooseEntity();
		param.setIsValid(1);
		List<ZeroCarGroupChooseEntity> zeroCarModelChooseEntityList = zeroCarGroupChooseRepo.select(param);
		System.out.println(zeroCarModelChooseEntityList);
	}

	@Test
	public void carModelInsert(){
		ZeroCarModelChooseEntity entity = new ZeroCarModelChooseEntity();
		entity.setEngine("3.0 BT SOFT V6 2WD 330 HP");
		entity.setGearBox("AUTOMATIC");
		entity.setMarket("BOSNIA");
		entity.setYear("2016");
		entity.setCarModel("Ghibli 2014 - 2016");
		entity.setBrand("玛莎拉蒂");
		entity.setPhone("13735532621");
		entity.setUri("auth=bjd2enFwNy81NyQlJj5MJyZKJyUkIEokIEohSicmITc5NTdgfHE3LzU3JCYjIyctJiQkJiclJCQtICwtJjdo&code=maserati&carstype=");
		entity.setAuth("bjd2enFwNy81NyQlJj5MJyZKJyUkIEokIEohSicmITc5NTdgfHE3LzU3JCYjIyctJiQkJiclJCQtICwtJjdo");
		zeroCarModelChooseRepo.insert(entity);
	}

	@Test
	public void carGroupBatchInsert(){
		ZeroCarGroupChooseEntity entity = new ZeroCarGroupChooseEntity();
		entity.setAuth("bjd7YHg3LzU3JSQ3OTU3dnpxcDcvNTckJSY%2BTCcmSiclJCNKJCRKIUonJiQ3OTU3Y3x7Ny81Nzc5NTdgfHE3LzU3JiAhICYnIyUjJCQjJSUgJCUjJzc5NTd2YWxlcDcvNTd2JSUmN2g%3D");
		entity.setGroupName("01 发动机");
		entity.setGroupNum("01");
		entity.setName("01 发动机");
		entity.setUri("auth=bjd7YHg3LzU3JSQ3OTU3dnpxcDcvNTckJSY%2BTCcmSiclJCNKJCRKIUonJiQ3OTU3Y3x7Ny81Nzc5NTdgfHE3LzU3JiAhICYnIyUjJCQjJSUgJCUjJzc5NTd2YWxlcDcvNTd2JSUmN2g%3D");
		zeroCarGroupChooseRepo.batchInsert(Collections.singletonList(entity));
	}

	@Test
	public void selectCarGroup(){
		ZeroCarGroupChooseEntity entity = new ZeroCarGroupChooseEntity();
		entity.setAuth("bjd7YHg3LzU3JSQ3OTU3dnpxcDcvNTckJSY%2BTCcmSiclJCNKJCRKIUonJiQ3OTU3Y3x7Ny81Nzc5NTdgfHE3LzU3JiAhICYnIyUjJCQjJSUgJCUjJzc5NTd2YWxlcDcvNTd2JSUmN2g%3D");
		entity.setIsValid(1);
		List<ZeroCarGroupChooseEntity> entityList = zeroCarGroupChooseRepo.select(entity);
		System.out.println(entityList);
	}

	@Test
	public void carSubGroupBatchInsert(){
		ZeroCarSubGroupChooseEntity entity = new ZeroCarSubGroupChooseEntity();
		entity.setAuth("bjd7YHg3LzU3JSQ3OTU3dnpxcDcvNTckJSY%2BTCcmSiclJCNKJCRKIUonJiQ3OTU3Y3x7Ny81Nzc5NTdgfHE3LzU3JiAhICYnIyUjJCQjJSUgJCUjJzc5NTd2YWxlcDcvNTd2JSUmN2g%3D");
		entity.setInnerGroupId(3);
		entity.setInnerSubGroupId(3);
		entity.setGroupId(1);
		entity.setGroupName("xxx");
		entity.setSubGroupName("01 发动机");
		entity.setSubGroup("01");
		entity.setMid("01 发动机");
		entity.setNum("01");
		entity.setUri("auth=bjd7YHg3LzU3JSQ3OTU3dnpxcDcvNTckJSY%2BTCcmSiclJCNKJCRKIUonJiQ3OTU3Y3x7Ny81Nzc5NTdgfHE3LzU3JiAhICYnIyUjJCQjJSUgJCUjJzc5NTd2YWxlcDcvNTd2JSUmN2g%3D");
		zeroCarSubGroupChooseRepo.batchInsert(Collections.singletonList(entity));
	}
	@Test
	public void success(){
		int chooseCount = zeroCarSubGroupChooseRepo.getSubGroupChooseCountByCId(1413);
		int count = zeroCarSubGroupChooseRepo.getSubGroupCountByCId(1413);
		System.out.println(chooseCount);
		System.out.println(count);
	}

}

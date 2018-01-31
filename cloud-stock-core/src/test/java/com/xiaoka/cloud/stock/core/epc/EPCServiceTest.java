package com.xiaoka.cloud.stock.core.epc;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.*;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.EpcRequestLogEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardCategoryEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.*;

/**
 * Do something
 *
 * @author gancao 2017/11/17 14:41
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class EPCServiceTest extends ContainerTest {

	@Resource
	private CarModelRepo carModelRepo;
	@Resource
	private StandardCategoryRepo standardCategoryRepo;
	@Resource
	private StandardPartRepo standardPartRepo;
	@Resource
	private CarBrandIconRepo carBrandIconRepo;
	@Resource
	private EpcRequestLogRepo epcRequestLogRepo;

	@Test
	public void getModelList(){
		List<Integer> list = Lists.newArrayList(ImmutableList.of(123,345));
		List<CarModelEntity> entityList = carModelRepo.selectByIds(list);
		System.out.println(Jackson.mobile().writeValueAsString(entityList));
	}

	@Test
	public void insertModel(){
		List<CarModelEntity> carModelEntityList = new ArrayList<>();
		CarModelEntity entity = new CarModelEntity();
		entity.setModelId(130);
		entity.setModelName("测试车型");
		entity.setBrandId(1);
		entity.setBrandName("测试品牌");
		entity.setMakeId(1);
		entity.setMakeName("测试厂商");
		entity.setSeriesId(2);
		entity.setSeriesName("测试车系");

		carModelEntityList.add(entity);
		carModelRepo.batchInsert(carModelEntityList);
	}

	@Test
	public void selectCCategory(){
		StandardCategoryEntity entity = new StandardCategoryEntity();
		entity.setIsValid(1);
		List<StandardCategoryEntity> categoryEntityList = standardCategoryRepo.select(entity);
		System.out.println(categoryEntityList);
	}

	@Test
	public void selectPart(){
		standardPartRepo.getPartIdByNames(Lists.newArrayList("测试","哈哈"));
	}

	@Test
	public void selectIcon(){
		String icon = carBrandIconRepo.selectIconByName("大众");
		System.out.println(icon);
	}

	@Test
	public void selectIconByNames(){
		Map<String, String> map = carBrandIconRepo.selectIconByNames(Arrays.asList("大众", "丰田"));
		System.out.println(Jackson.mobile().writeValueAsString(map));
	}

	@Test
	public void insertLog(){
		EpcRequestLogEntity entity = new EpcRequestLogEntity();
		entity.setService("service");
		entity.setMethod("method");
		entity.setArgs("args");
		entity.setCost(1000);
		entity.setStatus(1);
		entity.setCreateTime(new Date());
		entity.setUpdateTime(new Date());
		epcRequestLogRepo.insert(entity);
	}

}

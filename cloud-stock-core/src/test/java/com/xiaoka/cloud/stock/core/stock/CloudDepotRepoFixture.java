package com.xiaoka.cloud.stock.core.stock;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.stock.repo.CloudDepotRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudDepotEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/16
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class CloudDepotRepoFixture extends ContainerTest {

	@Resource
	CloudDepotRepo cloudDepotRepo;

	@Test
	public void queryDepotByCDepotIdAndCSupplierIdTest() {

		CloudDepotEntity cloudDepotEntity = cloudDepotRepo.queryDepotByCondition("3", "2");

		Assert.assertNotNull(cloudDepotEntity);
		Assert.assertEquals(Integer.valueOf(1), cloudDepotEntity.getShopId());
	}

	@Test
	public void queryDepotByCDepotIdAndCSupplierIdListTest() {
		List<CloudDepotEntity> list = Lists.newArrayList();
		CloudDepotEntity       c1   = new CloudDepotEntity();
		c1.setShopId(2);
		c1.setcDepotId("3");
		list.add(c1);

		CloudDepotEntity       c3   = new CloudDepotEntity();
		c3.setShopId(2);
		c3.setcDepotId("4");
		list.add(c3);

		CloudDepotEntity c2 = new CloudDepotEntity();
		c2.setShopId(11);
		c2.setcDepotId("12");
		list.add(c2);

		List<CloudDepotEntity> cloudDepotEntities = cloudDepotRepo.queryDepotByConditionList(list);

		Assert.assertNotNull(cloudDepotEntities);
		Assert.assertEquals(3, cloudDepotEntities.size());
	}

	@Test
	public void insertCloudDepotEntitiesTest() {
		List<CloudDepotEntity> list = Lists.newArrayList();
		CloudDepotEntity       c1   = new CloudDepotEntity();
		c1.setcDepotId("123");
		c1.setcSupplierId("21");
		c1.setShopId(1);
		c1.setDepotCity("sad");
		c1.setDepotCode("code");
		list.add(c1);

		CloudDepotEntity c2 = new CloudDepotEntity();
		c2.setcDepotId("126");
		c2.setcSupplierId("22");
		c2.setShopId(1);
		c2.setDepotCity("city");
		c2.setDepotCode("code");
		list.add(c2);

		cloudDepotRepo.insertCloudDepotEntities(list);

		Assert.assertNotNull(list);

		Assert.assertTrue(list.stream().allMatch(p -> null != p.getId()));
	}

	@Test
	public void updateCloudDepotEntitiesTest() {
		List<CloudDepotEntity> list = Lists.newArrayList();
		CloudDepotEntity       c1   = new CloudDepotEntity();
		c1.setcDepotId("123");
		c1.setShopId(1);
		c1.setcSupplierId("111");
		c1.setDepotCity("qqqq");
		c1.setDepotCode("qqqq");
		c1.setDepotAddress("sadasdas");
		c1.setVersion(3);
		list.add(c1);

		CloudDepotEntity c2 = new CloudDepotEntity();
		c2.setcDepotId("126");
		c2.setShopId(2);
		c2.setcSupplierId("222");
		c2.setDepotCity("www");
		c2.setDepotCode("www");
		c2.setVersion(2);
		list.add(c2);

		cloudDepotRepo.casUpdateCloudDepotEntities(list);

		Assert.assertNotNull(list);

	}


}

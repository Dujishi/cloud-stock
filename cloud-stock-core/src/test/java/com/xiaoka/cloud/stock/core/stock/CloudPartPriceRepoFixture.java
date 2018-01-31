package com.xiaoka.cloud.stock.core.stock;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartPriceRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/17
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class CloudPartPriceRepoFixture extends ContainerTest {

	@Resource
	CloudPartPriceRepo cloudPartPriceRepo;


	@Test
	public void queryCloudPartPriceByConditionListTest() {
		List<CloudPartPriceEntity> partPrices             = Lists.newArrayList();
		List<CloudPartPriceEntity> cloudPartPriceEntities = cloudPartPriceRepo.queryCloudPartPriceByConditionList(partPrices);

		Assert.assertNotNull(cloudPartPriceEntities);
	}

	@Test
	public void insertCloudPartsPriceEntitiesTest() {
		List<CloudPartPriceEntity> list   = Lists.newArrayList();
		CloudPartPriceEntity c1 = new CloudPartPriceEntity();
		c1.setcPartId("111");
		c1.setcDepotId("21");
		c1.setShopId(1);
		c1.setOeNo("OE1");
		c1.setRepairStationPrice(BigDecimal.TEN);
		list.add(c1);

		CloudPartPriceEntity c2 = new CloudPartPriceEntity();
		c2.setcPartId("121");
		c2.setcDepotId("21");
		c2.setShopId(1);
		c2.setOeNo("OE1");
		c2.setRepairStationPrice(BigDecimal.TEN);
		list.add(c2);
		

		Integer count = cloudPartPriceRepo.insertCloudPartsPriceEntities(list);

		Assert.assertNotNull(count);
	}


	@Test
	public void casUpdateCloudPartPriceEntitiesTest() {
		List<CloudPartPriceEntity> list   = Lists.newArrayList();
		CloudPartPriceEntity c1 = new CloudPartPriceEntity();
		c1.setcPartId("11");
		c1.setcDepotId("21");
		c1.setShopId(1);
		c1.setOeNo("OE1");
		c1.setRepairStationPrice(BigDecimal.TEN);
		c1.setVersion(1);
		list.add(c1);

		CloudPartPriceEntity c2 = new CloudPartPriceEntity();
		c2.setcPartId("12");
		c2.setcDepotId("21");
		c2.setShopId(1);
		c2.setOeNo("OE2");
		c2.setRepairStationPrice(BigDecimal.ONE);
		c2.setVersion(1);
		list.add(c2);


		Integer count = cloudPartPriceRepo.casUpdateCloudPartPriceEntities(list);

		Assert.assertNotNull(count);
	}
}

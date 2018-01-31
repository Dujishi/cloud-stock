package com.xiaoka.cloud.stock.core.stock;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.help.page.PageList;
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
public class CloudPartRepoFixture extends ContainerTest {

	@Resource
	CloudPartRepo cloudPartRepo;

	@Test
	public void queryCloudPartsByConditionListTest() {
		List<CloudPartEntity> cloudPartEntities = null;
//		List<CloudPartEntity> cloudPartEntities = Lists.newArrayList();

		List<CloudPartEntity> cloudPartEntityList = cloudPartRepo.queryCloudPartsByConditionList(cloudPartEntities);

		Assert.assertNotNull(cloudPartEntityList);
	}

	@Test
	public void insertCloudPartEntitiesTest() {
		List<CloudPartEntity> list = Lists.newArrayList();
		CloudPartEntity       c1   = new CloudPartEntity();
		c1.setcPartId("25");
		c1.setcDepotId("123");
		c1.setcSupplierId("21");
		c1.setShopId(1);
		c1.setcSupplierId("1");
		c1.setOeNo("s1");
		c1.setPartName("partName");
		c1.setPartBrand("brand1");
		c1.setOriginPlace("原厂");
		c1.setManufacturer("sadas");
		c1.setcOperateMode(1);
		c1.setcCreateTime("2017-01-01");
		c1.setcUpdateTime("2017-01-01");
		list.add(c1);

		CloudPartEntity c2 = new CloudPartEntity();
		c2.setcPartId("26");
		c2.setcDepotId("222");
		c2.setcSupplierId("21");
		c2.setShopId(2);
		c2.setcSupplierId("2");
		c2.setOeNo("s2");
		c2.setPartName("partName");
		c2.setPartBrand("brand2");
		c2.setOriginPlace("副厂");
		c2.setManufacturer("qqqq");
		c2.setcOperateMode(2);
		c2.setcCreateTime("2017-01-01");
		c2.setcUpdateTime("2017-01-01");
		list.add(c2);


		cloudPartRepo.insertCloudPartEntities(list);

		Assert.assertNotNull(list);

	}

	@Test
	public void updateCloudPartEntitiesTest() {
		List<CloudPartEntity> list = Lists.newArrayList();
		CloudPartEntity       c1   = new CloudPartEntity();
		c1.setcPartId("1");
		c1.setcDepotId("123");
		c1.setShopId(1);
		c1.setcSupplierId("1");
		c1.setOeNo("s1");
		c1.setPartName("partName1");
		c1.setPartBrand("brand1");
		c1.setOriginPlace("原厂1");
		c1.setManufacturer("sadas1");
		c1.setcOperateMode(1);
		c1.setcCreateTime("2017-02-01");
		c1.setcUpdateTime("2017-02-01");
		c1.setVersion(2);
		list.add(c1);

		CloudPartEntity c2 = new CloudPartEntity();
		c2.setcPartId("2");
		c2.setcDepotId("222");
		c2.setShopId(2);
		c2.setcSupplierId("2");
		c2.setOeNo("s2");
		c2.setPartName("partName");
		c2.setPartBrand("brand2");
		c2.setOriginPlace("副厂2");
		c2.setManufacturer("qqqq2");
		c2.setcOperateMode(2);
		c2.setcCreateTime("2017-03-01");
		c2.setcUpdateTime("2017-03-01");
		c2.setVersion(3);
		list.add(c2);

		Integer flag = cloudPartRepo.casUpdateCloudPartEntities(list);


	}

	@Test
	public void insertNatureCloudPartEntitiesTest() {
		long vt = System.currentTimeMillis();

		for (int y = 1; y <= 200; y++) {
			List<CloudPartEntity> list = Lists.newArrayList();
			for (int i = 1; i <= 500; i++) {
				CloudPartEntity c = new CloudPartEntity();
				c.setcPartId(String.valueOf(y * i));
				c.setcDepotId(String.valueOf(i));
				c.setcSupplierId("21");
				c.setShopId(1);
				c.setcSupplierId("1");
				c.setOeNo("s1");
				c.setPartName("partName");
				c.setPartBrand("brand1");
				c.setOriginPlace("原厂");
				c.setManufacturer("sadas");
				c.setcOperateMode(1);
				c.setcCreateTime("2017-01-01");
				c.setcUpdateTime("2017-01-01");
				list.add(c);
			}
			cloudPartRepo.insertCloudPartEntities(list);
		}

		long ct1 = System.currentTimeMillis();

		for (int y = 1; y <= 1000; y++) {
			List<CloudPartEntity> list = Lists.newArrayList();
			for (int i = 1; i <= 100; i++) {
				CloudPartEntity c = new CloudPartEntity();
				c.setcPartId(String.valueOf(y * i + 110000));
				c.setcDepotId(String.valueOf(i));
				c.setcSupplierId("21");
				c.setShopId(1);
				c.setcSupplierId("1");
				c.setOeNo("s1");
				c.setPartName("partName");
				c.setPartBrand("brand1");
				c.setOriginPlace("原厂");
				c.setManufacturer("sadas");
				c.setcOperateMode(1);
				c.setcCreateTime("2017-01-01");
				c.setcUpdateTime("2017-01-01");
				list.add(c);
			}
			cloudPartRepo.insertCloudPartEntities(list);
		}

		long ct2 = System.currentTimeMillis();


		System.out.println("Plan[A]耗时-> " + (ct1 - vt) + "ms");
		System.out.println("Plan[B]耗时-> " + (ct2 - ct1) + "ms");

	}

	@Test
	public void queryExistsStockPageListTest() {
		int                       page      = 2;
		PageList<CloudPartEntity> pageList  = PageList.build(page, 2);
		CloudPartEntity           condition = new CloudPartEntity();
		condition.setShopId(32825);
		pageList.setCondition(condition);
		List<CloudPartEntity> cloudPartEntities = cloudPartRepo.queryExistsStockPageList(pageList);

		Assert.assertNotNull(cloudPartEntities);
	}

}

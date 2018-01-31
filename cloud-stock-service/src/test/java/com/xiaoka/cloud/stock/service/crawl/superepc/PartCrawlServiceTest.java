package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.PartCodeRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartCodeEntity;
import com.xiaoka.freework.container.test.ContainerTest;
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
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml"
})
public class PartCrawlServiceTest extends ContainerTest {

	@Resource
	PartCrawlService partCrawlService;

	@Resource
	PartCodeRepo partCodeRepo;

	@Test
	public void crawlModelPartTask() throws Exception {
	}

	@Test
	public void crawlModelPartByModelId() throws Exception {
		partCrawlService.crawlModelPartByModelId(7998);
	}

	@Test
	public void insertList() throws Exception {
		List<PartCodeEntity> insertList = Lists.newArrayList();
		PartCodeEntity       p1         = new PartCodeEntity();
		p1.setPartCode("1");
		p1.setPartName("asd");
		insertList.add(p1);

		PartCodeEntity p2 = new PartCodeEntity();
		p2.setPartCode("1");
		p2.setPartName("asd");
		insertList.add(p2);

		partCodeRepo.insertList(insertList);
	}

	@Test
	public void insertPartCodes() throws Exception {
		List<CarModelPartEntity> list = Lists.newArrayList();
		CarModelPartEntity carModelPart1 = new CarModelPartEntity();
		carModelPart1.setPartCode("21");
		carModelPart1.setPartName("test1");
		list.add(carModelPart1);

		CarModelPartEntity carModelPart2 = new CarModelPartEntity();
		carModelPart2.setPartCode("21");
		carModelPart2.setPartName("test1");
		list.add(carModelPart2);

		partCrawlService.insertPartCodes(list);
	}

}
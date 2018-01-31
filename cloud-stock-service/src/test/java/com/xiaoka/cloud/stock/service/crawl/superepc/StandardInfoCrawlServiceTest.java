package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by suqin on 2017/11/18.
 */
@ContextConfiguration(locations = {
		"classpath:spring/mongo.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
})
public class StandardInfoCrawlServiceTest extends ContainerTest {
	@Resource
	StandardInfoCrawlService standardInfoCrawlService;

	@Resource
	CarAssemblyInfoCrawlService carAssemblyInfoCrawlService;
	@Resource
	Part4sPriceCrawlService     part4sPriceCrawlService;
	@Resource
	CarModelRepo                carModelRepo;

	@Test
	public void testCrawlStandardPartAndAssemblyInfo() {
		long ct = System.currentTimeMillis();
		standardInfoCrawlService.crawlStandardPartAndAssemblyInfo();

		System.out.println("执行时间 ==> " + (System.currentTimeMillis() - ct) / 1000);
	}

	@Test
	public void testCrawlCarAssemblyAndPicInfo() {
		for (int i = 0; i < 5; i++) {
			List<CarModelEntity> carModelEntities = carModelRepo.selectGroupByModelId(i, 100, null);
			//		List<Integer> tid = Arrays.asList(7998, 353, 374, 660, 781, 868, 869, 1092, 1862, 2080, 2193);
			//			List<Integer> tid = Arrays.asList(7998);

			long vt = System.currentTimeMillis();

			carModelEntities.forEach(x ->
					carAssemblyInfoCrawlService.crawlCarAssemblyAndPicInfo(x.getModelId())
			);

			List<Integer> modelIds = carModelEntities.stream().map(CarModelEntity::getModelId).collect(Collectors.toList());

			long vt2 = System.currentTimeMillis();

			System.out.println("车型数据处理：" + JSON.toJSONString(modelIds) + "，当前批次:" + i + "，耗时：" + (vt2 - vt) / 1000 + "");
		}


	}

	@Test
	public void testCrawlPart4sPriceInfo() {
		part4sPriceCrawlService.crawlPart4sPriceInfo("DS73 F00016 BA");
	}
}

package com.xiaoka.cloud.stock.soa.impl.epc;

import com.xiaoka.cloud.stock.core.epc.repo.CarModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.PartCodeRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.service.crawl.superepc.*;
import com.xiaoka.cloud.stock.soa.api.epc.SuperEpcFullCrawlSoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by sabo on 22/11/2017.
 */
@Service("superEpcFullCrawlSoaService")
public class SuperEpcFullCrawlSoaServiceImpl implements SuperEpcFullCrawlSoaService {

	private static final Logger logger = LoggerFactory.getLogger(SuperEpcFullCrawlSoaServiceImpl.class);

	@Resource
	CarInfoCrawlService carInfoCrawlService;

	@Resource
	StandardInfoCrawlService standardInfoCrawlService;

	@Resource
	CarAssemblyInfoCrawlService carAssemblyInfoCrawlService;

	@Resource
	PartCrawlService partCrawlService;

	@Resource
	CarModelRepo carModelRepo;

	@Resource
	PartAdapterCarModelCrawlService partAdapterCarModelCrawlService;

	@Resource
	PartCodeRepo partCodeRepo;

	@Resource
	ReplacePartCrawlService replacePartCrawlService;

	@Resource
	Part4sPriceCrawlService part4sPriceCrawlService;

	@Override
	public void fullCrawl() {
		logger.info("Full craw start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		carInfoCrawlService.crawlAllCarModel(-1);

		carInfoCrawlService.crawlCarBrandAndSeriesAndMakes();

		standardInfoCrawlService.crawlStandardPartAndAssemblyInfo();

		int count       = carModelRepo.countDistinctModelId();
		int pagePerSize = 500;
		int totalPages  = count / pagePerSize + 1;

		ExecutorService es = Executors.newFixedThreadPool(30);
		for (int pageNumber = 1; pageNumber <= totalPages; pageNumber++) {
			List<CarModelEntity> carModelEntityList = carModelRepo.selectGroupByModelId(pageNumber, pagePerSize, null);//22467
			es.execute(() -> {
				for (CarModelEntity carModelEntity : carModelEntityList) {
					carAssemblyInfoCrawlService.crawlCarAssemblyAndPicInfo(carModelEntity.getModelId());
					//partCrawlService.crawlModelPartByModelId(carModelEntity.getModelId());//TODO 查master
				}
			});
		}

		es.shutdown();
		try {
			boolean finshed = es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error("awaitTermination error", e);
		}
		logger.info("craw car model finished !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

		/*logger.info("craw part start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

		int codeCount = partCodeRepo.selectCount();
		int pageSize  = 500;
		int total     = codeCount / pageSize + 1;

		for (int pageNumber = 1; pageNumber <= total; pageNumber++) {
			List<PartCodeEntity> partCodeEntities = partCodeRepo.selectPage(pageNumber, pageSize);
			if (CollectionUtils.isEmpty(partCodeEntities)) {
				continue;
			}
			List<String> partCodes = partCodeEntities.stream().map(PartCodeEntity::getPartCode).collect(Collectors.toList());
			if (CollectionUtils.isEmpty(partCodes)) {
				continue;
			}

			long currentT = System.currentTimeMillis();
			logger.info("=>开始执行适配车型数据.partCode:{}", JSON.toJSONString(partCodes));
			partAdapterCarModelCrawlService.crawlPartAdapterCarModel(partCodes);
			logger.info("=>执行适配车型数据完成.partCode:{},执行时间:{}", JSON.toJSONString(partCodes), (currentT - System.currentTimeMillis()) / 1000);

			partCodes.forEach(p -> {

				replacePartCrawlService.crawlReplacePart(p);

				part4sPriceCrawlService.crawlPart4sPriceInfo(p);
			});
		}
*/
		logger.info("Full craw finished !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");


	}
}

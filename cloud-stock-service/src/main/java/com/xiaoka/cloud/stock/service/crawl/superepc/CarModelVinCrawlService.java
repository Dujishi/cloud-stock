package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelVinRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelVinEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCVtmService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTidResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.SuperEPCResp;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 根据车架号获取tid（参见接口1.1），然后保存到表:car_model_vin中
 * Created by sabo on 20/11/2017.
 */
@Service
public class CarModelVinCrawlService {

	private static final Logger logger = LoggerFactory.getLogger(CarModelVinCrawlService.class);

	@Resource
	private SuperEPCVtmService superEPCVtmService;

	@Resource
	CarModelVinRepo carModelVinRepo;

	public void crawlCarModelVin(String vin) {
		logger.info("Prepare for crawl car model with vin={}", vin);
		SuperEPCResp<List<GetTidResp>> resp = superEPCVtmService.getTid(vin, null);
		if (resp != null && CollectionUtils.isNotEmpty(resp.getResult()) && StringUtils.isNotBlank(resp.getResult().get(0).getTid())) {
			logger.info("Successfully crawl car model=={} with vin==={}", resp.getResult().get(0).getTid(), vin);
			String[] tids = resp.getResult().get(0).getTid().split(SuperEPCconstant.SEP_COMMA);
			if (tids != null && tids.length > 0) {
				List<CarModelVinEntity> carModelVinEntityList = new ArrayList<>();
				for (String tid : tids) {
					CarModelVinEntity carModelVinEntity = new CarModelVinEntity();
					carModelVinEntity.setVin(vin);
					carModelVinEntity.setModelId(Integer.valueOf(tid));
					carModelVinEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
					carModelVinEntity.setIsValid(1);
					carModelVinEntityList.add(carModelVinEntity);
				}
				//去重逻辑
				List<CarModelVinEntity> existedCarModelVinEntities = carModelVinRepo.selectByVinModelIdList(carModelVinEntityList);
				if (CollectionUtils.isEmpty(existedCarModelVinEntities)) {
					carModelVinRepo.batchInsert(carModelVinEntityList);
				} else {
					List<CarModelVinEntity> insertList = Lists.newArrayList();
					insertList.addAll(carModelVinEntityList.stream()
					                                       .filter(p -> existedCarModelVinEntities.stream().noneMatch(
							                                       x -> Objects.equals(x.getVin(), p.getVin())
									                                       && Objects.equals(x.getModelId(), p.getModelId())))
					                                       .collect(Collectors.toList()));
				}
			}
		} else {
			logger.info("Cannot find car model with vin==={}", vin);
		}
	}


}

package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelPicRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.cloud.stock.service.crawl.superepc.CarAssemblyInfoCrawlService;
import com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.freework.utils.lock.ClusterLock;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 车型分类(总成、分总成)通知
 *
 * @author gancao 2017/11/25 14:15
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ModelAssemblyMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelAssemblyMessageHandlerService.class);

	@Resource
	private CarAssemblyInfoCrawlService carAssemblyInfoCrawlService;
	@Resource
	CarModelPicRepo carModelPicRepo;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (Objects.isNull(epcNotifyDto.getModelId())) {
			LOGGER.info("处理车型分类时车型id为空不做处理");
			return null;
		}
		List<EpcNotifyDto> chainDtoList = null;
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(
				EPCMessageTypeEnum.车型分类同步通知.getLockUrl(), epcNotifyDto.getModelId().toString());
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				chainDtoList = locker.execute(() -> {
					//处理车型分类、总成、分总成和图片
					carAssemblyInfoCrawlService.crawlCarAssemblyAndPicInfo(epcNotifyDto.getModelId());
					List<CarModelPicEntity> carModelPicEntityList = carModelPicRepo.selectGroupPicByModelId(epcNotifyDto.getModelId());
					return getEpcNotifyDtoList(carModelPicEntityList);
				});
			}
		} catch (Exception e) {
			LOGGER.error("处理车型分类异常,车型:{}", epcNotifyDto.getModelId(), e);
			throw new RuntimeException(e);
		}
		return chainDtoList;
	}

	private List<EpcNotifyDto> getEpcNotifyDtoList(List<CarModelPicEntity> carModelPicEntityList){
		List<EpcNotifyDto> resultList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(carModelPicEntityList)) {
			return resultList;
		}
		//以图号聚合数据
		Map<String, List<CarModelPicEntity>> map = carModelPicEntityList.stream().collect(Collectors.groupingBy(CarModelPicEntity::getPicNum));
		map.forEach((k ,v) -> {
			//图号取一个就行
			EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
			epcNotifyDto.setType(v.get(0).getType());
			epcNotifyDto.setPicNum(k);
			epcNotifyDto.setModelId(v.get(0).getModelId());
		});
		return resultList;
	}
}

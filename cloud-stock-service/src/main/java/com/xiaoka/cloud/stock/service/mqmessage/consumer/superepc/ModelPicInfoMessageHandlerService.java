package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.crawl.superepc.CarAssemblyInfoCrawlService;
import com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.freework.utils.lock.ClusterLock;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 车型图号信息
 *
 * @author gancao 2017/11/25 11:34
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ModelPicInfoMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelPicInfoMessageHandlerService.class);

	@Resource
	private CarAssemblyInfoCrawlService carAssemblyInfoCrawlService;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (!verifyParam(epcNotifyDto)) {
			return null;
		}
		List<EpcNotifyDto> chainDtoList = null;
		ClusterLock.Locker locker = getClusterLock(epcNotifyDto);
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				chainDtoList = locker.execute(()->{
					List<CarModelPicEntity> carModelPicEntityList = carAssemblyInfoCrawlService.crawlCarPicInfo(epcNotifyDto.getModelId(),
							Collections.singletonList(BeanUtils.transform(CarModelCategoryEntity.class, epcNotifyDto)), epcNotifyDto.getType());
					if (CollectionUtils.isNotEmpty(carModelPicEntityList)) {
						return carModelPicEntityList.stream().map(p -> BeanUtils.transform(EpcNotifyDto.class, p)).collect(Collectors.toList());
					}
					return null;
				});

			}
		} catch (Exception e) {
			LOGGER.error("处理车型图号下的配件异常,车型:{},零件码:{}", epcNotifyDto.getModelId(), epcNotifyDto.getPicNum(), e);
			throw new RuntimeException(e);
		}
		//下级链路返回的车型图号对象集合
		return chainDtoList;
	}

	private boolean verifyParam(EpcNotifyDto epcNotifyDto) {
		boolean result = false;
		if (Objects.nonNull(epcNotifyDto.getType())) {
			if (epcNotifyDto.getType() == 1) {//正时分类
				if (Objects.nonNull(epcNotifyDto.getSubAssemblyId())) {
					result = true;
				} else {
					LOGGER.info("正时分类分总成id不能为空");
				}
			} else if (epcNotifyDto.getType() == 0) {//原厂分类
				if (StringUtils.isNotBlank(epcNotifyDto.getAssemblyName()) && StringUtils.isNotBlank(epcNotifyDto.getSubAssemblyName())) {
					result = true;
				} else {
					LOGGER.info("原厂分类总成名称和分总成名称不能为空");
				}
			}

		} else {
			LOGGER.info("分类类型不能为空");
		}
		return result;
	}

	private ClusterLock.Locker getClusterLock(EpcNotifyDto epcNotifyDto) {
		ClusterLock.Locker locker = null;
		if (epcNotifyDto.getType() == 1) {
			locker = Utils.get(ClusterLock.class).transiantLock(
					EPCMessageTypeEnum.车型分总成图片通知.getLockUrl(), epcNotifyDto.getModelId().toString().concat(":" + epcNotifyDto.getSubAssemblyId()));
		} else if (epcNotifyDto.getType() == 0) {
			locker = Utils.get(ClusterLock.class).transiantLock(EPCMessageTypeEnum.车型分总成图片通知.getLockUrl(),
					epcNotifyDto.getModelId().toString().concat(epcNotifyDto.getAssemblyName()).concat(epcNotifyDto.getSubAssemblyName()));
		}
		return locker;
	}
}

package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.crawl.superepc.PartCrawlService;
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
 * 车型和图号同步车型配件
 *
 * @author gancao 2017/11/24 20:40
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ModelPicNumPartMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelPicNumPartMessageHandlerService.class);

	@Resource
	private PartCrawlService partCrawlService;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (StringUtils.isBlank(epcNotifyDto.getPicNum()) || Objects.isNull(epcNotifyDto.getModelId())) {
			LOGGER.info("零件号或者车型id为空不做处理");
			return null;
		}
		List<EpcNotifyDto> chainDtoList = null;
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(
				EPCMessageTypeEnum.车型图号下的零件通知.getLockUrl(), epcNotifyDto.getModelId().toString().concat(epcNotifyDto.getPicNum()));
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				List<CarModelPartEntity> carModelPartEntityList = locker.execute(() -> partCrawlService
						.crawModelPartByModelIdAndPicNum(epcNotifyDto.getModelId(), Collections.singletonList(epcNotifyDto.getPicNum())));
				if (CollectionUtils.isNotEmpty(carModelPartEntityList)) {
					chainDtoList = carModelPartEntityList.stream().map(p -> BeanUtils.transform(EpcNotifyDto.class, p)).collect(Collectors.toList());
				}
			}
		} catch (Exception e) {
			LOGGER.error("处理车型图号下的配件异常,车型:{},零件码:{}", epcNotifyDto.getModelId(), epcNotifyDto.getPicNum(), e);
			throw new RuntimeException(e);
		}
		//下级链路返回的配件对象集合
		return chainDtoList;
	}

}

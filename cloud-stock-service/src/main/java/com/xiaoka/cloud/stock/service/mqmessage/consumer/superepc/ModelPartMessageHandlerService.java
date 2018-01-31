package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.service.crawl.superepc.PartCrawlService;
import com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.freework.utils.lock.ClusterLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 车型零件信息获取
 *
 * @author gancao 2017/11/24 20:15
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ModelPartMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelPartMessageHandlerService.class);

	@Resource
	private PartCrawlService partCrawlService;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (StringUtils.isBlank(epcNotifyDto.getPartCode()) || Objects.isNull(epcNotifyDto.getModelId())) {
			LOGGER.info("零件号或者车型id为空不做处理");
			return null;
		}
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(
				EPCMessageTypeEnum.车型零件通知.getLockUrl(), epcNotifyDto.getModelId().toString().concat(epcNotifyDto.getPartCode()));
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				locker.execute(() -> {
					partCrawlService.crawModelPartByModeIdAndPartCode(epcNotifyDto.getModelId(), epcNotifyDto.getPartCode());
					return null;
				});
			}
		} catch (Exception e) {
			LOGGER.error("处理车型零件信息异常,车型:{},零件码:{}", epcNotifyDto.getModelId(), epcNotifyDto.getPartCode(), e);
			throw new RuntimeException(e);
		}
		//下级链路返回原对象(主要是处理配件信息)
		return Collections.singletonList(epcNotifyDto);
	}
}

package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.service.crawl.superepc.PartAdapterCarModelCrawlService;
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
import java.util.concurrent.TimeUnit;

/**
 * 零件适用车型
 *
 * @author gancao 2017/11/24 16:59
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PartAdapterCarModelMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PartAdapterCarModelMessageHandlerService.class);

	@Resource
	private PartAdapterCarModelCrawlService partAdapterCarModelCrawlService;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (StringUtils.isBlank(epcNotifyDto.getPartCode())) {
			LOGGER.info("零件号为空不做处理");
			return null;
		}
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(
				EPCMessageTypeEnum.零件适用车型通知.getLockUrl(), epcNotifyDto.getPartCode());
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				locker.execute(() -> {
					partAdapterCarModelCrawlService.crawlPartAdapterCarModel(Collections.singletonList(epcNotifyDto.getPartCode()));
					return null;
				});
			}
		} catch (Exception e) {
			LOGGER.error("处理零件适用车型异常,零件码:{}", epcNotifyDto.getPartCode(), e);
			throw new RuntimeException(e);
		}
		return null;
	}
}

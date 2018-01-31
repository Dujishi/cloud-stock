package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.service.crawl.superepc.CarInfoCrawlService;
import com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.freework.utils.lock.ClusterLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 车型信息同步通知
 *
 * @author gancao 2017/11/25 15:01
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ModelInfoMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelInfoMessageHandlerService.class);

	@Resource
	private CarInfoCrawlService carInfoCrawlService;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (Objects.isNull(epcNotifyDto.getModelId())) {
			LOGGER.info("处理车型信息时车型id为空不做处理");
			return null;
		}
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).lock(EPCMessageTypeEnum.车型同步通知.getLockUrl());
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				locker.execute(() -> {
					//车型的整表查询
					carInfoCrawlService.crawlAllCarModel(-1);
					return null;
				});
			}
		} catch (Exception e) {
			LOGGER.error("处理车型信息异常,车型:{}", epcNotifyDto.getModelId(), e);
			throw new RuntimeException(e);
		}
		//下级链路处理车型分类
		return Collections.singletonList(epcNotifyDto);
	}
}

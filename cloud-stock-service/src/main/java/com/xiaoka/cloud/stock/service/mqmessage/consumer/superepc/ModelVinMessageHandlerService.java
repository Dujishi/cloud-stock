package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.service.crawl.superepc.CarModelVinCrawlService;
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
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 处理vin码对应车型同步通知
 *
 * @author gancao 2017/11/25 15:29
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ModelVinMessageHandlerService extends AbstractMessageHandlerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ModelVinMessageHandlerService.class);

	@Resource
	private CarModelVinCrawlService carModelVinCrawlService;

	@Override
	protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto) {
		LOGGER.info("当前链路处理的消息内容是:{}", Jackson.mobile().writeValueAsString(epcNotifyDto));
		if (StringUtils.isBlank(epcNotifyDto.getVin())) {
			LOGGER.info("处理vin码适配车型id时vin码为空不做处理");
			return null;
		}
		ClusterLock.Locker locker = Utils.get(ClusterLock.class).lock(EPCMessageTypeEnum.VIN码同步通知.getLockUrl());
		try {
			if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {
				locker.execute(() -> {
					//处理适配
					carModelVinCrawlService.crawlCarModelVin(epcNotifyDto.getVin());
					return null;
				});
			}
		} catch (Exception e) {
			LOGGER.error("处理vin码适配车型id异常,vin码:{}", epcNotifyDto.getVin(), e);
			throw new RuntimeException(e);
		}
		return null;
	}
}

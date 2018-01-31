package com.xiaoka.cloud.stock.service.mqmessage.consumer;

import com.google.common.collect.ImmutableList;
import com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum;
import com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc.AbstractMessageHandlerService;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcMessageEvent;
import com.xiaoka.ddyc.tool.mq.XkMessageListener;
import com.xiaoka.freework.utils.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * epc消息监听处理服务
 *
 * @author gancao 2017/11/25 15:53
 * @see [相关类/方法]
 * @since [版本号]
 */
@Component
public class EpcMessageConsumerListener implements InitializingBean, XkMessageListener<EpcMessageEvent> {

	private static final Logger LOGGER = LoggerFactory.getLogger(EpcMessageConsumerListener.class);

	@Resource
	private AbstractMessageHandlerService modelAssemblyMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService modelInfoMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService modelPartMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService modelPicInfoMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService modelPicNumPartMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService modelVinMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService part4sPriceMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService partAdapterCarModelMessageHandlerService;
	@Resource
	private AbstractMessageHandlerService partReplaceMessageHandlerService;

	@Override
	public void onApplicationEvent(EpcMessageEvent event) {
		LOGGER.info("进入EPC消息同步处理，消息体{}", Jackson.mobile().writeValueAsString(event));
		if (Objects.nonNull(event)) {
			AbstractMessageHandlerService messageHandlerService = getMessageHandlerService(event.getMessageType());
			if (Objects.nonNull(messageHandlerService)) {
				//执行消息处理 //// TODO: 2017/11/29  代码先注释，因为授权码访问次数有所限制
				//messageHandlerService.startMessageHandler(event.getEpcNotifyDto());
			}
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//设置各消息处理器的责任链关系
		List<AbstractMessageHandlerService> handlerServiceArrayList = new ArrayList<>(
				ImmutableList.of(part4sPriceMessageHandlerService, partAdapterCarModelMessageHandlerService, partReplaceMessageHandlerService));
		//vin码只更新匹配不判断车型是否更新
		//车型更新的下级链路应该是处理分类，考虑到车型更新只能整表所以暂时不设置下级链路，分类不存在的车型走分类的消息通知
		modelAssemblyMessageHandlerService.setNextAbstractHandlerList(Collections.singletonList(modelPicInfoMessageHandlerService));
		modelPicInfoMessageHandlerService.setNextAbstractHandlerList(Collections.singletonList(modelPicNumPartMessageHandlerService));
		modelPartMessageHandlerService.setNextAbstractHandlerList(handlerServiceArrayList);
		modelPicNumPartMessageHandlerService.setNextAbstractHandlerList(handlerServiceArrayList);
	}

	/**
	 * 获取具体业务的消息处理器
	 *
	 * @param messageType
	 * @return
	 */
	private AbstractMessageHandlerService getMessageHandlerService(Integer messageType) {
		if (Objects.isNull(messageType)) {
			return null;
		}
		EPCMessageTypeEnum epcMessageTypeEnum = EPCMessageTypeEnum.getEPCMessageTypeEnumByType(messageType);
		if (Objects.nonNull(epcMessageTypeEnum)) {
			switch (epcMessageTypeEnum) {
			case VIN码同步通知:
				return modelVinMessageHandlerService;
			case 车型同步通知:
				return modelInfoMessageHandlerService;
			case 车型分类同步通知:
				return modelAssemblyMessageHandlerService;
			case 车型分总成图片通知:
				return modelPicInfoMessageHandlerService;
			case 车型图号下的零件通知:
				return modelPicNumPartMessageHandlerService;
			case 车型零件通知:
				return modelPartMessageHandlerService;
			case 零件适用车型通知:
				return partAdapterCarModelMessageHandlerService;
			case 零件4S店价格通知:
				return part4sPriceMessageHandlerService;
			case 零件替换件通知:
				return partReplaceMessageHandlerService;
			}
		}
		return null;
	}
}

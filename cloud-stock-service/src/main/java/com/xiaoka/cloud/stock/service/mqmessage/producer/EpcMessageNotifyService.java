package com.xiaoka.cloud.stock.service.mqmessage.producer;

import com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcMessageEvent;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import com.xiaoka.ddyc.tool.mq.MessageEventFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * EPC消息通知服务
 *
 * @author gancao 2017/11/26 15:19
 * @see [相关类/方法]
 * @since [版本号]
 */
public class EpcMessageNotifyService {

	private static final Logger logger = LoggerFactory.getLogger(EpcMessageNotifyService.class);

	@Resource
	private MessageEventFactory messageEventFactory;

	public void notifyVinUpdate(String vin) {
		logger.info("消息通知更新vin:{}的适配车型", vin);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.VIN码同步通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setVin(vin);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);

	}

	public void notifyCarModelUpdate(Integer modelId) {
		logger.info("消息通知更新车型id:{}的基础信息", modelId);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.车型同步通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setModelId(modelId);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}

	public void notifyCarModelAssemblyUpdate(Integer modelId) {
		logger.info("消息通知更新车型id:{}的分类总成分总成消息", modelId);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.车型分类同步通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setModelId(modelId);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}

	public void notifyCarModelPicInfoUpdate(Integer modelId, Integer type, Integer subAssemblyId, String assemblyName, String subAssemblyName) {
		logger.info("消息通知更新车型id:{},分类类型:{},子总成id:{},总成名:{},子总成名:{}的图号信息", modelId, type, subAssemblyId, assemblyName, subAssemblyName);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.车型分总成图片通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setModelId(modelId);
		epcNotifyDto.setSubAssemblyId(subAssemblyId);
		epcNotifyDto.setType(type);
		epcNotifyDto.setAssemblyName(assemblyName);
		epcNotifyDto.setSubAssemblyName(subAssemblyName);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}

	public void notifyCarModelPartUpdate(Integer modelId, String picNum, String partCode) {
		logger.info("车型id:{},图号:{},零件号:{},通知更新车型配件", modelId, picNum, partCode);
		EpcMessageEvent event= new EpcMessageEvent();
		if (StringUtils.isNotBlank(partCode)){
			event.setMessageType(EPCMessageTypeEnum.车型零件通知.getType());
		}
		if (StringUtils.isNotBlank(picNum)){
			event.setMessageType(EPCMessageTypeEnum.车型图号下的零件通知.getType());
		}
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setModelId(modelId);
		epcNotifyDto.setPicNum(picNum);
		epcNotifyDto.setPartCode(partCode);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}

	public void notifyPartAdapterCarModelUpdate(String partCode) {
		logger.info("零件号:{}通知更新适用车型", partCode);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.零件适用车型通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setPartCode(partCode);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}

	public void notifyPartReplaceUpdate(String partCode) {
		logger.info("零件号:{}通知更新替换件", partCode);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.零件替换件通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setPartCode(partCode);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}

	public void notifyPart4SPriceUpdate(String partCode) {
		logger.info("零件号:{}通知更新4S店价格", partCode);
		EpcMessageEvent event= new EpcMessageEvent();
		event.setMessageType(EPCMessageTypeEnum.零件4S店价格通知.getType());
		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setPartCode(partCode);
		event.setEpcNotifyDto(epcNotifyDto);
		messageEventFactory.publishEvent(event);
	}
}

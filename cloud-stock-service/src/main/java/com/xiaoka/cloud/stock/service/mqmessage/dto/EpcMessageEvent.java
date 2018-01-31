package com.xiaoka.cloud.stock.service.mqmessage.dto;

import com.xiaoka.ddyc.tool.mq.XkMessageEvent;

/**
 * EPC数据同步消息体
 *
 * @author gancao 2017/11/25 15:45
 * @see [相关类/方法]
 * @since [版本号]
 */
public class EpcMessageEvent implements XkMessageEvent {

	@Override public String name() {
		return "epcNotifyEvent";
	}

	/**
	 * 消息类型
	 * {@link com.xiaoka.cloud.stock.service.mqmessage.constant.EPCMessageTypeEnum}
	 */
	private Integer messageType;
	/**
	 * 消息内容
	 */
	private EpcNotifyDto epcNotifyDto;

	public Integer getMessageType() {
		return messageType;
	}

	public void setMessageType(Integer messageType) {
		this.messageType = messageType;
	}

	public EpcNotifyDto getEpcNotifyDto() {
		return epcNotifyDto;
	}

	public void setEpcNotifyDto(EpcNotifyDto epcNotifyDto) {
		this.epcNotifyDto = epcNotifyDto;
	}
}

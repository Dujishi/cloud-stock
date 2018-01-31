package com.xiaoka.cloud.stock.service.mqmessage.producer;

import com.xiaoka.cloud.stock.service.mqmessage.dto.CsOrderStatusEvent;
import com.xiaoka.ddyc.tool.mq.MessageEventFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CsOrderStatusChangeNotifyService {

	private static final Logger logger = LoggerFactory.getLogger(CsOrderStatusChangeNotifyService.class);

	@Resource
	private MessageEventFactory messageEventFactory;

	public void notifyFinished(Integer orderId) {

		CsOrderStatusEvent csOrderStatusEvent = new CsOrderStatusEvent();
		csOrderStatusEvent.setOrderId(orderId);
		messageEventFactory.publishEvent(csOrderStatusEvent);
	}


}

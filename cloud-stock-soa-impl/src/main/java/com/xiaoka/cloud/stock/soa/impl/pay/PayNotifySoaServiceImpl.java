package com.xiaoka.cloud.stock.soa.impl.pay;

import com.xiaoka.cloud.stock.service.order.OrderService;
import com.xiaoka.cloud.stock.soa.api.pay.PayNotifySoaService;
import com.xiaoka.cloud.stock.soa.api.pay.dto.PayNotifyDto;
import com.xiaoka.freework.utils.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("payNotifySoaService")
public class PayNotifySoaServiceImpl implements PayNotifySoaService {

	private Logger logger = LoggerFactory.getLogger(PayNotifySoaServiceImpl.class);

	@Resource
	OrderService orderService;


	@Override public String notify(PayNotifyDto payNotifyDto) {
		// 记录回调信息
		logger.info("centerpay notify ======{}", Jackson.base().writeValueAsString(payNotifyDto));

		// 空值校验
		if (null == payNotifyDto || (null == payNotifyDto.getOrderId() && null == payNotifyDto.getOrderIdList())) {
			logger.error("centerpay:notify is null ");
			return "fail";
		}

		// 业务处理
		try {
			orderService.updateStatusToPaid(payNotifyDto.getOrderId());
		} catch (Exception e) {
			logger.error("centerpay notify error", e);
			return "fail";
		}
		return "success";
	}
}

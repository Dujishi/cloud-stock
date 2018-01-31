package com.xiaoka.cloud.stock.soa.api.order;

import com.xiaoka.cloud.stock.soa.api.order.dto.CsOrderDto;

public interface CsIndentOrderSoaService {

	CsOrderDto getOrderDetailForPay(String payUrlCode);

	/**
	 * 根据订单ID获取订单详情
	 * @param orderId
	 * @return
	 */
	CsOrderDto getOrderById(Integer orderId);
}

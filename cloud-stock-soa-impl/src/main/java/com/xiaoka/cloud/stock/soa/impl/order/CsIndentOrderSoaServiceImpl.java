package com.xiaoka.cloud.stock.soa.impl.order;

import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.order.BigDecimalUtil;
import com.xiaoka.cloud.stock.service.order.OrderService;
import com.xiaoka.cloud.stock.service.order.OrderStatus;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.service.order.impl.IndentHelper;
import com.xiaoka.cloud.stock.soa.api.order.CsIndentOrderSoaService;
import com.xiaoka.cloud.stock.soa.api.order.dto.CsOrderDto;
import com.xiaoka.shop.care.soa.api.shop.ShopCareSoaService;
import com.xiaoka.shop.care.soa.api.shop.result.ShopResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("csIndentOrderSoaService")
public class CsIndentOrderSoaServiceImpl implements CsIndentOrderSoaService{

	private Logger logger = LoggerFactory.getLogger(CsIndentOrderSoaServiceImpl.class);

	@Resource
	OrderService orderService;

	@Resource
	ShopCareSoaService shopCareSoaService;

	@Resource
	IndentHelper indentHelper;

	@Override
	public CsOrderDto getOrderDetailForPay(String payUrlCode) {
		logger.info("getOrderDetailForPay with payUrlCode={}", payUrlCode);
		OrderDto orderDto = orderService.getOrderDetailForPay(payUrlCode);
		CsOrderDto csOrderDto = BeanUtils.transform(CsOrderDto.class, orderDto);
		csOrderDto.setPaid(OrderStatus.Paid.getCode().equals(orderDto.getStatus()));
		//当前登录的配件商信息
		ShopResult currentShop = shopCareSoaService.getShopById(indentHelper.getSellerIdByCode(orderDto.getSellerCode()));
		csOrderDto.setSellerName(currentShop.getCareShopName());
		csOrderDto.setSellerPhone(currentShop.getContactPhone());
		csOrderDto.setDiscountPrice(BigDecimalUtil.scaleOf(csOrderDto.getDiscountPrice()));
		csOrderDto.setPayPrice(BigDecimalUtil.scaleOf(csOrderDto.getPayPrice()));
		csOrderDto.setTotal(BigDecimalUtil.scaleOf(csOrderDto.getTotal()));
		return csOrderDto;
	}

	@Override
	public CsOrderDto getOrderById(Integer orderId) {
		logger.info("getOrderDetailById with orderId={}", orderId);
		OrderDto orderDto = orderService.getOrderById(orderId);
		CsOrderDto csOrderDto =  BeanUtils.transform(CsOrderDto.class, orderDto);
		csOrderDto.setPaid(OrderStatus.Paid.getCode().equals(orderDto.getStatus()));
		csOrderDto.setSellerId(indentHelper.getSellerIdByCode(csOrderDto.getSellerCode()));
		return csOrderDto;
	}
}

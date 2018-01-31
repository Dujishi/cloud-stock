package com.xiaoka.cloud.stock.service.order.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndentHelper {

	private static final String SELLER_PREFIX = "S";
	private static final String BUYER_PREFIX = "B";
	private static final String INDENT_NO_PREFIX = "PHD";
	private static final String ORDER_NO_PREFIX = "DD";


	public String getSellerCode(Integer supplierId) {
		return SELLER_PREFIX + supplierId;
	}

	public Integer getSellerIdByCode(String code) {
		if (StringUtils.isBlank(code)) {
			return null;
		}
		return Integer.parseInt(code.substring(1));
	}

	public String getBuyerCodeByCompanyName(Integer buyerId) {
		return BUYER_PREFIX + buyerId;
	}

	public String getBuyerCodeByContactPhone(String phone) {
		return BUYER_PREFIX + phone;
	}

	public String getIndentNo(Integer indentId) {
		return INDENT_NO_PREFIX + indentId;
	}

	public String getOrderNo(Integer orderId) {
		return ORDER_NO_PREFIX + orderId;
	}

	public String getPayUrlCode() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}



}

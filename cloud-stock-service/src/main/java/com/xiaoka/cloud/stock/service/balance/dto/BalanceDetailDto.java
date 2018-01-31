package com.xiaoka.cloud.stock.service.balance.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaoka.cloud.stock.service.core.json.MoneySerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class BalanceDetailDto implements Serializable{
	/**
	 * 服务名称
	 */
	private String serviceName;
	/**
	 * 金额
	 */
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal amount;
	/**
	 * 余额
	 */
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal balanceAmount;

	/**
	 * 日期
	 */
	private Date date;

	/**
	 * 状态描述
	 */
	private String statusDesc;

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getBalanceAmount() {
		return balanceAmount;
	}

	public void setBalanceAmount(BigDecimal balanceAmount) {
		this.balanceAmount = balanceAmount;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
}

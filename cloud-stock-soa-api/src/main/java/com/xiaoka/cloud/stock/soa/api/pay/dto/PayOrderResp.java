/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: OrderResponse.java
 * Author:   Cheng Zhujiang
 * Date:     Apr 1, 2015 6:02:43 PM
 * Description: 
 */
package com.xiaoka.cloud.stock.soa.api.pay.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 支付订单响应
 * 支付系统回调商户后台的统一响应
 * 
 * @author Cheng Zhujiang
 * @see 支付系统通知接口
 * @since 1.0
 */
public class PayOrderResp<T> implements Serializable {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** 签名是否通过 */
	private boolean signPassed;

	/** 编码 0-交易成功,1-等待买家付款,2-退款成功,3-退款失败,4-退款中,5-交易中,6-交易失败,7-交易关闭,8-交易撤销,9-退款关闭 */
	private int code;

	/** 错误描述 */
	private String msg;

	/** 外部交易号:商户订单号 */
	private String outTradeNo;

	/** 交易号:在支付系统中唯一 */
	private String tradeNo;

	/** 支付金额 单位为分 */
	private Integer amount;

	/** 支付时间 */
	private Date orderPaidTime;

	/** 退款时间 */
	private Date refundTime;

	/** 订单关闭时间 */
	private Date closeTime;

	/** 支付回调信息实体 */
	private T notifyInfo;

	public boolean isSignPassed() {
		return signPassed;
	}

	public void setSignPassed(boolean signPassed) {
		this.signPassed = signPassed;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getOutTradeNo() {
		return outTradeNo;
	}

	public void setOutTradeNo(String outTradeNo) {
		this.outTradeNo = outTradeNo;
	}

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Date getOrderPaidTime() {
		return orderPaidTime;
	}

	public void setOrderPaidTime(Date orderPaidTime) {
		this.orderPaidTime = orderPaidTime;
	}

	public Date getRefundTime() {
		return refundTime;
	}

	public void setRefundTime(Date refundTime) {
		this.refundTime = refundTime;
	}

	public Date getCloseTime() {
		return closeTime;
	}

	public void setCloseTime(Date closeTime) {
		this.closeTime = closeTime;
	}

	public T getNotifyInfo() {
		return notifyInfo;
	}

	public void setNotifyInfo(T notifyInfo) {
		this.notifyInfo = notifyInfo;
	}

}

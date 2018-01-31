/*
 * Copyright (C), 2014-2016, 杭州小卡科技有限公司
 * FileName: PrepayReq.java
 * Author:   Cheng Zhujiang
 * Date:     Feb 19, 2016 9:53:23 AM
 * Description: 
 */
package com.xiaoka.cloud.stock.soa.api.pay.dto;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * 支付通知DTO
 * 支付完成后,通知业务系统的对象
 *
 * @author Cheng Zhujiang
 * @see com.xiaoka.common.pay.notify.entity.PayNotifyInfo
 * @since 1.0
 */
public class PayNotifyDto implements Serializable {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** 订单Id 订单唯一标识 */
	private Integer orderId;

	/** 支付金额 单位为分 */
	private Integer payAmount;

	/** 支付方式: 0-余额,1-线下,2-支付宝,3-微信,4-联动,5-快钱,6-宝钢,7-盒子,10-招行,见PayPlatForm */
	private Integer payMethod;

	/** 支付类型: 0-全款,1-预付款,2-尾款,3-补缴款,见PayTypeEnum 可选 默认为0 */
	private Integer payType;

	/** 支付时间,单位为毫秒 */
	private Long payTime;

	// 招行特殊字段 <--
	// 身份证hash值
	private String custPidV;

	/**批量订单**/
	private List<Integer> orderIdList;

	private PayOrderResp<?> resp;

	private Map<String, Object> propertiesMap;

	public PayOrderResp<?> getResp() {
		return resp;
	}

	public void setResp(PayOrderResp<?> resp) {
		this.resp = resp;
	}

	public String getCustPidV() {
		return custPidV;
	}

	public void setCustPidV(String custPidV) {
		this.custPidV = custPidV;
	}
	/**
	 * Y:有优惠 N：无优惠。
	 */
//	private String discountFlag;
	/**
	 * 优惠金额，格式：xxxx.xx
	 */
//	private BigDecimal discountAmt;
	// 招行特殊字段 -->
//
//	public String getDiscountFlag() {
//		return discountFlag;
//	}
//
//	public void setDiscountFlag(String discountFlag) {
//		this.discountFlag = discountFlag;
//	}
//
//	public BigDecimal getDiscountAmt() {
//		return discountAmt;
//	}
//
//	public void setDiscountAmt(BigDecimal discountAmt) {
//		this.discountAmt = discountAmt;
//	}

	/**
	 * @return the orderId
	 */
	public Integer getOrderId() {
		return orderId;
	}

	/**
	 * @param orderId the orderId to set
	 */
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	/**
	 * @return the payAmount
	 */
	public Integer getPayAmount() {
		return payAmount;
	}

	/**
	 * @param payAmount the payAmount to set
	 */
	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}

	/**
	 * @return the payMethod
	 */
	public Integer getPayMethod() {
		return payMethod;
	}

	/**
	 * @param payMethod the payMethod to set
	 */
	public void setPayMethod(Integer payMethod) {
		this.payMethod = payMethod;
	}

	/**
	 * @return the payType
	 */
	public Integer getPayType() {
		return payType;
	}

	/**
	 * @param payType the payType to set
	 */
	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	/**
	 * @return the payTime
	 */
	public Long getPayTime() {
		return payTime;
	}

	/**
	 * @param payTime the payTime to set
	 */
	public void setPayTime(Long payTime) {
		this.payTime = payTime;
	}


	public List<Integer> getOrderIdList() {
		return orderIdList;
	}

	public void setOrderIdList(List<Integer> orderIdList) {
		this.orderIdList = orderIdList;
	}

	public Map<String, Object> getPropertiesMap() {
		return propertiesMap;
	}

	public void setPropertiesMap(Map<String, Object> propertiesMap) {
		this.propertiesMap = propertiesMap;
	}
}

package com.xiaoka.cloud.stock.service.order.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaoka.cloud.stock.service.core.json.MoneySerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class OrderPartDto implements Serializable{

	private Integer id;
	private String orderNo;
	private String buyerCode;
	private String sellerCode;
	private Integer csIndentPartId;
	private String oeNo;
	private String partName;
	private String originPlace;
	private String manufacturer;
	private String partDepot;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal unitPrice;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal amount;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal subtotal;
	private Boolean isValid;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getBuyerCode() {
		return buyerCode;
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode;
	}

	public String getSellerCode() {
		return sellerCode;
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode;
	}

	public Integer getCsIndentPartId() {
		return csIndentPartId;
	}

	public void setCsIndentPartId(Integer csIndentPartId) {
		this.csIndentPartId = csIndentPartId;
	}

	public String getOeNo() {
		return oeNo;
	}

	public void setOeNo(String oeNo) {
		this.oeNo = oeNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getPartDepot() {
		return partDepot;
	}

	public void setPartDepot(String partDepot) {
		this.partDepot = partDepot;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public Boolean getValid() {
		return isValid;
	}

	public void setValid(Boolean valid) {
		isValid = valid;
	}
}

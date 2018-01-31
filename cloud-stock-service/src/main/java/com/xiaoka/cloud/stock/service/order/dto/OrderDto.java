package com.xiaoka.cloud.stock.service.order.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.xiaoka.cloud.stock.service.core.json.MoneySerializer;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class OrderDto implements Serializable{

	private Integer id;
	private String orderNo;
	private String buyerCode;
	private String sellerCode;
	private String indentNo;
	private String customerName;
	private String contact;
	private String contactPhone;
	private String province;
	private String city;
	private String district;
	private String address;
	private String vin;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal total;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal discountPrice;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal payPrice;
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal amount;
	private Integer status;
	private Integer subStatus;
	private String payUrlCode;
	private Integer salesmanId;
	private String salesmanName;
	private Date createTime;
	private List<OrderPartDto> orderPartDtoList;

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

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(Integer salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getIndentNo() {
		return indentNo;
	}

	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo;
	}

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public List<OrderPartDto> getOrderPartDtoList() {
		return orderPartDtoList;
	}

	public void setOrderPartDtoList(List<OrderPartDto> orderPartDtoList) {
		this.orderPartDtoList = orderPartDtoList;
	}

	public Integer getSubStatus() {
		return subStatus;
	}

	public void setSubStatus(Integer subStatus) {
		this.subStatus = subStatus;
	}

	public String getPayUrlCode() {
		return payUrlCode;
	}

	public void setPayUrlCode(String payUrlCode) {
		this.payUrlCode = payUrlCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}

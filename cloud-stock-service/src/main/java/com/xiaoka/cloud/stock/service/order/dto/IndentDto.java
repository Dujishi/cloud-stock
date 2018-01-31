package com.xiaoka.cloud.stock.service.order.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class IndentDto implements Serializable{

	private Integer id;
	private String name;
	private String indentNo;
	private String customerName;
	private String contact;
	private String contactPhone;
	private String buyerCode;
	private String sellerCode;
	private String vin;
	private String province;
	private String city;
	private String district;
	private String address;
	private String carModel;
	private BigDecimal discountPrice;
	private Integer salesmanId;
	private String salesmanName;
	private Integer indentStatus;

	private List<IndentPartDto> indentPartDtoList;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIndentNo() {
		return indentNo;
	}

	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo;
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

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
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

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public BigDecimal getDiscountPrice() {
		return discountPrice;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public Integer getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(Integer salesmanId) {
		this.salesmanId = salesmanId;
	}

	public String getSalesmanName() {
		return salesmanName;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName;
	}

	public Integer getIndentStatus() {
		return indentStatus;
	}

	public void setIndentStatus(Integer indentStatus) {
		this.indentStatus = indentStatus;
	}

	public List<IndentPartDto> getIndentPartDtoList() {
		return indentPartDtoList;
	}

	public void setIndentPartDtoList(List<IndentPartDto> indentPartDtoList) {
		this.indentPartDtoList = indentPartDtoList;
	}
}

package com.xiaoka.cloud.stock.core.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * for car brand
 * CsIndentEntity
 *
 * @author auto-generate
 */
public class CsIndentEntity {

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
	private Boolean isValid;
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getIndentNo() {
		return this.indentNo;
	}

	public String getCustomerName() {
		return this.customerName;
	}

	public String getContact() {
		return this.contact;
	}

	public String getContactPhone() {
		return this.contactPhone;
	}

	public String getBuyerCode() {
		return this.buyerCode;
	}

	public String getSellerCode() {
		return this.sellerCode;
	}

	public String getVin() {
		return this.vin;
	}

	public String getProvince() {
		return this.province;
	}

	public String getCity() {
		return this.city;
	}

	public String getDistrict() {
		return this.district;
	}

	public String getAddress() {
		return this.address;
	}

	public String getCarModel() {
		return this.carModel;
	}

	public BigDecimal getDiscountPrice() {
		return this.discountPrice;
	}

	public Integer getSalesmanId() {
		return this.salesmanId;
	}

	public String getSalesmanName() {
		return this.salesmanName;
	}

	public Integer getIndentStatus() {
		return this.indentStatus;
	}

	public Boolean isIsValid() {
		return this.isValid;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo == null ? null : indentNo.trim();
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName == null ? null : customerName.trim();
	}

	public void setContact(String contact) {
		this.contact = contact == null ? null : contact.trim();
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone == null ? null : contactPhone.trim();
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode == null ? null : buyerCode.trim();
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
	}

	public void setVin(String vin) {
		this.vin = vin == null ? null : vin.trim();
	}

	public void setProvince(String province) {
		this.province = province == null ? null : province.trim();
	}

	public void setCity(String city) {
		this.city = city == null ? null : city.trim();
	}

	public void setDistrict(String district) {
		this.district = district == null ? null : district.trim();
	}

	public void setAddress(String address) {
		this.address = address == null ? null : address.trim();
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel == null ? null : carModel.trim();
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setSalesmanId(Integer salesmanId) {
		this.salesmanId = salesmanId;
	}

	public void setSalesmanName(String salesmanName) {
		this.salesmanName = salesmanName == null ? null : salesmanName.trim();
	}

	public void setIndentStatus(Integer indentStatus) {
		this.indentStatus = indentStatus;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
	}

}
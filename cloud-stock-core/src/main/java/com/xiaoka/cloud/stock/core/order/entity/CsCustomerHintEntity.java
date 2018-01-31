package com.xiaoka.cloud.stock.core.order.entity;

import java.util.Date;

/**
 * for car brand
 * CsCustomerHintEntity
 *
 * @author auto-generate
 */
public class CsCustomerHintEntity {

	private Integer id;
	private String sellerCode;
	private String customerName;
	private String contact;
	private String contactPhone;
	private String province;
	private String city;
	private String district;
	private String address;
	private Boolean isValid;
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getSellerCode() {
		return this.sellerCode;
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

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
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
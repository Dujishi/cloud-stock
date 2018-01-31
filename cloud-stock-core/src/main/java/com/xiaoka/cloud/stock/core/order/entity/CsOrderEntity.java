package com.xiaoka.cloud.stock.core.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * for car brand
 * CsOrderEntity
 *
 * @author auto-generate
 */
public class CsOrderEntity {

	private Integer id;
	private String orderNo;
	private String buyerCode;
	private String sellerCode;
	private String indentNo;
	private String customerName;
	private String contact;
	private String contactPhone;
	private String vin;
	private BigDecimal total;
	private BigDecimal discountPrice;
	private BigDecimal payPrice;
	private Integer status;
	private Integer subStatus;
	private String payUrlCode;
	private Integer salesmanId;
	private String salesmanName;
	private Boolean isValid;
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getOrderNo() {
		return this.orderNo;
	}

	public String getBuyerCode() {
		return this.buyerCode;
	}

	public String getSellerCode() {
		return this.sellerCode;
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

	public String getVin() {
		return this.vin;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public BigDecimal getDiscountPrice() {
		return this.discountPrice;
	}

	public Integer getStatus() {
		return this.status;
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

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo == null ? null : orderNo.trim();
	}

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode == null ? null : buyerCode.trim();
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
	}

	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo;
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

	public void setVin(String vin) {
		this.vin = vin == null ? null : vin.trim();
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setDiscountPrice(BigDecimal discountPrice) {
		this.discountPrice = discountPrice;
	}

	public void setStatus(Integer status) {
		this.status = status;
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

	public BigDecimal getPayPrice() {
		return payPrice;
	}

	public void setPayPrice(BigDecimal payPrice) {
		this.payPrice = payPrice;
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
}
package com.xiaoka.cloud.stock.core.order.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * for car brand
 * CsIndentPartEntity
 *
 * @author auto-generate
 */
public class CsIndentPartEntity {

	private Integer id;
	private String indentNo;
	private String buyerCode;
	private String sellerCode;
	private String oeNo;
	private String partName;
	private String originPlace;
	private String manufacturer;
	private String partDepot;
//	private String depotId;
	private BigDecimal unitPrice;
	private BigDecimal amount;
	private BigDecimal subtotal;
	private Boolean isValid;
	private String createBy;
	private Date createTime;
	private String updateBy;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getBuyerCode() {
		return this.buyerCode;
	}

	public String getSellerCode() {
		return this.sellerCode;
	}

	public String getOeNo() {
		return this.oeNo;
	}

	public String getPartName() {
		return this.partName;
	}

	public String getOriginPlace() {
		return this.originPlace;
	}

	public String getManufacturer() {
		return this.manufacturer;
	}

//	public String getDepotId() {
//		return this.depotId;
//	}

	public BigDecimal getUnitPrice() {
		return this.unitPrice;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public BigDecimal getSubtotal() {
		return this.subtotal;
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

	public void setBuyerCode(String buyerCode) {
		this.buyerCode = buyerCode == null ? null : buyerCode.trim();
	}

	public void setSellerCode(String sellerCode) {
		this.sellerCode = sellerCode == null ? null : sellerCode.trim();
	}

	public void setOeNo(String oeNo) {
		this.oeNo = oeNo == null ? null : oeNo.trim();
	}

	public void setPartName(String partName) {
		this.partName = partName == null ? null : partName.trim();
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace == null ? null : originPlace.trim();
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer == null ? null : manufacturer.trim();
	}

//	public void setDepotId(String depotId) {
//		this.depotId = depotId == null ? null : depotId.trim();
//	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
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

	public String getIndentNo() {
		return indentNo;
	}

	public void setIndentNo(String indentNo) {
		this.indentNo = indentNo;
	}

	public String getPartDepot() {
		return partDepot;
	}

	public void setPartDepot(String partDepot) {
		this.partDepot = partDepot;
	}
}
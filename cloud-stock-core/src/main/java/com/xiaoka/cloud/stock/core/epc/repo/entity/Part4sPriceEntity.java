package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Part4sPriceEntity
 *
 * @author suqin
 */
public class Part4sPriceEntity implements Serializable {

	private static final long serialVersionUID = -4602698893443338668L;
	private String partCode;
	private Integer brandId;
	private String brandName;
	private Integer makeId;
	private String makeName;
	private String partCodeTemp;
	private String partName;
	private BigDecimal price;
	private Integer epcNo;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public String getPartCode() {
		return this.partCode;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public Integer getMakeId() {
		return this.makeId;
	}

	public String getMakeName() {
		return this.makeName;
	}

	public String getPartCodeTemp() {
		return this.partCodeTemp;
	}

	public String getPartName() {
		return this.partName;
	}

	public BigDecimal getPrice() {
		return this.price;
	}

	public Integer getIsValid() {
		return this.isValid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode == null ? null : partCode.trim();
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName == null ? null : makeName.trim();
	}

	public void setPartCodeTemp(String partCodeTemp) {
		this.partCodeTemp = partCodeTemp == null ? null : partCodeTemp.trim();
	}

	public void setPartName(String partName) {
		this.partName = partName == null ? null : partName.trim();
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy == null ? null : createBy.trim();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy == null ? null : updateBy.trim();
	}

	public Integer getEpcNo() {
		return epcNo;
	}

	public void setEpcNo(Integer epcNo) {
		this.epcNo = epcNo;
	}
}
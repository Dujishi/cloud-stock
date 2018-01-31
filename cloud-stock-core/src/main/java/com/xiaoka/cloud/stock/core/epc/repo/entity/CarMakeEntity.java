package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * CarMakeEntity
 *
 * @author suqin
 */
public class CarMakeEntity implements Serializable{

	private static final long serialVersionUID = 2936892041795536020L;
	private Integer makeId;
	private String makeName;
	private String viewName;
	private String firstLetter;
	private Integer brandId;
	private String brandName;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getMakeId() {
		return this.makeId;
	}

	public String getMakeName() {
		return this.makeName;
	}

	public String getViewName() {
		return this.viewName;
	}

	public String getFirstLetter() {
		return this.firstLetter;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public String getBrandName() {
		return this.brandName;
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

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName == null ? null : makeName.trim();
	}

	public void setViewName(String viewName) {
		this.viewName = viewName == null ? null : viewName.trim();
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter == null ? null : firstLetter.trim();
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
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

}
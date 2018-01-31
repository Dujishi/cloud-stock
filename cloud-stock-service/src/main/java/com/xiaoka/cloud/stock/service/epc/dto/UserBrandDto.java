package com.xiaoka.cloud.stock.service.epc.dto;

import java.util.Date;

/**
 * 用户品牌权限
 *
 * @author suqin
 */
public class UserBrandDto {

	private Integer csUserId;
	private Integer brandId;
	private String brandName;
	private String viewName;
	private String firstLetter;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getCsUserId() {
		return this.csUserId;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public String getViewName() {
		return this.viewName;
	}

	public String getFirstLetter() {
		return this.firstLetter;
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

	public void setCsUserId(Integer csUserId) {
		this.csUserId = csUserId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public void setViewName(String viewName) {
		this.viewName = viewName == null ? null : viewName.trim();
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter == null ? null : firstLetter.trim();
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
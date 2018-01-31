package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.util.Date;

/**
 * UserBrandEntity
 *
 * @author suqin
 */
public class UserBrandEntity {

	private Integer csUserId;
	private Integer brandId;
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
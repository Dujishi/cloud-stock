package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * StandardCategoryEntity
 *
 * @author suqin
 */
public class StandardCategoryEntity implements Serializable {

	private static final long serialVersionUID = -4770448600751335253L;
	private Integer categoryId;
	private String categoryName;
	private String logoPath;
	private String remark;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public String getLogoPath() {
		return this.logoPath;
	}

	public String getRemark() {
		return this.remark;
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

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName == null ? null : categoryName.trim();
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath == null ? null : logoPath.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
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
package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * StandardSubAssemblyEntity
 *
 * @author suqin
 */
public class StandardSubAssemblyEntity implements Serializable {

	private static final long serialVersionUID = 4434940628109102587L;
	private Integer subAssemblyId;
	private String subAssemblyName;
	private Integer categoryId;
	private String categoryName;
	private Integer assemblyId;
	private String assemblyName;
	private String remark;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getSubAssemblyId() {
		return this.subAssemblyId;
	}

	public String getSubAssemblyName() {
		return this.subAssemblyName;
	}

	public Integer getCategoryId() {
		return this.categoryId;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public Integer getAssemblyId() {
		return this.assemblyId;
	}

	public String getAssemblyName() {
		return this.assemblyName;
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

	public void setSubAssemblyId(Integer subAssemblyId) {
		this.subAssemblyId = subAssemblyId;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName == null ? null : subAssemblyName.trim();
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName == null ? null : categoryName.trim();
	}

	public void setAssemblyId(Integer assemblyId) {
		this.assemblyId = assemblyId;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName == null ? null : assemblyName.trim();
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
package com.xiaoka.cloud.stock.soa.api.epc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * CarModelCategoryEntity
 *
 * @author suqin
 */
public class CarSubAssemblyDto implements Serializable{

	private static final long serialVersionUID = -8780835516338437211L;
	private Integer modelId;
	private Integer categoryId;
	private String categoryName;
	private Integer assemblyId;
	private String assemblyName;
	private Integer subAssemblyId;
	private String subAssemblyName;
	private Integer type;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getModelId() {
		return this.modelId;
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

	public Integer getSubAssemblyId() {
		return this.subAssemblyId;
	}

	public String getSubAssemblyName() {
		return this.subAssemblyName;
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

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
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

	public void setSubAssemblyId(Integer subAssemblyId) {
		this.subAssemblyId = subAssemblyId;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName == null ? null : subAssemblyName.trim();
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

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
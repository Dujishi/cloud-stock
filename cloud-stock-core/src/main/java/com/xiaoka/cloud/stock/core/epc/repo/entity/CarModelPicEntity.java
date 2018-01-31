package com.xiaoka.cloud.stock.core.epc.repo.entity;

import com.xiaoka.cloud.stock.core.route.Route;

import java.io.Serializable;
import java.util.Date;

/**
 * CarModelPicEntity
 *
 * @author suqin
 */
@Route(tableName = "car_model_pic",shardType = "%50",shardBy = "modelId")
public class CarModelPicEntity implements Serializable {

	private static final long serialVersionUID = 5633510287589140437L;
	private Integer modelId;
	private String kpsName;
	private Integer assemblyId;
	private String assemblyName;
	private Integer subAssemblyId;
	private String subAssemblyName;
	private String picNum;
	private String picName;
	private String picPath;
	private String originalSubAssemblyName;
	private Integer type;
	private Integer enable;
	private Integer timerId;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getModelId() {
		return this.modelId;
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

	public String getPicNum() {
		return this.picNum;
	}

	public String getPicName() {
		return this.picName;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public String getOriginalSubAssemblyName() {
		return this.originalSubAssemblyName;
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

	public void setPicNum(String picNum) {
		this.picNum = picNum == null ? null : picNum.trim();
	}

	public void setPicName(String picName) {
		this.picName = picName == null ? null : picName.trim();
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath == null ? null : picPath.trim();
	}

	public void setOriginalSubAssemblyName(String originalSubAssemblyName) {
		this.originalSubAssemblyName = originalSubAssemblyName == null ? null : originalSubAssemblyName.trim();
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

	public Integer getTimerId() {
		return timerId;
	}

	public void setTimerId(Integer timerId) {
		this.timerId = timerId;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getKpsName() {
		return kpsName;
	}

	public void setKpsName(String kpsName) {
		this.kpsName = kpsName == null ? null : kpsName.trim();
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}
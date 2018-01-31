package com.xiaoka.cloud.stock.core.epc.repo.entity;

import com.xiaoka.cloud.stock.core.route.Route;

import java.io.Serializable;
import java.util.Date;

/**
 * PartModelEntity
 *
 * @author suqin
 */
//@Route(tableName = "part_model", shardType = "%50", shardBy = "partCode")
public class PartModelEntity implements Serializable{

	private static final long serialVersionUID = 5207225198663449772L;
	private String  partCode;
	private Integer brandId;
	private String  brandName;
	private Integer makeId;
	private String  makeName;
	private Integer seriesId;
	private String  seriesName;
	private Integer modelId;
	private String  modelName;
	private String  structure;
	private String  type;
	private String  timerType;
	private Integer epcNo;
	private Integer isValid;
	private Date    createTime;
	private String  createBy;
	private Date    updateTime;
	private String  updateBy;

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

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public String getSeriesName() {
		return this.seriesName;
	}

	public Integer getModelId() {
		return this.modelId;
	}

	public String getModelName() {
		return this.modelName;
	}

	public String getStructure() {
		return this.structure;
	}

	public String getType() {
		return this.type;
	}

	public String getTimerType() {
		return this.timerType;
	}

	public Integer getEpcNo() {
		return this.epcNo;
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

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName == null ? null : seriesName.trim();
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}

	public void setStructure(String structure) {
		this.structure = structure == null ? null : structure.trim();
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public void setTimerType(String timerType) {
		this.timerType = timerType == null ? null : timerType.trim();
	}

	public void setEpcNo(Integer epcNo) {
		this.epcNo = epcNo;
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
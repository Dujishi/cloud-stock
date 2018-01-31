package com.xiaoka.cloud.stock.core.epc.repo.entity;

import com.xiaoka.cloud.stock.core.route.Route;

import java.io.Serializable;
import java.util.Date;

/**
 * CarModelPartEntity
 *
 * @author suqin
 */
@Route(tableName = "car_model_part",shardType = "%50",shardBy = "modelId")
public class CarModelPartEntity implements Serializable{

	private static final long serialVersionUID = -3749165816076901482L;
	private Integer modelId;
	private String partCode;
	private String partName;
	private String perUseNum;
	private String picName;
	private String picNum;
	private String picPath;
	private String originalAssemblyName;
	private String originalSubAssemblyName;
	private String picSequence;
	private String remarkBrief;
	private String remarkDetail;
	private Integer standardPartId;
	private Integer assemblyId;
	private String assemblyName;
	private Integer subAssemblyId;
	private String subAssemblyName;
	private Integer epcNo;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getModelId() {
		return this.modelId;
	}

	public String getPartCode() {
		return this.partCode;
	}

	public String getPartName() {
		return this.partName;
	}

	public String getPerUseNum() {
		return this.perUseNum;
	}

	public String getPicName() {
		return this.picName;
	}

	public String getPicNum() {
		return this.picNum;
	}

	public String getPicPath() {
		return this.picPath;
	}

	public String getOriginalAssemblyName() {
		return this.originalAssemblyName;
	}

	public String getOriginalSubAssemblyName() {
		return this.originalSubAssemblyName;
	}

	public String getPicSequence() {
		return this.picSequence;
	}

	public String getRemarkBrief() {
		return this.remarkBrief;
	}

	public String getRemarkDetail() {
		return this.remarkDetail;
	}

	public Integer getStandardPartId() {
		return this.standardPartId;
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

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode == null ? null : partCode.trim();
	}

	public void setPartName(String partName) {
		this.partName = partName == null ? null : partName.trim();
	}

	public void setPerUseNum(String perUseNum) {
		this.perUseNum = perUseNum;
	}

	public void setPicName(String picName) {
		this.picName = picName == null ? null : picName.trim();
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum == null ? null : picNum.trim();
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath == null ? null : picPath.trim();
	}

	public void setOriginalAssemblyName(String originalAssemblyName) {
		this.originalAssemblyName = originalAssemblyName == null ? null : originalAssemblyName.trim();
	}

	public void setOriginalSubAssemblyName(String originalSubAssemblyName) {
		this.originalSubAssemblyName = originalSubAssemblyName == null ? null : originalSubAssemblyName.trim();
	}

	public void setPicSequence(String picSequence) {
		this.picSequence = picSequence == null ? null : picSequence.trim();
	}

	public void setRemarkBrief(String remarkBrief) {
		this.remarkBrief = remarkBrief == null ? null : remarkBrief.trim();
	}

	public void setRemarkDetail(String remarkDetail) {
		this.remarkDetail = remarkDetail == null ? null : remarkDetail.trim();
	}

	public void setStandardPartId(Integer standardPartId) {
		this.standardPartId = standardPartId;
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
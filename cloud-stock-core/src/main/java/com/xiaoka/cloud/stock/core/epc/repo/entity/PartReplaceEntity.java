package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * PartReplaceEntity
 *
 * @author suqin
 */
public class PartReplaceEntity implements Serializable {

	private static final long serialVersionUID = -2611809972688513674L;
	private String partCode;
	private String replacePartCode;
	private String type;
	/**
	 * 车的品牌
	 */
	private String gpId;
	private String brandName;
	private String bpId;
	private String makeName;
	private String partBrand;
	private String logoPath;
	private String remark;
	private String epcNo;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public String getPartCode() {
		return this.partCode;
	}

	public String getReplacePartCode() {
		return this.replacePartCode;
	}

	public String getType() {
		return this.type;
	}

	public String getGpId() {
		return this.gpId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public String getBpId() {
		return this.bpId;
	}

	public String getMakeName() {
		return this.makeName;
	}

	public String getPartBrand() {
		return this.partBrand;
	}

	public String getLogoPath() {
		return this.logoPath;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getEpcNo() {
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

	public void setReplacePartCode(String replacePartCode) {
		this.replacePartCode = replacePartCode == null ? null : replacePartCode.trim();
	}

	public void setType(String type) {
		this.type = type == null ? null : type.trim();
	}

	public void setGpId(String gpId) {
		this.gpId = gpId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public void setBpId(String bpId) {
		this.bpId = bpId;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName == null ? null : makeName.trim();
	}

	public void setPartBrand(String partBrand) {
		this.partBrand = partBrand == null ? null : partBrand.trim();
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath == null ? null : logoPath.trim();
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public void setEpcNo(String epcNo) {
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
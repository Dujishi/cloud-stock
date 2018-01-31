package com.xiaoka.cloud.stock.core.crawl.repo.entity;


import java.util.Date;

/**
 * for car brand
 * ZeroSubGroupEntity
 *
 * @author zhouze
 */
public class ZeroSubGroupEntity {

	private Integer id;
	private Integer cId;
	private Integer groupId;
	private String  groupName;
	private String  groupImg;
	private String  subGroup;
	private String  subGroupName;
	private String  subGroupUrl;
	private String  subModel;
	private String  subMid;
	private String  subDesc;
	private String  carModel;
	private String  brand;
	private String  market;
	private String  year;
	private Integer isValid;
	private Date    createTime;
	private String  createBy;
	private Date    updateTime;
	private String  updateBy;
	private String  remark;

	public Integer getId() {
		return this.id;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getGroupImg() {
		return this.groupImg;
	}

	public String getSubGroup() {
		return this.subGroup;
	}

	public String getSubGroupName() {
		return this.subGroupName;
	}

	public String getSubGroupUrl() {
		return this.subGroupUrl;
	}

	public String getSubModel() {
		return this.subModel;
	}

	public String getSubMid() {
		return this.subMid;
	}

	public String getSubDesc() {
		return this.subDesc;
	}

	public String getCarModel() {
		return this.carModel;
	}

	public String getBrand() {
		return this.brand;
	}

	public String getMarket() {
		return this.market;
	}

	public String getYear() {
		return this.year;
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

	public String getRemark() {
		return this.remark;
	}


	public void setId(Integer id) {
		this.id = id;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg == null ? null : groupImg.trim();
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup == null ? null : subGroup.trim();
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName == null ? null : subGroupName.trim();
	}

	public void setSubGroupUrl(String subGroupUrl) {
		this.subGroupUrl = subGroupUrl == null ? null : subGroupUrl.trim();
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel == null ? null : subModel.trim();
	}

	public void setSubMid(String subMid) {
		this.subMid = subMid == null ? null : subMid.trim();
	}

	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc == null ? null : subDesc.trim();
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel == null ? null : carModel.trim();
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public void setMarket(String market) {
		this.market = market == null ? null : market.trim();
	}

	public void setYear(String year) {
		this.year = year == null ? null : year.trim();
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

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}
}
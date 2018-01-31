package com.xiaoka.cloud.stock.core.crawl.repo.entity;


import java.util.Date;

/**
 * for car brand
 * ZeroAdapterCarModelEntity
 *
 * @author
 */
public class ZeroAdapterCarModelEntity {

	private Integer id;
	private String  brand;
	private String  pid;
	private String  carModel;
	private String  groupName;
	private String  mainGroupName;
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

	public String getBrand() {
		return this.brand;
	}

	public String getPid() {
		return this.pid;
	}

	public String getCarModel() {
		return this.carModel;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public String getMainGroupName() {
		return this.mainGroupName;
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

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public void setPid(String pid) {
		this.pid = pid == null ? null : pid.trim();
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel == null ? null : carModel.trim();
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName == null ? null : groupName.trim();
	}

	public void setMainGroupName(String mainGroupName) {
		this.mainGroupName = mainGroupName == null ? null : mainGroupName.trim();
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

}
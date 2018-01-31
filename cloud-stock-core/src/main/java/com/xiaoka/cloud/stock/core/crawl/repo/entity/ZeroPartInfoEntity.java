package com.xiaoka.cloud.stock.core.crawl.repo.entity;


import java.util.Date;

/**
 * for car brand
 * ZeroPartInfoEntity
 *
 * @author zhouze
 */
public class ZeroPartInfoEntity {

	private Integer id;
	private String  brand;
	private String  pid;
	private String  realPid;
	private String  pidModel;
	private String  pidLabel;
	private String  pidRemark;
	private String  itId;
	private String  num;
	private String  imgUrl;
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

	public String getRealPid() {
		return this.realPid;
	}

	public String getPidModel() {
		return this.pidModel;
	}

	public String getPidLabel() {
		return this.pidLabel;
	}

	public String getPidRemark() {
		return this.pidRemark;
	}

	public String getItId() {
		return this.itId;
	}

	public String getNum() {
		return this.num;
	}

	public String getImgUrl() {
		return this.imgUrl;
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

	public void setRealPid(String realPid) {
		this.realPid = realPid == null ? null : realPid.trim();
	}

	public void setPidModel(String pidModel) {
		this.pidModel = pidModel == null ? null : pidModel.trim();
	}

	public void setPidLabel(String pidLabel) {
		this.pidLabel = pidLabel == null ? null : pidLabel.trim();
	}

	public void setPidRemark(String pidRemark) {
		this.pidRemark = pidRemark == null ? null : pidRemark.trim();
	}

	public void setItId(String itId) {
		this.itId = itId == null ? null : itId.trim();
	}

	public void setNum(String num) {
		this.num = num == null ? null : num.trim();
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl == null ? null : imgUrl.trim();
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
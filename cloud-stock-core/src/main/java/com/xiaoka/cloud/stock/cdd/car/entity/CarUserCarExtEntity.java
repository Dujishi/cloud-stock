package com.xiaoka.cloud.stock.cdd.car.entity;

import java.util.Date;

/**
 * for car brand
 * CarUserCarExtEntity
 *
 * @author auto-generate
 */
public class CarUserCarExtEntity {

	private Integer id;
	private Integer userId;
	private Integer userCarId;
	private Integer vehicleLicenseId;
	private String licenseImg;
	private String licenseImgCopy;
	private Integer cityId;
	private Integer auditStatus;
	private Integer source;
	private boolean ticketFlag;
	private boolean claimState;
	private Date issueDate;
	private Date mandatoryExpireDate;
	private Date commercialExpireDate;
	private Date createTime;
	private Date modifyTime;
	private String saleInvoiceNo;
	private boolean licIsFinish;
	private Integer accidentType;
	private boolean inspectionVehicleType;

	public Integer getId() {
		return this.id;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public Integer getUserCarId() {
		return this.userCarId;
	}

	public Integer getVehicleLicenseId() {
		return this.vehicleLicenseId;
	}

	public String getLicenseImg() {
		return this.licenseImg;
	}

	public String getLicenseImgCopy() {
		return this.licenseImgCopy;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public Integer getAuditStatus() {
		return this.auditStatus;
	}

	public Integer getSource() {
		return this.source;
	}

	public boolean isTicketFlag() {
		return this.ticketFlag;
	}

	public boolean isClaimState() {
		return this.claimState;
	}

	public Date getIssueDate() {
		return this.issueDate;
	}

	public Date getMandatoryExpireDate() {
		return this.mandatoryExpireDate;
	}

	public Date getCommercialExpireDate() {
		return this.commercialExpireDate;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public String getSaleInvoiceNo() {
		return this.saleInvoiceNo;
	}

	public boolean isLicIsFinish() {
		return this.licIsFinish;
	}

	public Integer getAccidentType() {
		return this.accidentType;
	}

	public boolean isInspectionVehicleType() {
		return this.inspectionVehicleType;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setUserCarId(Integer userCarId) {
		this.userCarId = userCarId;
	}

	public void setVehicleLicenseId(Integer vehicleLicenseId) {
		this.vehicleLicenseId = vehicleLicenseId;
	}

	public void setLicenseImg(String licenseImg) {
		this.licenseImg = licenseImg == null ? null : licenseImg.trim();
	}

	public void setLicenseImgCopy(String licenseImgCopy) {
		this.licenseImgCopy = licenseImgCopy == null ? null : licenseImgCopy.trim();
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public void setAuditStatus(Integer auditStatus) {
		this.auditStatus = auditStatus;
	}

	public void setSource(Integer source) {
		this.source = source;
	}

	public void setTicketFlag(boolean ticketFlag) {
		this.ticketFlag = ticketFlag;
	}

	public void setClaimState(boolean claimState) {
		this.claimState = claimState;
	}

	public void setIssueDate(Date issueDate) {
		this.issueDate = issueDate == null ? null : (Date) issueDate.clone();
	}

	public void setMandatoryExpireDate(Date mandatoryExpireDate) {
		this.mandatoryExpireDate = mandatoryExpireDate == null ? null : (Date) mandatoryExpireDate.clone();
	}

	public void setCommercialExpireDate(Date commercialExpireDate) {
		this.commercialExpireDate = commercialExpireDate == null ? null : (Date) commercialExpireDate.clone();
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime == null ? null : (Date) modifyTime.clone();
	}

	public void setSaleInvoiceNo(String saleInvoiceNo) {
		this.saleInvoiceNo = saleInvoiceNo == null ? null : saleInvoiceNo.trim();
	}

	public void setLicIsFinish(boolean licIsFinish) {
		this.licIsFinish = licIsFinish;
	}

	public void setAccidentType(Integer accidentType) {
		this.accidentType = accidentType;
	}

	public void setInspectionVehicleType(boolean inspectionVehicleType) {
		this.inspectionVehicleType = inspectionVehicleType;
	}

}
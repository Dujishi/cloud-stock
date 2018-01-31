package com.xiaoka.cloud.stock.cdd.car.entity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * for car brand
 * CarUserCarEntity
 *
 * @author auto-generate
 */
public class CarUserCarEntity {

	private Integer carId;
	private Integer carUserid;
	private String carNum;
	private Integer carBrand;
	private Integer carModel;
	private Integer carSeries;
	private BigDecimal carKilometers;
	private String carVehicleFrameNo;
	private Integer carDefault;
	private boolean isDeleted;
	private String tireStandard;
	private Date dateTime;
	private String carEngineNo;
	private Date violationSearchTime;
	private String violationCarType;
	private Integer searchCityId;
	private Integer carInfoError;
	private Date violationSearchLastTime;
	private Integer violationSearchCount;
	private String backTireStandard;
	private Date createTime;
	private Date modifyTime;
	private boolean fresh;
	private boolean usefor;
	private Integer carModelType;
	private Integer carTypeColor;

	public Integer getCarId() {
		return this.carId;
	}

	public Integer getCarUserid() {
		return this.carUserid;
	}

	public String getCarNum() {
		return this.carNum;
	}

	public Integer getCarBrand() {
		return this.carBrand;
	}

	public Integer getCarModel() {
		return this.carModel;
	}

	public Integer getCarSeries() {
		return this.carSeries;
	}

	public BigDecimal getCarKilometers() {
		return this.carKilometers;
	}

	public String getCarVehicleFrameNo() {
		return this.carVehicleFrameNo;
	}

	public Integer getCarDefault() {
		return this.carDefault;
	}

	public boolean isIsDeleted() {
		return this.isDeleted;
	}

	public String getTireStandard() {
		return this.tireStandard;
	}

	public Date getDateTime() {
		return this.dateTime;
	}

	public String getCarEngineNo() {
		return this.carEngineNo;
	}

	public Date getViolationSearchTime() {
		return this.violationSearchTime;
	}

	public String getViolationCarType() {
		return this.violationCarType;
	}

	public Integer getSearchCityId() {
		return this.searchCityId;
	}

	public Integer getCarInfoError() {
		return this.carInfoError;
	}

	public Date getViolationSearchLastTime() {
		return this.violationSearchLastTime;
	}

	public Integer getViolationSearchCount() {
		return this.violationSearchCount;
	}

	public String getBackTireStandard() {
		return this.backTireStandard;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getModifyTime() {
		return this.modifyTime;
	}

	public boolean isFresh() {
		return this.fresh;
	}

	public boolean isUsefor() {
		return this.usefor;
	}

	public Integer getCarModelType() {
		return this.carModelType;
	}

	public Integer getCarTypeColor() {
		return this.carTypeColor;
	}

	public void setCarId(Integer carId) {
		this.carId = carId;
	}

	public void setCarUserid(Integer carUserid) {
		this.carUserid = carUserid;
	}

	public void setCarNum(String carNum) {
		this.carNum = carNum == null ? null : carNum.trim();
	}

	public void setCarBrand(Integer carBrand) {
		this.carBrand = carBrand;
	}

	public void setCarModel(Integer carModel) {
		this.carModel = carModel;
	}

	public void setCarSeries(Integer carSeries) {
		this.carSeries = carSeries;
	}

	public void setCarKilometers(BigDecimal carKilometers) {
		this.carKilometers = carKilometers;
	}

	public void setCarVehicleFrameNo(String carVehicleFrameNo) {
		this.carVehicleFrameNo = carVehicleFrameNo == null ? null : carVehicleFrameNo.trim();
	}

	public void setCarDefault(Integer carDefault) {
		this.carDefault = carDefault;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setTireStandard(String tireStandard) {
		this.tireStandard = tireStandard == null ? null : tireStandard.trim();
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime == null ? null : (Date) dateTime.clone();
	}

	public void setCarEngineNo(String carEngineNo) {
		this.carEngineNo = carEngineNo == null ? null : carEngineNo.trim();
	}

	public void setViolationSearchTime(Date violationSearchTime) {
		this.violationSearchTime = violationSearchTime == null ? null : (Date) violationSearchTime.clone();
	}

	public void setViolationCarType(String violationCarType) {
		this.violationCarType = violationCarType == null ? null : violationCarType.trim();
	}

	public void setSearchCityId(Integer searchCityId) {
		this.searchCityId = searchCityId;
	}

	public void setCarInfoError(Integer carInfoError) {
		this.carInfoError = carInfoError;
	}

	public void setViolationSearchLastTime(Date violationSearchLastTime) {
		this.violationSearchLastTime = violationSearchLastTime == null ? null : (Date) violationSearchLastTime.clone();
	}

	public void setViolationSearchCount(Integer violationSearchCount) {
		this.violationSearchCount = violationSearchCount;
	}

	public void setBackTireStandard(String backTireStandard) {
		this.backTireStandard = backTireStandard == null ? null : backTireStandard.trim();
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime == null ? null : (Date) modifyTime.clone();
	}

	public void setFresh(boolean fresh) {
		this.fresh = fresh;
	}

	public void setUsefor(boolean usefor) {
		this.usefor = usefor;
	}

	public void setCarModelType(Integer carModelType) {
		this.carModelType = carModelType;
	}

	public void setCarTypeColor(Integer carTypeColor) {
		this.carTypeColor = carTypeColor;
	}

}
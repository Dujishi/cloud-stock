package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.util.Date;

/**
 * for car brand
 * VinEpcResultEntity
 *
 * @author auto-generate
 */
public class VinEpcResultEntity {

	private Integer id;
	private String vin;
	private String carBrandName;
	private String carBrandFactory;
	private String carSeriesName;
	private String displacement;
	private String transmission;
	private String modelYear;
	private String carModelName;
	private String source;
	private Boolean hasEpc;
	private Boolean hasModelData;
	private Boolean isValid;
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getVin() {
		return this.vin;
	}

	public String getCarBrandName() {
		return this.carBrandName;
	}

	public String getCarBrandFactory() {
		return this.carBrandFactory;
	}

	public String getCarSeriesName() {
		return this.carSeriesName;
	}

	public String getDisplacement() {
		return this.displacement;
	}

	public String getTransmission() {
		return this.transmission;
	}

	public String getModelYear() {
		return this.modelYear;
	}

	public String getCarModelName() {
		return this.carModelName;
	}

	public String getSource() {
		return this.source;
	}

	public Boolean isHasEpc() {
		return this.hasEpc;
	}

	public Boolean isHasModelData() {
		return this.hasModelData;
	}

	public Boolean isIsValid() {
		return this.isValid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setVin(String vin) {
		this.vin = vin == null ? null : vin.trim();
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName == null ? null : carBrandName.trim();
	}

	public void setCarBrandFactory(String carBrandFactory) {
		this.carBrandFactory = carBrandFactory == null ? null : carBrandFactory.trim();
	}

	public void setCarSeriesName(String carSeriesName) {
		this.carSeriesName = carSeriesName == null ? null : carSeriesName.trim();
	}

	public void setDisplacement(String displacement) {
		this.displacement = displacement == null ? null : displacement.trim();
	}

	public void setTransmission(String transmission) {
		this.transmission = transmission == null ? null : transmission.trim();
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear == null ? null : modelYear.trim();
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName == null ? null : carModelName.trim();
	}

	public void setSource(String source) {
		this.source = source == null ? null : source.trim();
	}

	public void setHasEpc(Boolean hasEpc) {
		this.hasEpc = hasEpc;
	}

	public void setHasModelData(Boolean hasModelData) {
		this.hasModelData = hasModelData;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
	}

}
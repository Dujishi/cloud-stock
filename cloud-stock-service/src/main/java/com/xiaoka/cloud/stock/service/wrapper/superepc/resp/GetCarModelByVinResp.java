package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetCarModelByVinResp implements Serializable {

	private static final long serialVersionUID = 1314696170057061253L;
	@JsonProperty("c_capacity_in_litre_L")
	private String cCapacityInLitreL;
	@JsonProperty("c_model_year")
	private String cModelYear;
	@JsonProperty("c_oem_brand")
	private String cOemBrand;
	@JsonProperty("c_oem_name")
	private String cOemName;
	@JsonProperty("c_oem_name_show")
	private String cOemNameShow;
	@JsonProperty("c_series_BBG")
	private String cSeriesBbg;
	@JsonProperty("c_timer_model_name")
	private String cTimerModelName;
	@JsonProperty("c_transmission_type")
	private String cTransmissionType;
	@JsonProperty("t_letter")
	private String tLetter;
	private String tid;

	public void setCCapacityInLitreL(String cCapacityInLitreL) {
		this.cCapacityInLitreL = cCapacityInLitreL;
	}

	public String getCCapacityInLitreL() {
		return cCapacityInLitreL;
	}

	public void setCModelYear(String cModelYear) {
		this.cModelYear = cModelYear;
	}

	public String getCModelYear() {
		return cModelYear;
	}

	public void setCOemBrand(String cOemBrand) {
		this.cOemBrand = cOemBrand;
	}

	public String getCOemBrand() {
		return cOemBrand;
	}

	public void setCOemName(String cOemName) {
		this.cOemName = cOemName;
	}

	public String getCOemName() {
		return cOemName;
	}

	public void setCOemNameShow(String cOemNameShow) {
		this.cOemNameShow = cOemNameShow;
	}

	public String getCOemNameShow() {
		return cOemNameShow;
	}

	public void setCSeriesBbg(String cSeriesBbg) {
		this.cSeriesBbg = cSeriesBbg;
	}

	public String getCSeriesBbg() {
		return cSeriesBbg;
	}

	public void setCTimerModelName(String cTimerModelName) {
		this.cTimerModelName = cTimerModelName;
	}

	public String getCTimerModelName() {
		return cTimerModelName;
	}

	public void setCTransmissionType(String cTransmissionType) {
		this.cTransmissionType = cTransmissionType;
	}

	public String getCTransmissionType() {
		return cTransmissionType;
	}

	public void setTLetter(String tLetter) {
		this.tLetter = tLetter;
	}

	public String getTLetter() {
		return tLetter;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTid() {
		return tid;
	}

}

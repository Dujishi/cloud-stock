package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetCarModelInfoByTidResp implements Serializable {

	private static final long serialVersionUID = -8571411291897917101L;
	@JsonProperty("GPS")
	private String gps;
	@JsonProperty("HID_light")
	private String hidLight;
	@JsonProperty("aircon_mode")
	private String airconMode;
	@JsonProperty("alloy_wheel")
	private String alloyWheel;
	@JsonProperty("auto_head_light")
	private String autoHeadLight;
	@JsonProperty("brand_id_timing")
	private String brandIdTiming;
	@JsonProperty("c_MSRP_newest")
	private String cMsrpNewest;
	@JsonProperty("c_capacity_in_litre_L")
	private String cCapacityInLitreL;
	@JsonProperty("c_drive_form")
	private String cDriveForm;
	@JsonProperty("c_engine_model")
	private String cEngineModel;
	@JsonProperty("c_fuel_type")
	private String cFuelType;
	@JsonProperty("c_model_interior_code")
	private String cModelInteriorCode;
	@JsonProperty("c_model_name")
	private String cModelName;
	@JsonProperty("c_model_year")
	private String cModelYear;
	@JsonProperty("c_oem_abbrebiation")
	private String cOemAbbrebiation;
	@JsonProperty("c_oem_brand")
	private String cOemBrand;
	@JsonProperty("c_oem_name")
	private String cOemName;
	@JsonProperty("c_oem_name_show")
	private String cOemNameShow;
	@JsonProperty("c_series_BBG")
	private String cSeriesBbg;
	@JsonProperty("c_series_name")
	private String cSeriesName;
	@JsonProperty("c_structure")
	private String cStructure;
	@JsonProperty("c_timer_model_id")
	private String cTimerModelId;
	@JsonProperty("c_timer_model_name")
	private String cTimerModelName;
	@JsonProperty("c_timer_remark_transmission_type")
	private String cTimerRemarkTransmissionType;
	@JsonProperty("c_timer_type_id")
	private String cTimerTypeId;
	@JsonProperty("c_timer_type_name")
	private String cTimerTypeName;
	@JsonProperty("c_transmission_type")
	private String cTransmissionType;
	private String cruise;
	@JsonProperty("ele_rearview_adj")
	private String eleRearviewAdj;
	@JsonProperty("ele_rearview_fold")
	private String eleRearviewFold;
	@JsonProperty("ele_seats_adj")
	private String eleSeatsAdj;
	@JsonProperty("ele_sunroof")
	private String eleSunroof;
	@JsonProperty("epc_no")
	private String epcNo;
	@JsonProperty("front_tire_spec")
	private String frontTireSpec;
	@JsonProperty("head_light_clean_sys")
	private String headLightCleanSys;
	@JsonProperty("keyless_boot")
	private String keylessBoot;
	@JsonProperty("large_color_LCD")
	private String largeColorLcd;
	@JsonProperty("leather_seat")
	private String leatherSeat;
	@JsonProperty("make_id_timing")
	private String makeIdTiming;
	@JsonProperty("multi_steering_wheel_adj")
	private String multiSteeringWheelAdj;
	@JsonProperty("pano_sunroof")
	private String panoSunroof;
	@JsonProperty("parking_radar")
	private String parkingRadar;
	@JsonProperty("pic_path")
	private String picPath;
	@JsonProperty("rear_tire_spec")
	private String rearTireSpec;
	@JsonProperty("reversing_video")
	private String reversingVideo;
	@JsonProperty("seat_heating")
	private String seatHeating;
	@JsonProperty("series_id_timing")
	private String seriesIdTiming;
	@JsonProperty("t_HL_version")
	private String tHlVersion;
	@JsonProperty("t_id")
	private String tId;
	@JsonProperty("t_letter")
	private String tLetter;
	@JsonProperty("timer_type")
	private String timerType;

	public void setGps(String gps) {
		this.gps = gps;
	}

	public String getGps() {
		return gps;
	}

	public void setHidLight(String hidLight) {
		this.hidLight = hidLight;
	}

	public String getHidLight() {
		return hidLight;
	}

	public void setAirconMode(String airconMode) {
		this.airconMode = airconMode;
	}

	public String getAirconMode() {
		return airconMode;
	}

	public void setAlloyWheel(String alloyWheel) {
		this.alloyWheel = alloyWheel;
	}

	public String getAlloyWheel() {
		return alloyWheel;
	}

	public void setAutoHeadLight(String autoHeadLight) {
		this.autoHeadLight = autoHeadLight;
	}

	public String getAutoHeadLight() {
		return autoHeadLight;
	}

	public void setBrandIdTiming(String brandIdTiming) {
		this.brandIdTiming = brandIdTiming;
	}

	public String getBrandIdTiming() {
		return brandIdTiming;
	}

	public void setCMsrpNewest(String cMsrpNewest) {
		this.cMsrpNewest = cMsrpNewest;
	}

	public String getCMsrpNewest() {
		return cMsrpNewest;
	}

	public void setCCapacityInLitreL(String cCapacityInLitreL) {
		this.cCapacityInLitreL = cCapacityInLitreL;
	}

	public String getCCapacityInLitreL() {
		return cCapacityInLitreL;
	}

	public void setCDriveForm(String cDriveForm) {
		this.cDriveForm = cDriveForm;
	}

	public String getCDriveForm() {
		return cDriveForm;
	}

	public void setCEngineModel(String cEngineModel) {
		this.cEngineModel = cEngineModel;
	}

	public String getCEngineModel() {
		return cEngineModel;
	}

	public void setCFuelType(String cFuelType) {
		this.cFuelType = cFuelType;
	}

	public String getCFuelType() {
		return cFuelType;
	}

	public void setCModelInteriorCode(String cModelInteriorCode) {
		this.cModelInteriorCode = cModelInteriorCode;
	}

	public String getCModelInteriorCode() {
		return cModelInteriorCode;
	}

	public void setCModelName(String cModelName) {
		this.cModelName = cModelName;
	}

	public String getCModelName() {
		return cModelName;
	}

	public void setCModelYear(String cModelYear) {
		this.cModelYear = cModelYear;
	}

	public String getCModelYear() {
		return cModelYear;
	}

	public void setCOemAbbrebiation(String cOemAbbrebiation) {
		this.cOemAbbrebiation = cOemAbbrebiation;
	}

	public String getCOemAbbrebiation() {
		return cOemAbbrebiation;
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

	public void setCSeriesName(String cSeriesName) {
		this.cSeriesName = cSeriesName;
	}

	public String getCSeriesName() {
		return cSeriesName;
	}

	public void setCStructure(String cStructure) {
		this.cStructure = cStructure;
	}

	public String getCStructure() {
		return cStructure;
	}

	public void setCTimerModelId(String cTimerModelId) {
		this.cTimerModelId = cTimerModelId;
	}

	public String getCTimerModelId() {
		return cTimerModelId;
	}

	public void setCTimerModelName(String cTimerModelName) {
		this.cTimerModelName = cTimerModelName;
	}

	public String getCTimerModelName() {
		return cTimerModelName;
	}

	public void setCTimerRemarkTransmissionType(String cTimerRemarkTransmissionType) {
		this.cTimerRemarkTransmissionType = cTimerRemarkTransmissionType;
	}

	public String getCTimerRemarkTransmissionType() {
		return cTimerRemarkTransmissionType;
	}

	public void setCTimerTypeId(String cTimerTypeId) {
		this.cTimerTypeId = cTimerTypeId;
	}

	public String getCTimerTypeId() {
		return cTimerTypeId;
	}

	public void setCTimerTypeName(String cTimerTypeName) {
		this.cTimerTypeName = cTimerTypeName;
	}

	public String getCTimerTypeName() {
		return cTimerTypeName;
	}

	public void setCTransmissionType(String cTransmissionType) {
		this.cTransmissionType = cTransmissionType;
	}

	public String getCTransmissionType() {
		return cTransmissionType;
	}

	public void setCruise(String cruise) {
		this.cruise = cruise;
	}

	public String getCruise() {
		return cruise;
	}

	public void setEleRearviewAdj(String eleRearviewAdj) {
		this.eleRearviewAdj = eleRearviewAdj;
	}

	public String getEleRearviewAdj() {
		return eleRearviewAdj;
	}

	public void setEleRearviewFold(String eleRearviewFold) {
		this.eleRearviewFold = eleRearviewFold;
	}

	public String getEleRearviewFold() {
		return eleRearviewFold;
	}

	public void setEleSeatsAdj(String eleSeatsAdj) {
		this.eleSeatsAdj = eleSeatsAdj;
	}

	public String getEleSeatsAdj() {
		return eleSeatsAdj;
	}

	public void setEleSunroof(String eleSunroof) {
		this.eleSunroof = eleSunroof;
	}

	public String getEleSunroof() {
		return eleSunroof;
	}

	public void setEpcNo(String epcNo) {
		this.epcNo = epcNo;
	}

	public String getEpcNo() {
		return epcNo;
	}

	public void setFrontTireSpec(String frontTireSpec) {
		this.frontTireSpec = frontTireSpec;
	}

	public String getFrontTireSpec() {
		return frontTireSpec;
	}

	public void setHeadLightCleanSys(String headLightCleanSys) {
		this.headLightCleanSys = headLightCleanSys;
	}

	public String getHeadLightCleanSys() {
		return headLightCleanSys;
	}

	public void setKeylessBoot(String keylessBoot) {
		this.keylessBoot = keylessBoot;
	}

	public String getKeylessBoot() {
		return keylessBoot;
	}

	public void setLargeColorLcd(String largeColorLcd) {
		this.largeColorLcd = largeColorLcd;
	}

	public String getLargeColorLcd() {
		return largeColorLcd;
	}

	public void setLeatherSeat(String leatherSeat) {
		this.leatherSeat = leatherSeat;
	}

	public String getLeatherSeat() {
		return leatherSeat;
	}

	public void setMakeIdTiming(String makeIdTiming) {
		this.makeIdTiming = makeIdTiming;
	}

	public String getMakeIdTiming() {
		return makeIdTiming;
	}

	public void setMultiSteeringWheelAdj(String multiSteeringWheelAdj) {
		this.multiSteeringWheelAdj = multiSteeringWheelAdj;
	}

	public String getMultiSteeringWheelAdj() {
		return multiSteeringWheelAdj;
	}

	public void setPanoSunroof(String panoSunroof) {
		this.panoSunroof = panoSunroof;
	}

	public String getPanoSunroof() {
		return panoSunroof;
	}

	public void setParkingRadar(String parkingRadar) {
		this.parkingRadar = parkingRadar;
	}

	public String getParkingRadar() {
		return parkingRadar;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setRearTireSpec(String rearTireSpec) {
		this.rearTireSpec = rearTireSpec;
	}

	public String getRearTireSpec() {
		return rearTireSpec;
	}

	public void setReversingVideo(String reversingVideo) {
		this.reversingVideo = reversingVideo;
	}

	public String getReversingVideo() {
		return reversingVideo;
	}

	public void setSeatHeating(String seatHeating) {
		this.seatHeating = seatHeating;
	}

	public String getSeatHeating() {
		return seatHeating;
	}

	public void setSeriesIdTiming(String seriesIdTiming) {
		this.seriesIdTiming = seriesIdTiming;
	}

	public String getSeriesIdTiming() {
		return seriesIdTiming;
	}

	public void setTHlVersion(String tHlVersion) {
		this.tHlVersion = tHlVersion;
	}

	public String getTHlVersion() {
		return tHlVersion;
	}

	public void setTId(String tId) {
		this.tId = tId;
	}

	public String getTId() {
		return tId;
	}

	public void setTLetter(String tLetter) {
		this.tLetter = tLetter;
	}

	public String getTLetter() {
		return tLetter;
	}

	public void setTimerType(String timerType) {
		this.timerType = timerType;
	}

	public String getTimerType() {
		return timerType;
	}
}

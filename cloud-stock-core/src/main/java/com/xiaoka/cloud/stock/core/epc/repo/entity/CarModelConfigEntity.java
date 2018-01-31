package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * CarModelConfigEntity
 *
 * @author suqin
 */
public class CarModelConfigEntity implements Serializable {

	private static final long serialVersionUID = -1820057312361523979L;
	private Integer configId;
	private Integer modelId;
	private String gps;
	private String hidLight;
	private String airconMode;
	private String alloyWheel;
	private String autoHeadLight;
	private String cruise;
	private String eleRearviewAdj;
	private String eleRearviewFold;
	private String eleSeatsAdj;
	private String eleSunroof;
	private String frontTireSpec;
	private String rearTireSpec;
	private String headLightCleanSys;
	private String keylessBoot;
	private String largeColorLcd;
	private String leatherSeat;
	private String multiSteeringWheelAdj;
	private String panoSunroof;
	private String parkingRadar;
	private String reversingVideo;
	private String seatHeating;
	private String picPath;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getConfigId() {
		return this.configId;
	}

	public Integer getModelId() {
		return this.modelId;
	}

	public String getGps() {
		return this.gps;
	}

	public String getHidLight() {
		return this.hidLight;
	}

	public String getAirconMode() {
		return this.airconMode;
	}

	public String getAlloyWheel() {
		return this.alloyWheel;
	}

	public String getAutoHeadLight() {
		return this.autoHeadLight;
	}

	public String getCruise() {
		return this.cruise;
	}

	public String getEleRearviewAdj() {
		return this.eleRearviewAdj;
	}

	public String getEleRearviewFold() {
		return this.eleRearviewFold;
	}

	public String getEleSeatsAdj() {
		return this.eleSeatsAdj;
	}

	public String getEleSunroof() {
		return this.eleSunroof;
	}

	public String getFrontTireSpec() {
		return this.frontTireSpec;
	}

	public String getRearTireSpec() {
		return this.rearTireSpec;
	}

	public String getHeadLightCleanSys() {
		return this.headLightCleanSys;
	}

	public String getKeylessBoot() {
		return this.keylessBoot;
	}

	public String getLargeColorLcd() {
		return this.largeColorLcd;
	}

	public String getLeatherSeat() {
		return this.leatherSeat;
	}

	public String getMultiSteeringWheelAdj() {
		return this.multiSteeringWheelAdj;
	}

	public String getPanoSunroof() {
		return this.panoSunroof;
	}

	public String getParkingRadar() {
		return this.parkingRadar;
	}

	public String getReversingVideo() {
		return this.reversingVideo;
	}

	public String getSeatHeating() {
		return this.seatHeating;
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

	public void setConfigId(Integer configId) {
		this.configId = configId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public void setGps(String gps) {
		this.gps = gps == null ? null : gps.trim();
	}

	public void setHidLight(String hidLight) {
		this.hidLight = hidLight == null ? null : hidLight.trim();
	}

	public void setAirconMode(String airconMode) {
		this.airconMode = airconMode == null ? null : airconMode.trim();
	}

	public void setAlloyWheel(String alloyWheel) {
		this.alloyWheel = alloyWheel == null ? null : alloyWheel.trim();
	}

	public void setAutoHeadLight(String autoHeadLight) {
		this.autoHeadLight = autoHeadLight == null ? null : autoHeadLight.trim();
	}

	public void setCruise(String cruise) {
		this.cruise = cruise == null ? null : cruise.trim();
	}

	public void setEleRearviewAdj(String eleRearviewAdj) {
		this.eleRearviewAdj = eleRearviewAdj == null ? null : eleRearviewAdj.trim();
	}

	public void setEleRearviewFold(String eleRearviewFold) {
		this.eleRearviewFold = eleRearviewFold == null ? null : eleRearviewFold.trim();
	}

	public void setEleSeatsAdj(String eleSeatsAdj) {
		this.eleSeatsAdj = eleSeatsAdj == null ? null : eleSeatsAdj.trim();
	}

	public void setEleSunroof(String eleSunroof) {
		this.eleSunroof = eleSunroof == null ? null : eleSunroof.trim();
	}

	public void setFrontTireSpec(String frontTireSpec) {
		this.frontTireSpec = frontTireSpec == null ? null : frontTireSpec.trim();
	}

	public void setRearTireSpec(String rearTireSpec) {
		this.rearTireSpec = rearTireSpec == null ? null : rearTireSpec.trim();
	}

	public void setHeadLightCleanSys(String headLightCleanSys) {
		this.headLightCleanSys = headLightCleanSys == null ? null : headLightCleanSys.trim();
	}

	public void setKeylessBoot(String keylessBoot) {
		this.keylessBoot = keylessBoot == null ? null : keylessBoot.trim();
	}

	public void setLargeColorLcd(String largeColorLcd) {
		this.largeColorLcd = largeColorLcd == null ? null : largeColorLcd.trim();
	}

	public void setLeatherSeat(String leatherSeat) {
		this.leatherSeat = leatherSeat == null ? null : leatherSeat.trim();
	}

	public void setMultiSteeringWheelAdj(String multiSteeringWheelAdj) {
		this.multiSteeringWheelAdj = multiSteeringWheelAdj == null ? null : multiSteeringWheelAdj.trim();
	}

	public void setPanoSunroof(String panoSunroof) {
		this.panoSunroof = panoSunroof == null ? null : panoSunroof.trim();
	}

	public void setParkingRadar(String parkingRadar) {
		this.parkingRadar = parkingRadar == null ? null : parkingRadar.trim();
	}

	public void setReversingVideo(String reversingVideo) {
		this.reversingVideo = reversingVideo == null ? null : reversingVideo.trim();
	}

	public void setSeatHeating(String seatHeating) {
		this.seatHeating = seatHeating == null ? null : seatHeating.trim();
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

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}
}
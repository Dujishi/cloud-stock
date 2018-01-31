package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetAllCarModelResp implements Serializable{

	private static final long serialVersionUID = -2252792362342347642L;
	@JsonProperty("brand_id_timing")
	private String brandIdTiming;
	@JsonProperty("c_DMP_IMP")
	private String cDmpImp;
	@JsonProperty("c_MSRP_new_launach")
	private String cMsrpNewLaunach;
	@JsonProperty("c_MSRP_newest")
	private String cMsrpNewest;
	@JsonProperty("c_capacity_in_litre_CC")
	private String cCapacityInLitreCc;
	@JsonProperty("c_capacity_in_litre_L")
	private String cCapacityInLitreL;
	@JsonProperty("c_car_body")
	private String cCarBody;
	@JsonProperty("c_drive_form")
	private String cDriveForm;
	@JsonProperty("c_engine_model")
	private String cEngineModel;
	@JsonProperty("c_fuel_type")
	private String cFuelType;
	@JsonProperty("c_mixture_preparation")
	private String cMixturePreparation;
	@JsonProperty("c_model_eop")
	private String cModelEop;
	@JsonProperty("c_model_interior_code")
	private String cModelInteriorCode;
	@JsonProperty("c_model_name")
	private String cModelName;
	@JsonProperty("c_model_sop")
	private String cModelSop;
	@JsonProperty("c_model_year")
	private String cModelYear;
	@JsonProperty("c_nm_of_transmission_gear")
	private String cNmOfTransmissionGear;
	@JsonProperty("c_oem")
	private String cOem;
	@JsonProperty("c_oem_abbrebiation")
	private String cOemAbbrebiation;
	@JsonProperty("c_oem_brand")
	private String cOemBrand;
	@JsonProperty("c_oem_name")
	private String cOemName;
	@JsonProperty("c_platform")
	private String cPlatform;
	@JsonProperty("c_power")
	private String cPower;
	@JsonProperty("c_qty_of_chairs")
	private String cQtyOfChairs;
	@JsonProperty("c_qty_of_doors")
	private String cQtyOfDoors;
	@JsonProperty("c_series")
	private String cSeries;
	@JsonProperty("c_series_BBG")
	private String cSeriesBbg;
	@JsonProperty("c_series_BBG_eop_year")
	private String cSeriesBbgEopYear;
	@JsonProperty("c_series_BBG_sop_year")
	private String cSeriesBbgSopYear;
	@JsonProperty("c_series_name")
	private String cSeriesName;
	@JsonProperty("c_structure")
	private String cStructure;
	@JsonProperty("c_supercharger")
	private String cSupercharger;
	@JsonProperty("c_timer_grade")
	private String cTimerGrade;
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
	@JsonProperty("c_type_of_at_transmission")
	private String cTypeOfAtTransmission;
	@JsonProperty("c_variant_eop")
	private String cVariantEop;
	@JsonProperty("c_variant_sop")
	private String cVariantSop;
	@JsonProperty("epc_no")
	private String epcNo;
	private String id;
	@JsonProperty("make_id_timing")
	private String makeIdTiming;
	@JsonProperty("series_id_timing")
	private String seriesIdTiming;
	@JsonProperty("t_HL_version")
	private String tHlVersion;
	@JsonProperty("t_batch")
	private String tBatch;
	@JsonProperty("t_capacity_in_litrel")
	private String tCapacityInLitrel;
	@JsonProperty("t_car_year")
	private String tCarYear;
	@JsonProperty("t_cylinder_num")
	private String tCylinderNum;
	@JsonProperty("t_drv_mode")
	private String tDrvMode;
	@JsonProperty("t_e_suffix")
	private String tESuffix;
	@JsonProperty("t_env_standard")
	private String tEnvStandard;
	@JsonProperty("t_letter")
	private String tLetter;
	@JsonProperty("t_mode_name_without_year")
	private String tModeNameWithoutYear;
	@JsonProperty("t_paragraph")
	private String tParagraph;
	@JsonProperty("t_post_code")
	private String tPostCode;
	@JsonProperty("t_power_type")
	private String tPowerType;
	@JsonProperty("t_set_num")
	private String tSetNum;
	@JsonProperty("t_transmission")
	private String tTransmission;
	@JsonProperty("t_type_name")
	private String tTypeName;
	@JsonProperty("t_valid")
	private String tValid;
	private String tid;
	@JsonProperty("update_time")
	private String updateTime;
	private String xid;

	public void setBrandIdTiming(String brandIdTiming) {
		this.brandIdTiming = brandIdTiming;
	}

	public String getBrandIdTiming() {
		return brandIdTiming;
	}

	public void setCDmpImp(String cDmpImp) {
		this.cDmpImp = cDmpImp;
	}

	public String getCDmpImp() {
		return cDmpImp;
	}

	public void setCMsrpNewLaunach(String cMsrpNewLaunach) {
		this.cMsrpNewLaunach = cMsrpNewLaunach;
	}

	public String getCMsrpNewLaunach() {
		return cMsrpNewLaunach;
	}

	public void setCMsrpNewest(String cMsrpNewest) {
		this.cMsrpNewest = cMsrpNewest;
	}

	public String getCMsrpNewest() {
		return cMsrpNewest;
	}

	public void setCCapacityInLitreCc(String cCapacityInLitreCc) {
		this.cCapacityInLitreCc = cCapacityInLitreCc;
	}

	public String getCCapacityInLitreCc() {
		return cCapacityInLitreCc;
	}

	public void setCCapacityInLitreL(String cCapacityInLitreL) {
		this.cCapacityInLitreL = cCapacityInLitreL;
	}

	public String getCCapacityInLitreL() {
		return cCapacityInLitreL;
	}

	public void setCCarBody(String cCarBody) {
		this.cCarBody = cCarBody;
	}

	public String getCCarBody() {
		return cCarBody;
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

	public void setCMixturePreparation(String cMixturePreparation) {
		this.cMixturePreparation = cMixturePreparation;
	}

	public String getCMixturePreparation() {
		return cMixturePreparation;
	}

	public void setCModelEop(String cModelEop) {
		this.cModelEop = cModelEop;
	}

	public String getCModelEop() {
		return cModelEop;
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

	public void setCModelSop(String cModelSop) {
		this.cModelSop = cModelSop;
	}

	public String getCModelSop() {
		return cModelSop;
	}

	public void setCModelYear(String cModelYear) {
		this.cModelYear = cModelYear;
	}

	public String getCModelYear() {
		return cModelYear;
	}

	public void setCNmOfTransmissionGear(String cNmOfTransmissionGear) {
		this.cNmOfTransmissionGear = cNmOfTransmissionGear;
	}

	public String getCNmOfTransmissionGear() {
		return cNmOfTransmissionGear;
	}

	public void setCOem(String cOem) {
		this.cOem = cOem;
	}

	public String getCOem() {
		return cOem;
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

	public void setCPlatform(String cPlatform) {
		this.cPlatform = cPlatform;
	}

	public String getCPlatform() {
		return cPlatform;
	}

	public void setCPower(String cPower) {
		this.cPower = cPower;
	}

	public String getCPower() {
		return cPower;
	}

	public void setCQtyOfChairs(String cQtyOfChairs) {
		this.cQtyOfChairs = cQtyOfChairs;
	}

	public String getCQtyOfChairs() {
		return cQtyOfChairs;
	}

	public void setCQtyOfDoors(String cQtyOfDoors) {
		this.cQtyOfDoors = cQtyOfDoors;
	}

	public String getCQtyOfDoors() {
		return cQtyOfDoors;
	}

	public void setCSeries(String cSeries) {
		this.cSeries = cSeries;
	}

	public String getCSeries() {
		return cSeries;
	}

	public void setCSeriesBbg(String cSeriesBbg) {
		this.cSeriesBbg = cSeriesBbg;
	}

	public String getCSeriesBbg() {
		return cSeriesBbg;
	}

	public void setCSeriesBbgEopYear(String cSeriesBbgEopYear) {
		this.cSeriesBbgEopYear = cSeriesBbgEopYear;
	}

	public String getCSeriesBbgEopYear() {
		return cSeriesBbgEopYear;
	}

	public void setCSeriesBbgSopYear(String cSeriesBbgSopYear) {
		this.cSeriesBbgSopYear = cSeriesBbgSopYear;
	}

	public String getCSeriesBbgSopYear() {
		return cSeriesBbgSopYear;
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

	public void setCSupercharger(String cSupercharger) {
		this.cSupercharger = cSupercharger;
	}

	public String getCSupercharger() {
		return cSupercharger;
	}

	public void setCTimerGrade(String cTimerGrade) {
		this.cTimerGrade = cTimerGrade;
	}

	public String getCTimerGrade() {
		return cTimerGrade;
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

	public void setCTypeOfAtTransmission(String cTypeOfAtTransmission) {
		this.cTypeOfAtTransmission = cTypeOfAtTransmission;
	}

	public String getCTypeOfAtTransmission() {
		return cTypeOfAtTransmission;
	}

	public void setCVariantEop(String cVariantEop) {
		this.cVariantEop = cVariantEop;
	}

	public String getCVariantEop() {
		return cVariantEop;
	}

	public void setCVariantSop(String cVariantSop) {
		this.cVariantSop = cVariantSop;
	}

	public String getCVariantSop() {
		return cVariantSop;
	}

	public void setEpcNo(String epcNo) {
		this.epcNo = epcNo;
	}

	public String getEpcNo() {
		return epcNo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setMakeIdTiming(String makeIdTiming) {
		this.makeIdTiming = makeIdTiming;
	}

	public String getMakeIdTiming() {
		return makeIdTiming;
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

	public void setTBatch(String tBatch) {
		this.tBatch = tBatch;
	}

	public String getTBatch() {
		return tBatch;
	}

	public void setTCapacityInLitrel(String tCapacityInLitrel) {
		this.tCapacityInLitrel = tCapacityInLitrel;
	}

	public String getTCapacityInLitrel() {
		return tCapacityInLitrel;
	}

	public void setTCarYear(String tCarYear) {
		this.tCarYear = tCarYear;
	}

	public String getTCarYear() {
		return tCarYear;
	}

	public void setTCylinderNum(String tCylinderNum) {
		this.tCylinderNum = tCylinderNum;
	}

	public String getTCylinderNum() {
		return tCylinderNum;
	}

	public void setTDrvMode(String tDrvMode) {
		this.tDrvMode = tDrvMode;
	}

	public String getTDrvMode() {
		return tDrvMode;
	}

	public void setTESuffix(String tESuffix) {
		this.tESuffix = tESuffix;
	}

	public String getTESuffix() {
		return tESuffix;
	}

	public void setTEnvStandard(String tEnvStandard) {
		this.tEnvStandard = tEnvStandard;
	}

	public String getTEnvStandard() {
		return tEnvStandard;
	}

	public void setTLetter(String tLetter) {
		this.tLetter = tLetter;
	}

	public String getTLetter() {
		return tLetter;
	}

	public void setTModeNameWithoutYear(String tModeNameWithoutYear) {
		this.tModeNameWithoutYear = tModeNameWithoutYear;
	}

	public String getTModeNameWithoutYear() {
		return tModeNameWithoutYear;
	}

	public void setTParagraph(String tParagraph) {
		this.tParagraph = tParagraph;
	}

	public String getTParagraph() {
		return tParagraph;
	}

	public void setTPostCode(String tPostCode) {
		this.tPostCode = tPostCode;
	}

	public String getTPostCode() {
		return tPostCode;
	}

	public void setTPowerType(String tPowerType) {
		this.tPowerType = tPowerType;
	}

	public String getTPowerType() {
		return tPowerType;
	}

	public void setTSetNum(String tSetNum) {
		this.tSetNum = tSetNum;
	}

	public String getTSetNum() {
		return tSetNum;
	}

	public void setTTransmission(String tTransmission) {
		this.tTransmission = tTransmission;
	}

	public String getTTransmission() {
		return tTransmission;
	}

	public void setTTypeName(String tTypeName) {
		this.tTypeName = tTypeName;
	}

	public String getTTypeName() {
		return tTypeName;
	}

	public void setTValid(String tValid) {
		this.tValid = tValid;
	}

	public String getTValid() {
		return tValid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTid() {
		return tid;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setXid(String xid) {
		this.xid = xid;
	}

	public String getXid() {
		return xid;
	}
}

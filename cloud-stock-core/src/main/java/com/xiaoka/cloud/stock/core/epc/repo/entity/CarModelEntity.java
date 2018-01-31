package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * CarModelEntity
 *
 * @author suqin
 */
public class CarModelEntity implements Serializable{
	private static final long serialVersionUID = -1820057312361523974L;
	private Integer modelId;
	private String modelName;
	private String modelYear;
	private String capacityInLitreL;
	private String capacityInLitreCc;
	private String transmissionType;
	private String firstLetter;
	private Integer brandId;
	private String brandName;
	private Integer makeId;
	private String makeName;
	private Integer seriesId;
	private String seriesName;
	private String seriesBbg;
	private String dmpImp;
	private String msrpNewLaunach;
	private String msrpNewest;
	private String carBody;
	private String driveForm;
	private String engineModel;
	private String fuelType;
	private String mixturePreparation;
	private String modelEop;
	private String modelInteriorCode;
	private String modelSop;
	private String nmOfTransmissionGear;
	private String platform;
	private String power;
	private String qtyOfChairs;
	private String qtyOfDoors;
	private String structure;
	private String superCharger;
	private String timerGrade;
	private String standardModelId;
	private String standardModelName;
	private String remarkTransmissionType;
	private String standardTypeId;
	private String standardTypeName;
	private String typeOfAtTransmission;
	private Integer epcNo;
	private Integer id;
	private String hlVersion;
	private String xid;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;
	private String batch;
	private String tCapacityInLitrel;
	private String tCarYear;
	private String tCylinderNum;
	private String tDrvMode;
	private String tESuffix;
	private String tEnvStandard;
	private String tModeNameWithoutYear;
	private String tParagraph;
	private String tPostCode;
	private String tPowerType;
	private String tSetNum;
	private String tTransmission;
	private String tTypeName;
	private String tValid;
	private String seriesBbgEopYear;
	private String seriesBbgSopYear;
	private String zcUpdateTime;
	private String oem;
	private String oemAbbrebiation;
	private String series;
	private String variantEop;
	private String variantSop;

	public Integer getModelId() {
		return this.modelId;
	}

	public String getModelName() {
		return this.modelName;
	}

	public String getModelYear() {
		return this.modelYear;
	}

	public String getCapacityInLitreL() {
		return this.capacityInLitreL;
	}

	public String getCapacityInLitreCc() {
		return this.capacityInLitreCc;
	}

	public String getTransmissionType() {
		return this.transmissionType;
	}

	public String getFirstLetter() {
		return this.firstLetter;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public Integer getMakeId() {
		return this.makeId;
	}

	public String getMakeName() {
		return this.makeName;
	}

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public String getSeriesName() {
		return this.seriesName;
	}

	public String getSeriesBbg() {
		return this.seriesBbg;
	}

	public String getDmpImp() {
		return this.dmpImp;
	}

	public String getMsrpNewLaunach() {
		return this.msrpNewLaunach;
	}

	public String getMsrpNewest() {
		return this.msrpNewest;
	}

	public String getCarBody() {
		return this.carBody;
	}

	public String getDriveForm() {
		return this.driveForm;
	}

	public String getEngineModel() {
		return this.engineModel;
	}

	public String getFuelType() {
		return this.fuelType;
	}

	public String getMixturePreparation() {
		return this.mixturePreparation;
	}

	public String getModelEop() {
		return this.modelEop;
	}

	public String getModelInteriorCode() {
		return this.modelInteriorCode;
	}

	public String getModelSop() {
		return this.modelSop;
	}

	public String getNmOfTransmissionGear() {
		return this.nmOfTransmissionGear;
	}

	public String getPlatform() {
		return this.platform;
	}

	public String getPower() {
		return this.power;
	}

	public String getQtyOfChairs() {
		return this.qtyOfChairs;
	}

	public String getQtyOfDoors() {
		return this.qtyOfDoors;
	}

	public String getStructure() {
		return this.structure;
	}

	public String getSuperCharger() {
		return this.superCharger;
	}

	public String getTimerGrade() {
		return this.timerGrade;
	}

	public String getStandardModelId() {
		return this.standardModelId;
	}

	public String getStandardModelName() {
		return this.standardModelName;
	}

	public String getRemarkTransmissionType() {
		return this.remarkTransmissionType;
	}

	public String getStandardTypeId() {
		return this.standardTypeId;
	}

	public String getStandardTypeName() {
		return this.standardTypeName;
	}

	public String getTypeOfAtTransmission() {
		return this.typeOfAtTransmission;
	}

	public Integer getEpcNo() {
		return this.epcNo;
	}

	public Integer getId() {
		return this.id;
	}

	public String getHlVersion() {
		return this.hlVersion;
	}

	public String getXid() {
		return this.xid;
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

	public String getBatch() {
		return this.batch;
	}

	public String getTCapacityInLitrel() {
		return this.tCapacityInLitrel;
	}

	public String getTCarYear() {
		return this.tCarYear;
	}

	public String getTCylinderNum() {
		return this.tCylinderNum;
	}

	public String getTDrvMode() {
		return this.tDrvMode;
	}

	public String getTESuffix() {
		return this.tESuffix;
	}

	public String getTEnvStandard() {
		return this.tEnvStandard;
	}

	public String getTModeNameWithoutYear() {
		return this.tModeNameWithoutYear;
	}

	public String getTParagraph() {
		return this.tParagraph;
	}

	public String getTPostCode() {
		return this.tPostCode;
	}

	public String getTPowerType() {
		return this.tPowerType;
	}

	public String getTSetNum() {
		return this.tSetNum;
	}

	public String getTTransmission() {
		return this.tTransmission;
	}

	public String getTTypeName() {
		return this.tTypeName;
	}

	public String getTValid() {
		return this.tValid;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName == null ? null : modelName.trim();
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear == null ? null : modelYear.trim();
	}

	public void setCapacityInLitreL(String capacityInLitreL) {
		this.capacityInLitreL = capacityInLitreL == null ? null : capacityInLitreL.trim();
	}

	public void setCapacityInLitreCc(String capacityInLitreCc) {
		this.capacityInLitreCc = capacityInLitreCc == null ? null : capacityInLitreCc.trim();
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType == null ? null : transmissionType.trim();
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter == null ? null : firstLetter.trim();
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName == null ? null : makeName.trim();
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName == null ? null : seriesName.trim();
	}

	public void setSeriesBbg(String seriesBbg) {
		this.seriesBbg = seriesBbg == null ? null : seriesBbg.trim();
	}

	public void setDmpImp(String dmpImp) {
		this.dmpImp = dmpImp == null ? null : dmpImp.trim();
	}

	public void setMsrpNewLaunach(String msrpNewLaunach) {
		this.msrpNewLaunach = msrpNewLaunach == null ? null : msrpNewLaunach.trim();
	}

	public void setMsrpNewest(String msrpNewest) {
		this.msrpNewest = msrpNewest;
	}

	public void setCarBody(String carBody) {
		this.carBody = carBody == null ? null : carBody.trim();
	}

	public void setDriveForm(String driveForm) {
		this.driveForm = driveForm == null ? null : driveForm.trim();
	}

	public void setEngineModel(String engineModel) {
		this.engineModel = engineModel == null ? null : engineModel.trim();
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType == null ? null : fuelType.trim();
	}

	public void setMixturePreparation(String mixturePreparation) {
		this.mixturePreparation = mixturePreparation == null ? null : mixturePreparation.trim();
	}

	public void setModelEop(String modelEop) {
		this.modelEop = modelEop == null ? null : modelEop.trim();
	}

	public void setModelInteriorCode(String modelInteriorCode) {
		this.modelInteriorCode = modelInteriorCode == null ? null : modelInteriorCode.trim();
	}

	public void setModelSop(String modelSop) {
		this.modelSop = modelSop == null ? null : modelSop.trim();
	}

	public void setNmOfTransmissionGear(String nmOfTransmissionGear) {
		this.nmOfTransmissionGear = nmOfTransmissionGear == null ? null : nmOfTransmissionGear.trim();
	}

	public void setPlatform(String platform) {
		this.platform = platform == null ? null : platform.trim();
	}

	public void setPower(String power) {
		this.power = power == null ? null : power.trim();
	}

	public void setQtyOfChairs(String qtyOfChairs) {
		this.qtyOfChairs = qtyOfChairs;
	}

	public void setQtyOfDoors(String qtyOfDoors) {
		this.qtyOfDoors = qtyOfDoors;
	}

	public void setStructure(String structure) {
		this.structure = structure == null ? null : structure.trim();
	}

	public void setSuperCharger(String superCharger) {
		this.superCharger = superCharger == null ? null : superCharger.trim();
	}

	public void setTimerGrade(String timerGrade) {
		this.timerGrade = timerGrade == null ? null : timerGrade.trim();
	}

	public void setStandardModelId(String standardModelId) {
		this.standardModelId = standardModelId == null ? null : standardModelId.trim();
	}

	public void setStandardModelName(String standardModelName) {
		this.standardModelName = standardModelName == null ? null : standardModelName.trim();
	}

	public void setRemarkTransmissionType(String remarkTransmissionType) {
		this.remarkTransmissionType = remarkTransmissionType == null ? null : remarkTransmissionType.trim();
	}

	public void setStandardTypeId(String standardTypeId) {
		this.standardTypeId = standardTypeId;
	}

	public void setStandardTypeName(String standardTypeName) {
		this.standardTypeName = standardTypeName == null ? null : standardTypeName.trim();
	}

	public void setTypeOfAtTransmission(String typeOfAtTransmission) {
		this.typeOfAtTransmission = typeOfAtTransmission == null ? null : typeOfAtTransmission.trim();
	}

	public void setEpcNo(Integer epcNo) {
		this.epcNo = epcNo;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setHlVersion(String hlVersion) {
		this.hlVersion = hlVersion == null ? null : hlVersion.trim();
	}

	public void setXid(String xid) {
		this.xid = xid == null ? null : xid.trim();
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

	public void setBatch(String batch) {
		this.batch = batch == null ? null : batch.trim();
	}

	public void setTCapacityInLitrel(String tCapacityInLitrel) {
		this.tCapacityInLitrel = tCapacityInLitrel == null ? null : tCapacityInLitrel.trim();
	}

	public void setTCarYear(String tCarYear) {
		this.tCarYear = tCarYear == null ? null : tCarYear.trim();
	}

	public void setTCylinderNum(String tCylinderNum) {
		this.tCylinderNum = tCylinderNum == null ? null : tCylinderNum.trim();
	}

	public void setTDrvMode(String tDrvMode) {
		this.tDrvMode = tDrvMode == null ? null : tDrvMode.trim();
	}

	public void setTESuffix(String tESuffix) {
		this.tESuffix = tESuffix == null ? null : tESuffix.trim();
	}

	public void setTEnvStandard(String tEnvStandard) {
		this.tEnvStandard = tEnvStandard == null ? null : tEnvStandard.trim();
	}

	public void setTModeNameWithoutYear(String tModeNameWithoutYear) {
		this.tModeNameWithoutYear = tModeNameWithoutYear == null ? null : tModeNameWithoutYear.trim();
	}

	public void setTParagraph(String tParagraph) {
		this.tParagraph = tParagraph == null ? null : tParagraph.trim();
	}

	public void setTPostCode(String tPostCode) {
		this.tPostCode = tPostCode == null ? null : tPostCode.trim();
	}

	public void setTPowerType(String tPowerType) {
		this.tPowerType = tPowerType == null ? null : tPowerType.trim();
	}

	public void setTSetNum(String tSetNum) {
		this.tSetNum = tSetNum == null ? null : tSetNum.trim();
	}

	public void setTTransmission(String tTransmission) {
		this.tTransmission = tTransmission == null ? null : tTransmission.trim();
	}

	public void setTTypeName(String tTypeName) {
		this.tTypeName = tTypeName == null ? null : tTypeName.trim();
	}

	public void setTValid(String tValid) {
		this.tValid = tValid == null ? null : tValid.trim();
	}

	public String getSeriesBbgEopYear() {
		return seriesBbgEopYear;
	}

	public void setSeriesBbgEopYear(String seriesBbgEopYear) {
		this.seriesBbgEopYear = seriesBbgEopYear;
	}

	public String getSeriesBbgSopYear() {
		return seriesBbgSopYear;
	}

	public void setSeriesBbgSopYear(String seriesBbgSopYear) {
		this.seriesBbgSopYear = seriesBbgSopYear;
	}

	public String getZcUpdateTime() {
		return zcUpdateTime;
	}

	public void setZcUpdateTime(String zcUpdateTime) {
		this.zcUpdateTime = zcUpdateTime;
	}

	public String getOem() {
		return oem;
	}

	public void setOem(String oem) {
		this.oem = oem;
	}

	public String getOemAbbrebiation() {
		return oemAbbrebiation;
	}

	public void setOemAbbrebiation(String oemAbbrebiation) {
		this.oemAbbrebiation = oemAbbrebiation;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getVariantEop() {
		return variantEop;
	}

	public void setVariantEop(String variantEop) {
		this.variantEop = variantEop;
	}

	public String getVariantSop() {
		return variantSop;
	}

	public void setVariantSop(String variantSop) {
		this.variantSop = variantSop;
	}
}
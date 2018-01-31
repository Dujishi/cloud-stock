package com.xiaoka.cloud.stock.soa.api.epc.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouze
 * @date 2017/11/29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarModelDto implements Serializable {
	private static final long serialVersionUID = -7570478343051822652L;

	private Integer modelId;
	private String  modelName;
	private String  modelYear;
	private String  capacityInLitreL;
	private String  capacityInLitreCc;
	private String  transmissionType;
	private String  firstLetter;
	private Integer brandId;
	private String  brandName;
	private Integer makeId;
	private String  makeName;
	private Integer seriesId;
	private String  seriesName;
	private String  seriesBbg;
	private String  dmpImp;
	private String  msrpNewLaunach;
	private String  msrpNewest;
	private String  carBody;
	private String  driveForm;
	private String  engineModel;
	private String  fuelType;
	private String  mixturePreparation;
	private String  modelEop;
	private String  modelInteriorCode;
	private String  modelSop;
	private String  nmOfTransmissionGear;
	private String  platform;
	private String  power;
	private String  qtyOfChairs;
	private String  qtyOfDoors;
	private String  structure;
	private String  superCharger;
	private String  timerGrade;
	private String  standardModelId;
	private String  standardModelName;
	private String  remarkTransmissionType;
	private String  standardTypeId;
	private String  standardTypeName;
	private String  typeOfAtTransmission;
	private Integer epcNo;
	private Integer id;
	private String  hlVersion;
	private String  xid;
	private Integer isValid;
	private Date    createTime;
	private String  createBy;
	private Date    updateTime;
	private String  updateBy;
	private String  batch;
	private String  tCapacityInLitrel;
	private String  tCarYear;
	private String  tCylinderNum;
	private String  tDrvMode;
	private String  tESuffix;
	private String  tEnvStandard;
	private String  tModeNameWithoutYear;
	private String  tParagraph;
	private String  tPostCode;
	private String  tPowerType;
	private String  tSetNum;
	private String  tTransmission;
	private String  tTypeName;
	private String  tValid;
	private String  seriesBbgEopYear;
	private String  seriesBbgSopYear;
	private String  zcUpdateTime;
	private String  oem;
	private String  oemAbbrebiation;
	private String  series;
	private String  variantEop;
	private String  variantSop;

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelYear() {
		return modelYear;
	}

	public void setModelYear(String modelYear) {
		this.modelYear = modelYear;
	}

	public String getCapacityInLitreL() {
		return capacityInLitreL;
	}

	public void setCapacityInLitreL(String capacityInLitreL) {
		this.capacityInLitreL = capacityInLitreL;
	}

	public String getCapacityInLitreCc() {
		return capacityInLitreCc;
	}

	public void setCapacityInLitreCc(String capacityInLitreCc) {
		this.capacityInLitreCc = capacityInLitreCc;
	}

	public String getTransmissionType() {
		return transmissionType;
	}

	public void setTransmissionType(String transmissionType) {
		this.transmissionType = transmissionType;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeriesBbg() {
		return seriesBbg;
	}

	public void setSeriesBbg(String seriesBbg) {
		this.seriesBbg = seriesBbg;
	}

	public String getDmpImp() {
		return dmpImp;
	}

	public void setDmpImp(String dmpImp) {
		this.dmpImp = dmpImp;
	}

	public String getMsrpNewLaunach() {
		return msrpNewLaunach;
	}

	public void setMsrpNewLaunach(String msrpNewLaunach) {
		this.msrpNewLaunach = msrpNewLaunach;
	}

	public String getMsrpNewest() {
		return msrpNewest;
	}

	public void setMsrpNewest(String msrpNewest) {
		this.msrpNewest = msrpNewest;
	}

	public String getCarBody() {
		return carBody;
	}

	public void setCarBody(String carBody) {
		this.carBody = carBody;
	}

	public String getDriveForm() {
		return driveForm;
	}

	public void setDriveForm(String driveForm) {
		this.driveForm = driveForm;
	}

	public String getEngineModel() {
		return engineModel;
	}

	public void setEngineModel(String engineModel) {
		this.engineModel = engineModel;
	}

	public String getFuelType() {
		return fuelType;
	}

	public void setFuelType(String fuelType) {
		this.fuelType = fuelType;
	}

	public String getMixturePreparation() {
		return mixturePreparation;
	}

	public void setMixturePreparation(String mixturePreparation) {
		this.mixturePreparation = mixturePreparation;
	}

	public String getModelEop() {
		return modelEop;
	}

	public void setModelEop(String modelEop) {
		this.modelEop = modelEop;
	}

	public String getModelInteriorCode() {
		return modelInteriorCode;
	}

	public void setModelInteriorCode(String modelInteriorCode) {
		this.modelInteriorCode = modelInteriorCode;
	}

	public String getModelSop() {
		return modelSop;
	}

	public void setModelSop(String modelSop) {
		this.modelSop = modelSop;
	}

	public String getNmOfTransmissionGear() {
		return nmOfTransmissionGear;
	}

	public void setNmOfTransmissionGear(String nmOfTransmissionGear) {
		this.nmOfTransmissionGear = nmOfTransmissionGear;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getQtyOfChairs() {
		return qtyOfChairs;
	}

	public void setQtyOfChairs(String qtyOfChairs) {
		this.qtyOfChairs = qtyOfChairs;
	}

	public String getQtyOfDoors() {
		return qtyOfDoors;
	}

	public void setQtyOfDoors(String qtyOfDoors) {
		this.qtyOfDoors = qtyOfDoors;
	}

	public String getStructure() {
		return structure;
	}

	public void setStructure(String structure) {
		this.structure = structure;
	}

	public String getSuperCharger() {
		return superCharger;
	}

	public void setSuperCharger(String superCharger) {
		this.superCharger = superCharger;
	}

	public String getTimerGrade() {
		return timerGrade;
	}

	public void setTimerGrade(String timerGrade) {
		this.timerGrade = timerGrade;
	}

	public String getStandardModelId() {
		return standardModelId;
	}

	public void setStandardModelId(String standardModelId) {
		this.standardModelId = standardModelId;
	}

	public String getStandardModelName() {
		return standardModelName;
	}

	public void setStandardModelName(String standardModelName) {
		this.standardModelName = standardModelName;
	}

	public String getRemarkTransmissionType() {
		return remarkTransmissionType;
	}

	public void setRemarkTransmissionType(String remarkTransmissionType) {
		this.remarkTransmissionType = remarkTransmissionType;
	}

	public String getStandardTypeId() {
		return standardTypeId;
	}

	public void setStandardTypeId(String standardTypeId) {
		this.standardTypeId = standardTypeId;
	}

	public String getStandardTypeName() {
		return standardTypeName;
	}

	public void setStandardTypeName(String standardTypeName) {
		this.standardTypeName = standardTypeName;
	}

	public String getTypeOfAtTransmission() {
		return typeOfAtTransmission;
	}

	public void setTypeOfAtTransmission(String typeOfAtTransmission) {
		this.typeOfAtTransmission = typeOfAtTransmission;
	}

	public Integer getEpcNo() {
		return epcNo;
	}

	public void setEpcNo(Integer epcNo) {
		this.epcNo = epcNo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHlVersion() {
		return hlVersion;
	}

	public void setHlVersion(String hlVersion) {
		this.hlVersion = hlVersion;
	}

	public String getXid() {
		return xid;
	}

	public void setXid(String xid) {
		this.xid = xid;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public String getBatch() {
		return batch;
	}

	public void setBatch(String batch) {
		this.batch = batch;
	}

	public String gettCapacityInLitrel() {
		return tCapacityInLitrel;
	}

	public void settCapacityInLitrel(String tCapacityInLitrel) {
		this.tCapacityInLitrel = tCapacityInLitrel;
	}

	public String gettCarYear() {
		return tCarYear;
	}

	public void settCarYear(String tCarYear) {
		this.tCarYear = tCarYear;
	}

	public String gettCylinderNum() {
		return tCylinderNum;
	}

	public void settCylinderNum(String tCylinderNum) {
		this.tCylinderNum = tCylinderNum;
	}

	public String gettDrvMode() {
		return tDrvMode;
	}

	public void settDrvMode(String tDrvMode) {
		this.tDrvMode = tDrvMode;
	}

	public String gettESuffix() {
		return tESuffix;
	}

	public void settESuffix(String tESuffix) {
		this.tESuffix = tESuffix;
	}

	public String gettEnvStandard() {
		return tEnvStandard;
	}

	public void settEnvStandard(String tEnvStandard) {
		this.tEnvStandard = tEnvStandard;
	}

	public String gettModeNameWithoutYear() {
		return tModeNameWithoutYear;
	}

	public void settModeNameWithoutYear(String tModeNameWithoutYear) {
		this.tModeNameWithoutYear = tModeNameWithoutYear;
	}

	public String gettParagraph() {
		return tParagraph;
	}

	public void settParagraph(String tParagraph) {
		this.tParagraph = tParagraph;
	}

	public String gettPostCode() {
		return tPostCode;
	}

	public void settPostCode(String tPostCode) {
		this.tPostCode = tPostCode;
	}

	public String gettPowerType() {
		return tPowerType;
	}

	public void settPowerType(String tPowerType) {
		this.tPowerType = tPowerType;
	}

	public String gettSetNum() {
		return tSetNum;
	}

	public void settSetNum(String tSetNum) {
		this.tSetNum = tSetNum;
	}

	public String gettTransmission() {
		return tTransmission;
	}

	public void settTransmission(String tTransmission) {
		this.tTransmission = tTransmission;
	}

	public String gettTypeName() {
		return tTypeName;
	}

	public void settTypeName(String tTypeName) {
		this.tTypeName = tTypeName;
	}

	public String gettValid() {
		return tValid;
	}

	public void settValid(String tValid) {
		this.tValid = tValid;
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

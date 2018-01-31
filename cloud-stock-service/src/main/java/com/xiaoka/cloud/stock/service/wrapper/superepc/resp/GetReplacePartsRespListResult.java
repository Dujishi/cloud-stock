package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 原厂替换件（type=2）或者品牌替换配件
 * Created by sabo on 17/11/2017.
 */
public class GetReplacePartsRespListResult implements Serializable {

	private static final long serialVersionUID = 5962128111736377790L;
	/**
	 * 配件编号, 原厂替换件信息，type=2
	 * 品牌件号，品牌替换配件信息，type=3
	 */
	@JsonProperty("kps_code")
	private String kpsCode;

	/**
	 * 品牌，原厂替换件信息，type=2
	 */
	@JsonProperty("c_oem_brand")
	private String cOemBrand;

	/**
	 * 厂商，原厂替换件信息，type=2
	 */
	@JsonProperty("c_oem_abbrebiation")
	private String cOemAbbrebiation;

	/**
	 * epc_no，原厂替换件信息，type=2
	 */
	@JsonProperty("epc_no")
	private String epcNo;

	/**
	 * GPID， 原厂替换件信息，type=2
	 */
	@JsonProperty("gp_id")
	private String gpId;

	/**
	 * 品牌，品牌替换配件信息，type=3
	 */
	@JsonProperty("part_brand")
	private String partBrand;

	/**
	 * 品牌Logo，品牌替换配件信息，type=3
	 */
	@JsonProperty("logo_path")
	private String logoPath;

	/**
	 * BP_ID，品牌替换配件信息，type=3
	 */
	@JsonProperty("bp_id")
	private int bpId;

	/**
	 * 品牌配件配注，品牌替换配件信息，type=3
	 */
	@JsonProperty("M_REMARK")
	private String mRemark;

	public void setEpcNo(String epcNo) {
		this.epcNo = epcNo;
	}
	public String getEpcNo() {
		return epcNo;
	}

	public void setGpId(String gpId) {
		this.gpId = gpId;
	}
	public String getGpId() {
		return gpId;
	}

	public void setCOemAbbrebiation(String cOemAbbrebiation) {
		this.cOemAbbrebiation = cOemAbbrebiation;
	}
	public String getCOemAbbrebiation() {
		return cOemAbbrebiation;
	}

	public void setKpsCode(String kpsCode) {
		this.kpsCode = kpsCode;
	}
	public String getKpsCode() {
		return kpsCode;
	}

	public void setCOemBrand(String cOemBrand) {
		this.cOemBrand = cOemBrand;
	}
	public String getCOemBrand() {
		return cOemBrand;
	}

	public String getcOemBrand() {
		return cOemBrand;
	}

	public void setcOemBrand(String cOemBrand) {
		this.cOemBrand = cOemBrand;
	}

	public String getcOemAbbrebiation() {
		return cOemAbbrebiation;
	}

	public void setcOemAbbrebiation(String cOemAbbrebiation) {
		this.cOemAbbrebiation = cOemAbbrebiation;
	}

	public String getPartBrand() {
		return partBrand;
	}

	public void setPartBrand(String partBrand) {
		this.partBrand = partBrand;
	}

	public String getLogoPath() {
		return logoPath;
	}

	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}

	public int getBpId() {
		return bpId;
	}

	public void setBpId(int bpId) {
		this.bpId = bpId;
	}

	public String getMRemark() {
		return mRemark;
	}

	public void setMRemark(String mRemark) {
		this.mRemark = mRemark;
	}
}

package com.xiaoka.cloud.stock.core.region.entity;

/**
 * for car brand
 * CarCityEntity
 *
 * @author auto-generate
 */
public class CarCityEntity {

	private Integer id;
	private String name;
	private String mark;
	private String code;
	private String areaCode;
	private String bankCity;
	private Boolean isValid;
	private Integer frameNo;
	private Integer engineNo;
	private String violationCode;
	private Integer province;
	private Boolean isShow;
	private Boolean bankCityValid;
	private Integer parentId;
	private Integer queryViolationStatus;
	private String pinyin;
	private Integer coding;

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getMark() {
		return this.mark;
	}

	public String getCode() {
		return this.code;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public String getBankCity() {
		return this.bankCity;
	}

	public Boolean isIsValid() {
		return this.isValid;
	}

	public Integer getFrameNo() {
		return this.frameNo;
	}

	public Integer getEngineNo() {
		return this.engineNo;
	}

	public String getViolationCode() {
		return this.violationCode;
	}

	public Integer getProvince() {
		return this.province;
	}

	public Boolean isIsShow() {
		return this.isShow;
	}

	public Boolean isBankCityValid() {
		return this.bankCityValid;
	}

	public Integer getParentId() {
		return this.parentId;
	}

	public Integer getQueryViolationStatus() {
		return this.queryViolationStatus;
	}

	public String getPinyin() {
		return this.pinyin;
	}

	public Integer getCoding() {
		return this.coding;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public void setMark(String mark) {
		this.mark = mark == null ? null : mark.trim();
	}

	public void setCode(String code) {
		this.code = code == null ? null : code.trim();
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode == null ? null : areaCode.trim();
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity == null ? null : bankCity.trim();
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public void setFrameNo(Integer frameNo) {
		this.frameNo = frameNo;
	}

	public void setEngineNo(Integer engineNo) {
		this.engineNo = engineNo;
	}

	public void setViolationCode(String violationCode) {
		this.violationCode = violationCode == null ? null : violationCode.trim();
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setIsShow(Boolean isShow) {
		this.isShow = isShow;
	}

	public void setBankCityValid(Boolean bankCityValid) {
		this.bankCityValid = bankCityValid;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setQueryViolationStatus(Integer queryViolationStatus) {
		this.queryViolationStatus = queryViolationStatus;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin == null ? null : pinyin.trim();
	}

	public void setCoding(Integer coding) {
		this.coding = coding;
	}

}
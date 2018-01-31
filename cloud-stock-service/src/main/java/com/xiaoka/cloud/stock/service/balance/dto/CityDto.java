package com.xiaoka.cloud.stock.service.balance.dto;

import java.io.Serializable;

/**
 * 城市信息
 * Created by Luker on 2016/12/19.
 */
public class CityDto implements Serializable {

	/**
	 * 城市名
	 */
	private String name ;
	/**
	 * 城市代号
	 */
	private String code ;
	/**
	 * 省份代号
	 */
	private String areaCode ;
	/**
	 * 城市名称
	 */
	private String bankCity ;
	/**
	 * 省份id
	 */
	private String province ;
	/**
	 * 父级城市
	 */
	private Integer parentId ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"name\":\"").append(name).append('\"');
		sb.append(", \"code\":\"").append(code).append('\"');
		sb.append(", \"areaCode\":\"").append(areaCode).append('\"');
		sb.append(", \"bankCity\":\"").append(bankCity).append('\"');
		sb.append(", \"province\":\"").append(province).append('\"');
		sb.append(", \"parentId\":").append(parentId);
		sb.append('}');
		return sb.toString();
	}
}

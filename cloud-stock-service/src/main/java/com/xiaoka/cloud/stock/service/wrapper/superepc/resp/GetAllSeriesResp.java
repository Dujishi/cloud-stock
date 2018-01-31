package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 正时1.2查询厂商、品牌接口，接口输出参数
 * Created by sabo on 16/11/2017.
 */
public class GetAllSeriesResp implements Serializable {

	private static final long serialVersionUID = -6495271171117632829L;
	/**
	 * 品牌
	 */
	@JsonProperty("c_oem_brand")
	private String cOemBrand;
	/**
	 * 厂牌
	 */
	@JsonProperty("c_oem_name")
	private String cOemName;
	/**
	 * 厂牌（展示）
	 */
	@JsonProperty("c_oem_name_show")
	private String cOemNameShow;
	/**
	 * 首字母
	 */
	@JsonProperty("t_letter")
	private String tLetter;
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

	public void setTLetter(String tLetter) {
		this.tLetter = tLetter;
	}
	public String getTLetter() {
		return tLetter;
	}
}

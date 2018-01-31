package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetAdapterModelsResp implements Serializable{

	private static final long serialVersionUID = -7285562418683418839L;
	@JsonProperty("c_oem_brand")
	private String cOemBrand;
	@JsonProperty("c_oem_name")
	private String cOemName;
	@JsonProperty("c_series_BBG")
	private String cSeriesBbg;
	@JsonProperty("c_structure")
	private String cStructure;
	@JsonProperty("c_timer_model_name")
	private String cTimerModelName;
	@JsonProperty("epc_no")
	private String epcNo;
	@JsonProperty("part_brand")
	private String partBrand;
	private String tid;
	@JsonProperty("timer_type")
	private String timerType;
	private String type;

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

	public void setCSeriesBbg(String cSeriesBbg) {
		this.cSeriesBbg = cSeriesBbg;
	}

	public String getCSeriesBbg() {
		return cSeriesBbg;
	}

	public void setCStructure(String cStructure) {
		this.cStructure = cStructure;
	}

	public String getCStructure() {
		return cStructure;
	}

	public void setCTimerModelName(String cTimerModelName) {
		this.cTimerModelName = cTimerModelName;
	}

	public String getCTimerModelName() {
		return cTimerModelName;
	}

	public void setEpcNo(String epcNo) {
		this.epcNo = epcNo;
	}

	public String getEpcNo() {
		return epcNo;
	}

	public void setPartBrand(String partBrand) {
		this.partBrand = partBrand;
	}

	public String getPartBrand() {
		return partBrand;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTid() {
		return tid;
	}

	public void setTimerType(String timerType) {
		this.timerType = timerType;
	}

	public String getTimerType() {
		return timerType;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

}

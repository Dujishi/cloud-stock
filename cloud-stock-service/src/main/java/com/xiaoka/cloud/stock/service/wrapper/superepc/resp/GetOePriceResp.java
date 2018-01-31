package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetOePriceResp implements Serializable {

	private static final long serialVersionUID = 6214214476429218290L;
	@JsonProperty("car_brand")
	private String carBrand;
	@JsonProperty("car_oem")
	private String carOem;
	@JsonProperty("epc_no")
	private String epcNo;
	@JsonProperty("kps_code")
	private String kpsCode;
	@JsonProperty("kps_code_tmp")
	private String kpsCodeTmp;
	private String name;
	private String price;

	public void setCarBrand(String carBrand) {
		this.carBrand = carBrand;
	}

	public String getCarBrand() {
		return carBrand;
	}

	public void setCarOem(String carOem) {
		this.carOem = carOem;
	}

	public String getCarOem() {
		return carOem;
	}

	public void setEpcNo(String epcNo) {
		this.epcNo = epcNo;
	}

	public String getEpcNo() {
		return epcNo;
	}

	public void setKpsCode(String kpsCode) {
		this.kpsCode = kpsCode;
	}

	public String getKpsCode() {
		return kpsCode;
	}

	public void setKpsCodeTmp(String kpsCodeTmp) {
		this.kpsCodeTmp = kpsCodeTmp;
	}

	public String getKpsCodeTmp() {
		return kpsCodeTmp;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPrice() {
		return price;
	}
}

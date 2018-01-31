package com.xiaoka.cloud.stock.service.mqmessage.dto;

import java.io.Serializable;

/**
 * EPC消息通知的对象
 *
 * @author gancao 2017/11/23 14:52
 * @see [相关类/方法]
 * @since [版本号]
 */
public class EpcNotifyDto implements Serializable {

	private static final long serialVersionUID = 8877678497316536901L;

	//vin码
	private String vin;
	//车款id
	private Integer modelId;
	//分类类型
	private Integer type;
	//子总成id
	private Integer subAssemblyId;
	//总成名称
	private String assemblyName;
	//子总成名称
	private String subAssemblyName;
	//图号
	private String picNum;
	//零件号
	private String partCode;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Integer getModelId() {
		return modelId;
	}

	public void setModelId(Integer modelId) {
		this.modelId = modelId;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSubAssemblyId() {
		return subAssemblyId;
	}

	public void setSubAssemblyId(Integer subAssemblyId) {
		this.subAssemblyId = subAssemblyId;
	}

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public String getSubAssemblyName() {
		return subAssemblyName;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}
}

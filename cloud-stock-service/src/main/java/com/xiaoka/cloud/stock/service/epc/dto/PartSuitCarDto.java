package com.xiaoka.cloud.stock.service.epc.dto;

import java.io.Serializable;

/**
 * 配件适用车型
 *
 * @author gancao 2017/11/13 11:29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartSuitCarDto extends CarModelDto implements Serializable {

	private static final long serialVersionUID = 2153675201371809883L;

	//配件图号
	private String picNum;
	//图号名
	private String picName;
	//分总成名称即零件组
	private String subAssemblyName;
	//是否原厂
	private String type;
	/**
	 * 请求的扩展参数
	 */
	protected String extraParam;

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getSubAssemblyName() {
		return subAssemblyName;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getExtraParam() {
		return extraParam;
	}

	public void setExtraParam(String extraParam) {
		this.extraParam = extraParam;
	}
}

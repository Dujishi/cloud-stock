package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/6 17:29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class GetPicInfoForAssemblyResp implements Serializable {

	private static final long serialVersionUID = 2186165366002879709L;
	private String enable;
	@JsonProperty("kps_name")
	private String kpsName;
	@JsonProperty("pic_name")
	private String picName;
	@JsonProperty("pic_num")
	private String picNum;
	@JsonProperty("pic_path")
	private String picPath;
	@JsonProperty("sub_assembly_name")
	private String subAssemblyName;
	@JsonProperty("timer_id")
	private String timerId;

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getKpsName() {
		return kpsName;
	}

	public void setKpsName(String kpsName) {
		this.kpsName = kpsName;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getSubAssemblyName() {
		return subAssemblyName;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName;
	}

	public String getTimerId() {
		return timerId;
	}

	public void setTimerId(String timerId) {
		this.timerId = timerId;
	}
}

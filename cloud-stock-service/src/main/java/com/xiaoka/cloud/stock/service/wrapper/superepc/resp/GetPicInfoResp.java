package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetPicInfoResp implements Serializable {

	private static final long serialVersionUID = 8773455196507845856L;
	private String enable;
	@JsonProperty("kps_name")
	private String kpsName;
	@JsonProperty("pic_name")
	private String picName;
	@JsonProperty("pic_num")
	private String picNum;
	@JsonProperty("pic_path")
	private String picPath;
	@JsonProperty("timer_id")
	private String timerId;

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEnable() {
		return enable;
	}

	public void setKpsName(String kpsName) {
		this.kpsName = kpsName;
	}

	public String getKpsName() {
		return kpsName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicPath(String picPath) {
		this.picPath = picPath;
	}

	public String getPicPath() {
		return picPath;
	}

	public void setTimerId(String timerId) {
		this.timerId = timerId;
	}

	public String getTimerId() {
		return timerId;
	}

}

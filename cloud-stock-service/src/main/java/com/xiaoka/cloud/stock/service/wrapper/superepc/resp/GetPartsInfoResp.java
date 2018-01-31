package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetPartsInfoResp implements Serializable {

	private static final long serialVersionUID = -1137455486082855363L;
	private String assembly;
	private String enable;
	@JsonProperty("epc_no")
	private String epcNo;
	@JsonProperty("gp_id")
	private String gpId;
	@JsonProperty("kps_code")
	private String kpsCode;
	@JsonProperty("kps_name")
	private String kpsName;
	@JsonProperty("per_use_num")
	private String perUseNum;
	@JsonProperty("pic_name")
	private String picName;
	@JsonProperty("pic_num")
	private String picNum;
	@JsonProperty("pic_path")
	private String picPath;
	@JsonProperty("pic_sequence")
	private String picSequence;
	@JsonProperty("remark_brief")
	private String remarkBrief;
	@JsonProperty("remark_detail")
	private String remarkDetail;
	@JsonProperty("sub_assembly")
	private String subAssembly;
	private String tid;
	@JsonProperty("timer_assembly")
	private String timerAssembly;
	@JsonProperty("timer_assembly_id")
	private String timerAssemblyId;
	@JsonProperty("timer_id")
	private String timerId;
	@JsonProperty("timer_name")
	private String timerName;
	@JsonProperty("timer_stand_name")
	private String timerStandName;
	@JsonProperty("timer_sub_assembly")
	private String timerSubAssembly;
	@JsonProperty("timer_sub_assembly_id")
	private String timerSubAssemblyId;

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEnable() {
		return enable;
	}

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

	public void setKpsCode(String kpsCode) {
		this.kpsCode = kpsCode;
	}

	public String getKpsCode() {
		return kpsCode;
	}

	public void setKpsName(String kpsName) {
		this.kpsName = kpsName;
	}

	public String getKpsName() {
		return kpsName;
	}

	public void setPerUseNum(String perUseNum) {
		this.perUseNum = perUseNum;
	}

	public String getPerUseNum() {
		return perUseNum;
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

	public void setPicSequence(String picSequence) {
		this.picSequence = picSequence;
	}

	public String getPicSequence() {
		return picSequence;
	}

	public void setRemarkBrief(String remarkBrief) {
		this.remarkBrief = remarkBrief;
	}

	public String getRemarkBrief() {
		return remarkBrief;
	}

	public void setRemarkDetail(String remarkDetail) {
		this.remarkDetail = remarkDetail;
	}

	public String getRemarkDetail() {
		return remarkDetail;
	}

	public void setSubAssembly(String subAssembly) {
		this.subAssembly = subAssembly;
	}

	public String getSubAssembly() {
		return subAssembly;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTid() {
		return tid;
	}

	public void setTimerAssembly(String timerAssembly) {
		this.timerAssembly = timerAssembly;
	}

	public String getTimerAssembly() {
		return timerAssembly;
	}

	public void setTimerAssemblyId(String timerAssemblyId) {
		this.timerAssemblyId = timerAssemblyId;
	}

	public String getTimerAssemblyId() {
		return timerAssemblyId;
	}

	public void setTimerId(String timerId) {
		this.timerId = timerId;
	}

	public String getTimerId() {
		return timerId;
	}

	public void setTimerName(String timerName) {
		this.timerName = timerName;
	}

	public String getTimerName() {
		return timerName;
	}

	public void setTimerStandName(String timerStandName) {
		this.timerStandName = timerStandName;
	}

	public String getTimerStandName() {
		return timerStandName;
	}

	public void setTimerSubAssembly(String timerSubAssembly) {
		this.timerSubAssembly = timerSubAssembly;
	}

	public String getTimerSubAssembly() {
		return timerSubAssembly;
	}

	public void setTimerSubAssemblyId(String timerSubAssemblyId) {
		this.timerSubAssemblyId = timerSubAssemblyId;
	}

	public String getTimerSubAssemblyId() {
		return timerSubAssemblyId;
	}
}

package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetTimerStandardPartsResp implements Serializable {

	private static final long serialVersionUID = 910325209532413014L;
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
	@JsonProperty("timer_type")
	private String timerType;

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

	public void setTimerType(String timerType) {
		this.timerType = timerType;
	}

	public String getTimerType() {
		return timerType;
	}

}

package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Created by suqin on 16/11/2017.
 */
public class GetAssemblyCatalogueResp implements Serializable{

	private static final long serialVersionUID = 4060681986710064956L;
	private String assembly;
	@JsonProperty("assembly_enable")
	private String assemblyEnable;
	@JsonProperty("sub_assembly")
	private String subAssembly;
	@JsonProperty("sub_assembly_enable")
	private String subAssemblyEnable;
	@JsonProperty("timer_assembly_id")
	private String timerAssemblyId;
	@JsonProperty("timer_sub_assembly_id")
	private String timerSubAssemblyId;

	public void setAssembly(String assembly) {
		this.assembly = assembly;
	}

	public String getAssembly() {
		return assembly;
	}

	public void setAssemblyEnable(String assemblyEnable) {
		this.assemblyEnable = assemblyEnable;
	}

	public String getAssemblyEnable() {
		return assemblyEnable;
	}

	public void setSubAssembly(String subAssembly) {
		this.subAssembly = subAssembly;
	}

	public String getSubAssembly() {
		return subAssembly;
	}

	public void setSubAssemblyEnable(String subAssemblyEnable) {
		this.subAssemblyEnable = subAssemblyEnable;
	}

	public String getSubAssemblyEnable() {
		return subAssemblyEnable;
	}

	public void setTimerAssemblyId(String timerAssemblyId) {
		this.timerAssemblyId = timerAssemblyId;
	}

	public String getTimerAssemblyId() {
		return timerAssemblyId;
	}

	public void setTimerSubAssemblyId(String timerSubAssemblyId) {
		this.timerSubAssemblyId = timerSubAssemblyId;
	}

	public String getTimerSubAssemblyId() {
		return timerSubAssemblyId;
	}

}

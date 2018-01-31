package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2018/1/10 17:24
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSubGroupPartResp implements Serializable {

	private static final long serialVersionUID = 7572458238457792958L;

	private String status;
	private int flage;
	private int code;
	private String uid;
	@JsonProperty("itid")
	private String itId;
	private String pid;
	private String mid;
	@JsonProperty("pid_list")
	private String pidList;
	@JsonProperty("subgroupname")
	private String subGroupName;
	private int time;
	private String msg;
	private String auth;
	private List<List<ZeroPartInfoResp>> data;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFlage() {
		return flage;
	}

	public void setFlage(int flage) {
		this.flage = flage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getItId() {
		return itId;
	}

	public void setItId(String itId) {
		this.itId = itId;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPidList() {
		return pidList;
	}

	public void setPidList(String pidList) {
		this.pidList = pidList;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public List<List<ZeroPartInfoResp>> getData() {
		return data;
	}

	public void setData(List<List<ZeroPartInfoResp>> data) {
		this.data = data;
	}
}

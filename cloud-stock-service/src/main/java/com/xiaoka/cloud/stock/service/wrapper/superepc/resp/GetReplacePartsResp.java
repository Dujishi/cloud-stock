package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * 查找替换件输出总对象
 * Created by sabo on 17/11/2017.
 */
public class GetReplacePartsResp implements Serializable {

	private static final long serialVersionUID = 5733670742980992694L;
	@JsonProperty("statusCode")
	private int statuscode;
	private String desc;
	private String map;
	@JsonProperty("detailInfo")
	private String detailinfo;
	private List<GetReplacePartsRespList> list;
	private int size;

	public int getStatuscode() {
		return statuscode;
	}

	public void setStatuscode(int statuscode) {
		this.statuscode = statuscode;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getMap() {
		return map;
	}

	public void setMap(String map) {
		this.map = map;
	}

	public String getDetailinfo() {
		return detailinfo;
	}

	public void setDetailinfo(String detailinfo) {
		this.detailinfo = detailinfo;
	}

	public List<GetReplacePartsRespList> getList() {
		return list;
	}

	public void setList(List<GetReplacePartsRespList> list) {
		this.list = list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}

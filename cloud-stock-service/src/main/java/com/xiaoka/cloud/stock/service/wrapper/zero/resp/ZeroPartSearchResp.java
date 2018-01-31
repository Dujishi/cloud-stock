package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2018/1/10 17:37
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroPartSearchResp implements Serializable {

	private static final long serialVersionUID = 332600825828658459L;

	private int code;
	@JsonProperty("showmessage")
	private List<String> showMessage;
	private List<PartDetailDto> partdetail;
	private String img;
	private int time;
	private String msg;
	private List<ZeroPartInfoResp> data;
	@JsonProperty("headname")
	private List<String> headName;
	private String pid;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public List<String> getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(List<String> showMessage) {
		this.showMessage = showMessage;
	}

	public List<PartDetailDto> getPartdetail() {
		return partdetail;
	}

	public void setPartdetail(List<PartDetailDto> partdetail) {
		this.partdetail = partdetail;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
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

	public List<ZeroPartInfoResp> getData() {
		return data;
	}

	public void setData(List<ZeroPartInfoResp> data) {
		this.data = data;
	}

	public List<String> getHeadName() {
		return headName;
	}

	public void setHeadName(List<String> headName) {
		this.headName = headName;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
}

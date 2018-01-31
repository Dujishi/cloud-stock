package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/18 15:24
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartSearchResp implements Serializable{

	private static final long serialVersionUID = 9107512494995679670L;

	private int status;
	private String remark;
	@JsonProperty("has_compt")
	private int hasCompt;
	@JsonProperty("s_pid")
	private String sPid;
	private String brand;
	private String pid;
	private String label;
	private String prices;
	@JsonProperty("has_replace")
	private int hasReplace;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public int getHasCompt() {
		return hasCompt;
	}

	public void setHasCompt(int hasCompt) {
		this.hasCompt = hasCompt;
	}

	public String getsPid() {
		return sPid;
	}

	public void setsPid(String sPid) {
		this.sPid = sPid;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public int getHasReplace() {
		return hasReplace;
	}

	public void setHasReplace(int hasReplace) {
		this.hasReplace = hasReplace;
	}
}

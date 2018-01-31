package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroPartInfoDto implements Serializable {
	private static final long serialVersionUID = -9168214807085009548L;

	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 零件号
	 */
	private String pid;
	/**
	 * 真实零件号
	 */
	private String realPid;
	/**
	 * 零件型号
	 */
	private String pidModel;
	/**
	 * 原厂名称
	 */
	private String pidLabel;
	/**
	 * 零件描述
	 */
	private String pidRemark;
	/**
	 * 访问auth
	 */
	private String auth;

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

	public String getRealPid() {
		return realPid;
	}

	public void setRealPid(String realPid) {
		this.realPid = realPid;
	}

	public String getPidModel() {
		return pidModel;
	}

	public void setPidModel(String pidModel) {
		this.pidModel = pidModel;
	}

	public String getPidLabel() {
		return pidLabel;
	}

	public void setPidLabel(String pidLabel) {
		this.pidLabel = pidLabel;
	}

	public String getPidRemark() {
		return pidRemark;
	}

	public void setPidRemark(String pidRemark) {
		this.pidRemark = pidRemark;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

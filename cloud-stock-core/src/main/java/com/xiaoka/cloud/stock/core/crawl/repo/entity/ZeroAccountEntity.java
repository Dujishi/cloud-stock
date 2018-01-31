package com.xiaoka.cloud.stock.core.crawl.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for account
 * ZeroAccountEntity
 *
 * @author gancao
 */
public class ZeroAccountEntity implements Serializable {

	private static final long serialVersionUID = -7458641204233394349L;
	private Integer id;
	private Integer type;
	private Integer supplierId;
	private String phone;
	private String password;
	private String ip;
	private Integer port;
	private String realIp;
	private Integer isValid;
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getPassword() {
		return this.password;
	}

	public String getIp() {
		return this.ip;
	}

	public Integer getPort() {
		return this.port;
	}

	public Integer getIsValid() {
		return this.isValid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public void setIp(String ip) {
		this.ip = ip == null ? null : ip.trim();
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
	}

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}
}
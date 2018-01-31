package com.xiaoka.cloud.stock.core.supplier.repo.entity;

import java.util.Date;

/**
 * for car brand
 * CloudCompanyStrategyEntity
 *
 * @author zhouze
 */
public class CloudCompanyStrategyEntity {

	private Integer id;
	private Integer companyId;
	private String  strategyIdentity;
	private boolean isDelete;
	private Date    createTime;
	private Date    updateTime;

	public Integer getId() {
		return this.id;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public String getStrategyIdentity() {
		return this.strategyIdentity;
	}

	public boolean isIsDelete() {
		return this.isDelete;
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

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setStrategyIdentity(String strategyIdentity) {
		this.strategyIdentity = strategyIdentity == null ? null : strategyIdentity.trim();
	}

	public void setIsDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
	}

}
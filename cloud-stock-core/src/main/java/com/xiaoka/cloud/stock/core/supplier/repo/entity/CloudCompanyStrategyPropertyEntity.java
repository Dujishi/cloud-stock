package com.xiaoka.cloud.stock.core.supplier.repo.entity;

import java.util.Date;

/**
 * for car brand
 * CloudCompanyStrategyPropertyEntity
 *
 * @author zhouze
 */
public class CloudCompanyStrategyPropertyEntity {

	private Integer id;
	private Integer strategyId;
	private String  proName;
	private String  proValue;
	private boolean isDelete;
	private Date    createTime;
	private Date    updateTime;

	public Integer getId() {
		return this.id;
	}

	public Integer getStrategyId() {
		return this.strategyId;
	}

	public String getProName() {
		return this.proName;
	}

	public String getProValue() {
		return this.proValue;
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

	public void setStrategyId(Integer strategyId) {
		this.strategyId = strategyId;
	}

	public void setProName(String proName) {
		this.proName = proName == null ? null : proName.trim();
	}

	public void setProValue(String proValue) {
		this.proValue = proValue == null ? null : proValue.trim();
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
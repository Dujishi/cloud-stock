package com.xiaoka.cloud.stock.core.supplier.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for car brand
 * CloudCompanyStrategyAuthorizedEntity
 *
 * @author zhouze
 */
public class CloudCompanyStrategyAuthorizedEntity implements Serializable {
	private static final long serialVersionUID = 8397475013513758434L;

	private Integer id;
	private Integer companyId;
	private Integer userId;
	private String  phone;
	private Integer flag;
	private boolean isDelete;
	private Date    createTime;
	private Date    updateTime;

	public Integer getId() {
		return this.id;
	}

	public Integer getCompanyId() {
		return this.companyId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public String getPhone() {
		return this.phone;
	}

	public Integer getFlag() {
		return this.flag;
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

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
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
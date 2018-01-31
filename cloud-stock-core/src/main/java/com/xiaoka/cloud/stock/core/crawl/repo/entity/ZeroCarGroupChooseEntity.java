package com.xiaoka.cloud.stock.core.crawl.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for car group
 * ZeroCarGroupChooseEntity
 *
 * @author gancao
 */
public class ZeroCarGroupChooseEntity implements Serializable{

	private static final long serialVersionUID = 8089573145357178416L;
	private Integer id;
	private Integer cId;
	private Integer successStatus;
	private String auth;
	private String uri;
	private String groupName;
	private String groupNum;
	private String name;
	private Integer isValid;
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getSuccessStatus() {
		return successStatus;
	}

	public void setSuccessStatus(Integer successStatus) {
		this.successStatus = successStatus;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}
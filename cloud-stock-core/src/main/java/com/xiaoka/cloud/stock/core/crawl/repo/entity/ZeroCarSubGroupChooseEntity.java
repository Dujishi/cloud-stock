package com.xiaoka.cloud.stock.core.crawl.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for car sub group
 * ZeroCarSubGroupChooseEntity
 *
 * @author gancao
 */
public class ZeroCarSubGroupChooseEntity implements Serializable {

	private static final long serialVersionUID = -7265026250948738579L;
	private Integer id;
	private Integer innerGroupId;
	private Integer innerSubGroupId;
	private Integer groupId;
	private String groupName;
	private Integer successStatus;
	private String auth;
	private String uri;
	private String subGroupName;
	private String subGroup;
	private String num;
	private String mid;
	private String url;
	private Integer isValid;
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getInnerGroupId() {
		return innerGroupId;
	}

	public void setInnerGroupId(Integer innerGroupId) {
		this.innerGroupId = innerGroupId;
	}

	public Integer getInnerSubGroupId() {
		return innerSubGroupId;
	}

	public void setInnerSubGroupId(Integer innerSubGroupId) {
		this.innerSubGroupId = innerSubGroupId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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
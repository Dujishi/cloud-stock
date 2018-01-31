package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/10 17:05
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSubGroupResp implements Serializable {

	private static final long serialVersionUID = -1939683943866995603L;

	@JsonProperty("base_brandCode")
	private String baseBrandCode;
	private String uid;
	private String url;
	private String mid;
	@JsonProperty("subgroupname")
	private String subGroupName;
	@JsonProperty("uri_param")
	private UriParam uriParam;
	private String num;
	@JsonProperty("is_filter")
	private int isFilter;
	private String auth;
	@JsonProperty("has_subs")
	private boolean hasSubs;
	private String model;
	@JsonProperty("subgroup")
	private String subGroup;
	private String description;

	public String getBaseBrandCode() {
		return baseBrandCode;
	}

	public void setBaseBrandCode(String baseBrandCode) {
		this.baseBrandCode = baseBrandCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public UriParam getUriParam() {
		return uriParam;
	}

	public void setUriParam(UriParam uriParam) {
		this.uriParam = uriParam;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getIsFilter() {
		return isFilter;
	}

	public void setIsFilter(int isFilter) {
		this.isFilter = isFilter;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public boolean isHasSubs() {
		return hasSubs;
	}

	public void setHasSubs(boolean hasSubs) {
		this.hasSubs = hasSubs;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/18 14:21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarModelGroupChooseResp implements Serializable {

	private static final long serialVersionUID = -8292507850131273182L;

	private String name;
	private String img;
	@JsonProperty("uri_param")
	private UriParam uriParam;
	@JsonProperty("has_subs")
	private boolean hasSubs;
	@JsonProperty("groupname")
	private String groupName;
	@JsonProperty("groupnum")
	private String groupNum;
	private String auth;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public UriParam getUriParam() {
		return uriParam;
	}

	public void setUriParam(UriParam uriParam) {
		this.uriParam = uriParam;
	}

	public boolean isHasSubs() {
		return hasSubs;
	}

	public void setHasSubs(boolean hasSubs) {
		this.hasSubs = hasSubs;
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

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}


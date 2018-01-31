package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/10 16:57
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroGroupResp implements Serializable {

	private static final long serialVersionUID = 3711289514366891554L;

	private String name;
	private String img;
	@JsonProperty("uri_param")
	private UriParam uriParam;
	private String auth;
	private String groupname;
	private String groupnum;
	@JsonProperty("has_subs")
	private boolean hasSubs;

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

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public String getGroupnum() {
		return groupnum;
	}

	public void setGroupnum(String groupnum) {
		this.groupnum = groupnum;
	}

	public boolean isHasSubs() {
		return hasSubs;
	}

	public void setHasSubs(boolean hasSubs) {
		this.hasSubs = hasSubs;
	}
}

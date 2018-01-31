package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/15 10:43
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarModelChooseResp implements Serializable{

	private static final long serialVersionUID = -4692337378249168416L;
	@JsonProperty("has_next")
	private String hasNext;
	private String code;
	private String name;
	private String keys;
	private String uri;
	private String auth;

	public String getHasNext() {
		return hasNext;
	}

	public void setHasNext(String hasNext) {
		this.hasNext = hasNext;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKeys() {
		return keys;
	}

	public void setKeys(String keys) {
		this.keys = keys;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

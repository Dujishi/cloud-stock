package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/18 14:25
 * @see [相关类/方法]
 * @since [版本号]
 */
public class UriParam implements Serializable {

	private static final long serialVersionUID = -3107956036854849915L;
	private String p;
	private String code;
	private boolean firstload;
	private String carstype;
	private String auth;

	public String getP() {
		return p;
	}

	public void setP(String p) {
		this.p = p;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isFirstload() {
		return firstload;
	}

	public void setFirstload(boolean firstload) {
		this.firstload = firstload;
	}

	public String getCarstype() {
		return carstype;
	}

	public void setCarstype(String carstype) {
		this.carstype = carstype;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

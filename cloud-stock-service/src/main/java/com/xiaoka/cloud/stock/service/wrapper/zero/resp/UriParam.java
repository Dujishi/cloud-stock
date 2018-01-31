package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/10 16:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public class UriParam implements Serializable {

	private static final long serialVersionUID = -8969078351014036772L;

	private String code;
	private boolean firstload;
	private String vin;
	private String auth;

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

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

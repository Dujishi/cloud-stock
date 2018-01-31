package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/12/13 17:16
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroResp<T> implements Serializable {

	private static final long serialVersionUID = -2438274647894538699L;
	private Integer code;
	private String msg;
	private String title;
	private String auth;
	private String uri;
	private T data;
	private List<String> mains;

	private String brand;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<String> getMains() {
		return mains;
	}

	public void setMains(List<String> mains) {
		this.mains = mains;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
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

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}
}

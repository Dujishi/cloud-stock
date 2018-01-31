package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import java.io.Serializable;

/**
 * Created by sabo on 16/11/2017.
 */
public class SuperEPCResp<T> implements Serializable {

	private static final long serialVersionUID = -6260077769576115278L;
	private String desc;

	private Integer statusCode;

	private Integer size;

	private T result;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(Integer statusCode) {
		this.statusCode = statusCode;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}
}

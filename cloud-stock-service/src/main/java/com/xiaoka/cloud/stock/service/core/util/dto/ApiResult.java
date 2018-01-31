package com.xiaoka.cloud.stock.service.core.util.dto;

import java.io.Serializable;

/**
 *
 * api返回对象
 *
 * @author gancao 2017/11/13 14:12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ApiResult<T> implements Serializable {

	private static final long serialVersionUID = 9070774086985808388L;

	/**
	 * 是否成功
	 */
	private Boolean success;
	/**
	 * 消息码
	 */
	private String code;
	/**
	 * 消息
	 */
	private String message;
	/**
	 * 数据
	 */
	private T data;

	private Integer size;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Integer getSize() {
		return size;
	}

	public void setSize(Integer size) {
		this.size = size;
	}
}

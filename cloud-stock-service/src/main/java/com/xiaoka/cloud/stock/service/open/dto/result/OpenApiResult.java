package com.xiaoka.cloud.stock.service.open.dto.result;

import java.io.Serializable;

/**
 * 对外数据格式
 *
 * @author zhouze
 * @date 2017/11/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public class OpenApiResult<T> implements Serializable {
	private static final long serialVersionUID = -2091087204893304374L;

	/**
	 * 是否成功
	 */
	private Boolean success;
	/**
	 * 消息码
	 */
	private String  errCode;
	/**
	 * 消息
	 */
	private String  message;
	/**
	 * 数据
	 */
	private T       data;

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
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

}

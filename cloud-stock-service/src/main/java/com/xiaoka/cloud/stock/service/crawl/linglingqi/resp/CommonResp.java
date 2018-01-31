package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/12/28
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CommonResp<T> implements Serializable {
	private static final long serialVersionUID = 8580116214698362503L;

	private Integer code;

	private String msg;

	private String error;

	private List<T> data;

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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}


	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
}

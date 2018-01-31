package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/29 18:20
 * @see [相关类/方法]
 * @since [版本号]
 */
public class BaseZeroResp implements Serializable {

	private static final long serialVersionUID = -7379074809905563961L;
	private Integer code;
	private String msg;

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
}

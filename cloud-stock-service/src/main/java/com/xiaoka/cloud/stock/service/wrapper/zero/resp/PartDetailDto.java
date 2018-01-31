package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/10 17:36
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartDetailDto implements Serializable{

	private static final long serialVersionUID = -7127622108608540013L;

	private String key;
	private String value;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 品牌
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroBrandDto implements Serializable {
	private static final long serialVersionUID = -3521626809421810094L;

	/**
	 * 品牌名（英文）
	 */
	private String brand;

	/**
	 * 品牌名
	 */
	private String name;

	/**
	 * 品牌图片地址
	 */
	private String uri;

	/**
	 * 访问auth
	 */
	private String auth;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 市场
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroMarketDto implements Serializable {
	private static final long serialVersionUID = -7382797334299477115L;

	/**
	 * 市场
	 */
	private String market;
	/**
	 * 品牌名
	 */
	private String brand;
	/**
	 * 访问auth
	 */
	private String auth;

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 年份
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroCarYearDto implements Serializable {
	private static final long serialVersionUID = 8893443554131778429L;

	/**
	 * 车型
	 */
	private String carModel;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 市场
	 */
	private String market;
	/**
	 * 年份
	 */
	private String year;
	/**
	 * 访问auth
	 */
	private String auth;

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

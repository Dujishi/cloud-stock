package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 车型
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroCarModelDto implements Serializable {
	private static final long serialVersionUID = 3079722018192518258L;

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
	 * 发动机
	 */
	private String engine;
	/**
	 * 变速箱
	 */
	private String gearBox;
	/**
	 * 访问auth
	 */
	private String auth;

	public ZeroCarModelDto(String brand) {
		this.brand = brand;
	}

	public ZeroCarModelDto() {
	}

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

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getGearBox() {
		return gearBox;
	}

	public void setGearBox(String gearBox) {
		this.gearBox = gearBox;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

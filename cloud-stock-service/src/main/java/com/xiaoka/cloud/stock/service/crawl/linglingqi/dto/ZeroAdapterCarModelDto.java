package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 零件号适配车型
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroAdapterCarModelDto implements Serializable {
	private static final long serialVersionUID = -3716506786393677718L;

	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 零件号
	 */
	private String pid;
	/**
	 * 适配的车型
	 */
	private String carModel;
	/**
	 * 零件组
	 */
	private String groupName;
	/**
	 * 主组
	 */
	private String mainGroupName;
	/**
	 * 市场
	 */
	private String market;
	/**
	 * 年份
	 */
	private String year;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getMainGroupName() {
		return mainGroupName;
	}

	public void setMainGroupName(String mainGroupName) {
		this.mainGroupName = mainGroupName;
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
}

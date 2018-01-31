package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 适配车型
 *
 * @author zhouze
 * @date 2017/12/28
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartAdapterCarResp implements Serializable {
	private static final long serialVersionUID = -7461304308770807944L;

	/**
	 * 车型
	 */
	@JsonProperty("cars_model")
	private String carModel;
	/**
	 * 零件组名称
	 */
	@JsonProperty("group_name")
	private String groupName;
	/**
	 * 主组名称
	 */
	@JsonProperty("main_group_name")
	private String mainGroupName;
	/**
	 * 市场
	 */
	private String market;
	/**
	 * 年份
	 */
	private String year;

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

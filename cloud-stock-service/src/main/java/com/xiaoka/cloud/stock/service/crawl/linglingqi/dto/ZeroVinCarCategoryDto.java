package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroVinCarCategoryDto implements Serializable {
	private static final long serialVersionUID = -808895039863226761L;

	/**
	 * vin码
	 */
	private String vin;
	/**
	 * 车型
	 */
	private String carModel;
	/**
	 * 品牌
	 */
	private String brand;

	/**
	 * 附加信息列表
	 * 例如:
	 * 发动机序号 : 00000000000001052456
	 * 发动机 : B4204T11
	 */
	private List<String>          addInfo;
	/**
	 * 主组列表
	 */
	private List<ZeroVinGroupDto> groups;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
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

	public List<String> getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(List<String> addInfo) {
		this.addInfo = addInfo;
	}

	public List<ZeroVinGroupDto> getGroups() {
		return groups;
	}

	public void setGroups(List<ZeroVinGroupDto> groups) {
		this.groups = groups;
	}
}

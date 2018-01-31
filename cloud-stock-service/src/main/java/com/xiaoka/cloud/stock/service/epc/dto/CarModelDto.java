package com.xiaoka.cloud.stock.service.epc.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 详细车型实体对象
 *
 * @author gancao 2017/11/13 10:11
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarModelDto implements Serializable {

	private static final long serialVersionUID = -7288348961971160116L;

	//车型id
	private Integer carModelId;
	//车型名称
	private String carModelName;
	//品牌名称
	private String carBrandName;
	//车系名称
	private String carSeriesName;
	//厂商名称
	private String makeName;
	//车型配置
	private Map<String, String> carModelConfig;

	private Integer brandId;

	public Integer getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(Integer carModelId) {
		this.carModelId = carModelId;
	}

	public String getCarModelName() {
		return carModelName;
	}

	public void setCarModelName(String carModelName) {
		this.carModelName = carModelName;
	}

	public String getCarBrandName() {
		return carBrandName;
	}

	public void setCarBrandName(String carBrandName) {
		this.carBrandName = carBrandName;
	}

	public String getCarSeriesName() {
		return carSeriesName;
	}

	public void setCarSeriesName(String carSeriesName) {
		this.carSeriesName = carSeriesName;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}

	public Map<String, String> getCarModelConfig() {
		return carModelConfig;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setCarModelConfig(Map<String, String> carModelConfig) {
		this.carModelConfig = carModelConfig;
	}
}

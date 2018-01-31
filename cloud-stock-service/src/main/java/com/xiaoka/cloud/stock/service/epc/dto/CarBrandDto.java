package com.xiaoka.cloud.stock.service.epc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/20 22:12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarBrandDto implements Serializable {

	private static final long serialVersionUID = 4765436736643839856L;
	//品牌名称
	private String brandName;
	//品牌图标地址
	private String brandIconUrl;

	private Integer brandId;
	private String firstLetter;
	//品牌下的年份
	private List<CarYearDto> yearList;

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public String getBrandIconUrl() {
		return brandIconUrl;
	}

	public void setBrandIconUrl(String brandIconUrl) {
		this.brandIconUrl = brandIconUrl;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getFirstLetter() {
		return firstLetter;
	}

	public void setFirstLetter(String firstLetter) {
		this.firstLetter = firstLetter;
	}

	public List<CarYearDto> getYearList() {
		return yearList;
	}

	public void setYearList(List<CarYearDto> yearList) {
		this.yearList = yearList;
	}
}

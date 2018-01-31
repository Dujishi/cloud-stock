package com.xiaoka.cloud.stock.core.region.entity;

/**
 * for car brand
 * CarCityDistrictEntity
 *
 * @author auto-generate
 */
public class CarCityDistrictEntity {

	private Integer id;
	private Integer cityId;
	private String name;
	private String mark;
	private Integer coding;

	public Integer getId() {
		return this.id;
	}

	public Integer getCityId() {
		return this.cityId;
	}

	public String getName() {
		return this.name;
	}

	public String getMark() {
		return this.mark;
	}

	public Integer getCoding() {
		return this.coding;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public void setMark(String mark) {
		this.mark = mark == null ? null : mark.trim();
	}

	public void setCoding(Integer coding) {
		this.coding = coding;
	}

}
package com.xiaoka.cloud.stock.core.region.entity;

/**
 * for car brand
 * CarProvinceEntity
 *
 * @author auto-generate
 */
public class CarProvinceEntity {

	private Integer id;
	private String name;
	private Integer queryViolationStatus;
	private Integer hasCities;
	private Integer coding;

	public Integer getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Integer getQueryViolationStatus() {
		return this.queryViolationStatus;
	}

	public Integer getHasCities() {
		return this.hasCities;
	}

	public Integer getCoding() {
		return this.coding;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name == null ? null : name.trim();
	}

	public void setQueryViolationStatus(Integer queryViolationStatus) {
		this.queryViolationStatus = queryViolationStatus;
	}

	public void setHasCities(Integer hasCities) {
		this.hasCities = hasCities;
	}

	public void setCoding(Integer coding) {
		this.coding = coding;
	}

}
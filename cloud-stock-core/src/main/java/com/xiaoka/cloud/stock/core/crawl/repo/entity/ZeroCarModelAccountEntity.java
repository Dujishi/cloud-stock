package com.xiaoka.cloud.stock.core.crawl.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for car model account
 * ZeroCarModelAccountEntity
 *
 * @author gancao
 */
public class ZeroCarModelAccountEntity implements Serializable{

	private static final long serialVersionUID = -5602046788953626118L;
	private Integer id;
	private String phone;
	private String brand;
	private String carModel;
	private String market;
	private String year;
	private String engine;
	private String gearBox;
	private Integer isValid;
	private Date createTime;
	private Date updateTime;

	public Integer getId() {
		return this.id;
	}

	public String getPhone() {
		return this.phone;
	}

	public String getBrand() {
		return this.brand;
	}

	public String getCarModel() {
		return this.carModel;
	}

	public String getMarket() {
		return this.market;
	}

	public String getYear() {
		return this.year;
	}

	public String getEngine() {
		return this.engine;
	}

	public String getGearBox() {
		return this.gearBox;
	}

	public Integer getIsValid() {
		return this.isValid;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setPhone(String phone) {
		this.phone = phone == null ? null : phone.trim();
	}

	public void setBrand(String brand) {
		this.brand = brand == null ? null : brand.trim();
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel == null ? null : carModel.trim();
	}

	public void setMarket(String market) {
		this.market = market == null ? null : market.trim();
	}

	public void setYear(String year) {
		this.year = year == null ? null : year.trim();
	}

	public void setEngine(String engine) {
		this.engine = engine == null ? null : engine.trim();
	}

	public void setGearBox(String gearBox) {
		this.gearBox = gearBox == null ? null : gearBox.trim();
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime == null ? null : (Date) createTime.clone();
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
	}

}
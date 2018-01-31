package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 根据url返回实体
 * url : https://www.007vin.com/ppyvin/searchvins?vin=YV1FS40C3E2318156&brands=all
 *
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSearchVinDto implements Serializable {
	private static final long serialVersionUID = 5598667537763960078L;

	private String code;

	private String message;

	private String brand;

	private String vin;

	private String vins;

	private String name;

	private List<String> mains;

	private List<String> sub;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getVins() {
		return vins;
	}

	public void setVins(String vins) {
		this.vins = vins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getMains() {
		return mains;
	}

	public void setMains(List<String> mains) {
		this.mains = mains;
	}

	public List<String> getSub() {
		return sub;
	}

	public void setSub(List<String> sub) {
		this.sub = sub;
	}
}

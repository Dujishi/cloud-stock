package com.xiaoka.cloud.stock.soa.api.epc.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/11/29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarBrandDto implements Serializable {
	private static final long serialVersionUID = 4842793736644540697L;

	private Integer brandId;
	private String brandName;

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}

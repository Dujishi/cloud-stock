package com.xiaoka.cloud.stock.service.epc.param;

import java.io.Serializable;
import java.util.List;

/**
 * EPC查询对象
 *
 * @author gancao 2017/11/17 17:32
 * @see [相关类/方法]
 * @since [版本号]
 */
public class EPCQueryParam implements Serializable {

	private static final long serialVersionUID = -3259768964656443744L;

	//vin码
	private String vin;
	//车型id
	private Integer carModelId;
	//零件名称集合
	private List<String> partNameList;
	//零件码集合
	private List<String> partCodeList;
	//品牌名称
	private String brandName;

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public Integer getCarModelId() {
		return carModelId;
	}

	public void setCarModelId(Integer carModelId) {
		this.carModelId = carModelId;
	}

	public List<String> getPartNameList() {
		return partNameList;
	}

	public void setPartNameList(List<String> partNameList) {
		this.partNameList = partNameList;
	}

	public List<String> getPartCodeList() {
		return partCodeList;
	}

	public void setPartCodeList(List<String> partCodeList) {
		this.partCodeList = partCodeList;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
}

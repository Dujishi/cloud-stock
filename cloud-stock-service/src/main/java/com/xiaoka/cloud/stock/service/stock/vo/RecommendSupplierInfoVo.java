package com.xiaoka.cloud.stock.service.stock.vo;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/11/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RecommendSupplierInfoVo implements Serializable {
	private static final long serialVersionUID = 6800936660347320244L;

	/**
	 * 零件号
	 */
	private String  partCode;
	/**
	 * 零件名称
	 */
	private String  partName;
	/**
	 * 厂地
	 */
	private String  produceArea;
	/**
	 * 供应商id
	 */
	private Integer supplierId;
	/**
	 * 供应商名称
	 */
	private String  supplierName;
	/**
	 * 手机号码
	 */
	private String  phone;
	/**
	 * 供应商省份
	 */
	private String  supplierProv;
	/**
	 * 供应商城市
	 */
	private String  supplierCity;

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getProduceArea() {
		return produceArea;
	}

	public void setProduceArea(String produceArea) {
		this.produceArea = produceArea;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSupplierProv() {
		return supplierProv;
	}

	public void setSupplierProv(String supplierProv) {
		this.supplierProv = supplierProv;
	}

	public String getSupplierCity() {
		return supplierCity;
	}

	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
}

package com.xiaoka.cloud.stock.client.business.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2018/1/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public class StorageDto implements Serializable {
	private static final long serialVersionUID = -8441723841484342571L;

	/**
	 * 商品编码-外部配件id
	 */
	private String cPartId;
	/**
	 * 零件码
	 */
	private String partCode;
	/**
	 * 编码一
	 */
	private String code1;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 适用车型
	 */
	private String carModel;
	/**
	 * 产地
	 */
	private String originPlace;
	/**
	 * 规格
	 */
	private String standard;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 仓库
	 */
	private String depot;
	/**
	 * 实际库存
	 */
	private String balanceCount;
	/**
	 * 运营价、成本价
	 */
	private String costPrice;
	/**
	 * 提供给同行价格
	 */
	private String tradePrice;
	/**
	 * 提供给保险公司价
	 */
	private String insurerPrice;
	/**
	 * 提供给修理厂价格
	 */
	private String repairPrice;
	/**
	 * 维修站价/4S店价格
	 */
	private String maintainPrice;
	/**
	 * 库存标识
	 */
	private Integer flag;

	public String getcPartId() {
		return cPartId;
	}

	public void setcPartId(String cPartId) {
		this.cPartId = cPartId;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getCode1() {
		return code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getStandard() {
		return standard;
	}

	public void setStandard(String standard) {
		this.standard = standard;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getBalanceCount() {
		return balanceCount;
	}

	public void setBalanceCount(String balanceCount) {
		this.balanceCount = balanceCount;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(String tradePrice) {
		this.tradePrice = tradePrice;
	}

	public String getInsurerPrice() {
		return insurerPrice;
	}

	public void setInsurerPrice(String insurerPrice) {
		this.insurerPrice = insurerPrice;
	}

	public String getRepairPrice() {
		return repairPrice;
	}

	public void setRepairPrice(String repairPrice) {
		this.repairPrice = repairPrice;
	}

	public String getMaintainPrice() {
		return maintainPrice;
	}

	public void setMaintainPrice(String maintainPrice) {
		this.maintainPrice = maintainPrice;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}

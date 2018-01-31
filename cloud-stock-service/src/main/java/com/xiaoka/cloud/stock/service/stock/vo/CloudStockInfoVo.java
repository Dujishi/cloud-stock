package com.xiaoka.cloud.stock.service.stock.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhouze
 * @date 2017/11/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CloudStockInfoVo implements Serializable {
	private static final long serialVersionUID = -8523421309526675496L;

	/**
	 * 外部系统配件id
	 */
	private String     cPartId;
	/**
	 * 仓库名称
	 */
	private String     depot;
	/**
	 * 配件Id
	 */
	private Integer    partId;
	/**
	 * oe码
	 */
	private String     partCode;
	/**
	 * 配件名称
	 */
	private String     partName;
	/**
	 * 产地
	 */
	private String     produceArea;
	/**
	 * 仓库名称
	 */
	private String     partDepot;
	/**
	 * 库存余量
	 */
	private BigDecimal partStock;
	/**
	 * 成本价
	 */
	private BigDecimal costPrice;
	/**
	 * 同行价
	 */
	private BigDecimal tradePrice;
	/**
	 * 修理厂价
	 */
	private BigDecimal repairPrice;
	/**
	 * 保险公司价
	 */
	private BigDecimal insurerPrice;
	/**
	 * 维修站价
	 */
	private BigDecimal maintainPrice;

	public String getDepot() {
		return depot;
	}

	public void setDepot(String depot) {
		this.depot = depot;
	}

	public String getcPartId() {
		return cPartId;
	}

	public void setcPartId(String cPartId) {
		this.cPartId = cPartId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

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

	public String getPartDepot() {
		return partDepot;
	}

	public void setPartDepot(String partDepot) {
		this.partDepot = partDepot;
	}

	public BigDecimal getPartStock() {
		return partStock;
	}

	public void setPartStock(BigDecimal partStock) {
		this.partStock = partStock;
	}

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public BigDecimal getRepairPrice() {
		return repairPrice;
	}

	public void setRepairPrice(BigDecimal repairPrice) {
		this.repairPrice = repairPrice;
	}

	public BigDecimal getInsurerPrice() {
		return insurerPrice;
	}

	public void setInsurerPrice(BigDecimal insurerPrice) {
		this.insurerPrice = insurerPrice;
	}

	public BigDecimal getMaintainPrice() {
		return maintainPrice;
	}

	public void setMaintainPrice(BigDecimal maintainPrice) {
		this.maintainPrice = maintainPrice;
	}
}

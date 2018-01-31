package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/13
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroPartPriceDto implements Serializable {
	private static final long serialVersionUID = 974881556374789538L;

	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 零件码
	 */
	private String pid;
	/**
	 * 渠道
	 */
	private String channel;
	/**
	 * 工厂类型
	 */
	private String factoryType;
	/**
	 * 成本价
	 */
	private String costPrice;
	/**
	 * 进货价
	 */
	private String eotPrice;
	/**
	 * 参考销售价
	 */
	private String price;
	/**
	 * 原厂件
	 */
	private String partType;
	/**
	 * 厂商
	 */
	private String mill;
	/**
	 * 地区
	 */
	private String location;
	/**
	 * 库存余量
	 */
	private String amount;
	/**
	 * 原厂源
	 */
	private String origin;
	/**
	 * 服务商
	 */
	private String supplier;
	/**
	 * 备注
	 */
	private String remark;

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getFactoryType() {
		return factoryType;
	}

	public void setFactoryType(String factoryType) {
		this.factoryType = factoryType;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getEotPrice() {
		return eotPrice;
	}

	public void setEotPrice(String eotPrice) {
		this.eotPrice = eotPrice;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getMill() {
		return mill;
	}

	public void setMill(String mill) {
		this.mill = mill;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/19 14:14
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroPartPriceDetailResp implements Serializable {
	private static final long serialVersionUID = 3579207073023698473L;

	private String origin;
	@JsonProperty("parttype")
	private String partType;
	private String pid;
	private String prices;
	private String mill;
	private String remark;
	@JsonProperty("eot_price")
	private String eotPrice;
	@JsonProperty("factory_type")
	private int factoryType;
	private String amount;
	private String location;
	private String supplier;
	@JsonProperty("cost_price")
	private String costPrice;
	private String channel;

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getPartType() {
		return partType;
	}

	public void setPartType(String partType) {
		this.partType = partType;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public String getMill() {
		return mill;
	}

	public void setMill(String mill) {
		this.mill = mill;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEotPrice() {
		return eotPrice;
	}

	public void setEotPrice(String eotPrice) {
		this.eotPrice = eotPrice;
	}

	public int getFactoryType() {
		return factoryType;
	}

	public void setFactoryType(int factoryType) {
		this.factoryType = factoryType;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getSupplier() {
		return supplier;
	}

	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(String costPrice) {
		this.costPrice = costPrice;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}

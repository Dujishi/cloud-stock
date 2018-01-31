package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/19 11:06
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartChooseResp implements Serializable {

	private static final long serialVersionUID = 2118154090359306219L;

	private String remark;
	private String modelname;
	private String itid;
	private String pid;
	private String detail;
	@JsonProperty("multi_price")
	private int multiPrice;
	private String label;
	private String prices;
	private int step;
	private String num;
	@JsonProperty("is_filter")
	private int isFilter;
	@JsonProperty("has_inventory")
	private int hasInventory;
	private int isreplace;
	@JsonProperty("real_pid")
	private String realPid;
	private String model;
	private String quantity;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getModelname() {
		return modelname;
	}

	public void setModelname(String modelname) {
		this.modelname = modelname;
	}

	public String getItid() {
		return itid;
	}

	public void setItid(String itid) {
		this.itid = itid;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public int getMultiPrice() {
		return multiPrice;
	}

	public void setMultiPrice(int multiPrice) {
		this.multiPrice = multiPrice;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public int getStep() {
		return step;
	}

	public void setStep(int step) {
		this.step = step;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public int getIsFilter() {
		return isFilter;
	}

	public void setIsFilter(int isFilter) {
		this.isFilter = isFilter;
	}

	public int getHasInventory() {
		return hasInventory;
	}

	public void setHasInventory(int hasInventory) {
		this.hasInventory = hasInventory;
	}

	public int getIsreplace() {
		return isreplace;
	}

	public void setIsreplace(int isreplace) {
		this.isreplace = isreplace;
	}

	public String getRealPid() {
		return realPid;
	}

	public void setRealPid(String realPid) {
		this.realPid = realPid;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
}

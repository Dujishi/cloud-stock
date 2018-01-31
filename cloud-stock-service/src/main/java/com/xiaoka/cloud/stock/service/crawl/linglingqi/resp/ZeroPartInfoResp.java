package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroPartInfoResp implements Serializable {
	private static final long serialVersionUID = -1548248599856506221L;

	private String label;

	private String model;

	private String num;

	private String pid;

	private String prices;

	private String remark;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}

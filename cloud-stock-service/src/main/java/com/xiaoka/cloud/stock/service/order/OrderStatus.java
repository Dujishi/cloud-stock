package com.xiaoka.cloud.stock.service.order;

public enum OrderStatus {

	Prepay(2000, "待支付"),
	Paid(3000, "已支付"),
	ClosedBySys(9000, "已关闭"),
	ClosedBySelfDelete(9001, "已关闭"),
	ClosedByOrderModify(9002, "已关闭");

	private final Integer code;
	private final String descr;

	OrderStatus(Integer code, String descr) {
		this.descr = descr;
		this.code = code;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getDescr() {
		return this.descr;
	}

	public String toString() {
		return "{code=" + this.code + ", descr='" + this.descr + '\'' + '}';
	}
}

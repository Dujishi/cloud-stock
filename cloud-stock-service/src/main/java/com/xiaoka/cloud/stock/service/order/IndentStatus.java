package com.xiaoka.cloud.stock.service.order;

public enum IndentStatus {
	Created(1000, "已创建"),
	Prepay(2000, "待支付"),
	ClosedBySys(9000, "已删除"),
	ClosedBySelfDelete(9001, "已关闭"),
	ClosedByOrderDelete(9002, "已关闭");

	private final Integer code;
	private final String descr;

	IndentStatus(Integer code, String descr) {
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

package com.xiaoka.cloud.stock.service.epc;

public enum EpcSourceEnum {

	ZS("ZS", "trust_rate_0", "正时"),
	AM("AM", "trust_rate_1", "昂美"),
	LLQ("LLQ", "trust_rate_2", "007");

	private final String code;
	private final String column;
	private final String des;

	EpcSourceEnum(String code, String column, String des) {
		this.code = code;
		this.column = column;
		this.des = des;
	}

	public String getCode() {
		return code;
	}

	public String getDes() {
		return des;
	}

	public String getColumn() {
		return column;
	}
}

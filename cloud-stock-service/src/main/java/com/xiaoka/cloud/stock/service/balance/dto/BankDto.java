package com.xiaoka.cloud.stock.service.balance.dto;

import java.io.Serializable;

/**
 * 银行信息
 * Created by Luker on 2016/12/19.
 */
public class BankDto implements Serializable {

	/**
	 * 银行代码: 1001
	 */
	private String bankCode;
	/**
	 * 银行名称: 招商银行
	 */
	private String bankName;
	/**
	 * 缩写: Z
	 */
	private String miniName;

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getMiniName() {
		return miniName;
	}

	public void setMiniName(String miniName) {
		this.miniName = miniName;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"bankCode\":\"").append(bankCode).append('\"');
		sb.append(", \"bankName\":\"").append(bankName).append('\"');
		sb.append(", \"miniName\":\"").append(miniName).append('\"');
		sb.append('}');
		return sb.toString();
	}
}

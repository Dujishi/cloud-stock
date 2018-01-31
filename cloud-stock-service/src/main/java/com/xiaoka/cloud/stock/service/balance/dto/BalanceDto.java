package com.xiaoka.cloud.stock.service.balance.dto;

import java.io.Serializable;

public class BalanceDto implements Serializable{

	private String supplierName;
	private String phone;
	/**
	 * 可提现金额
	 */
	private Double withdrawAmount;
	/**
	 * 未入账金额
	 */
	private Double realSubAmount;
	/**
	 * 银行卡卡号
	 */
	private String bankNo;
	/**
	 * 银行分行 石桥支行
	 */
	private String bankBranch;

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Double getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(Double withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public Double getRealSubAmount() {
		return realSubAmount;
	}

	public void setRealSubAmount(Double realSubAmount) {
		this.realSubAmount = realSubAmount;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getBankBranch() {
		return bankBranch;
	}

	public void setBankBranch(String bankBranch) {
		this.bankBranch = bankBranch;
	}
}
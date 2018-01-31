package com.xiaoka.cloud.stock.service.balance.dto;

import com.xiaoka.stmt.soa.bank.RoleType;

import java.io.Serializable;
import java.math.BigDecimal;

public class ExtractRequestDto implements Serializable{
	/**
	 * 角色id
	 */
	private Integer roleId;

	/**
	 * 角色类型
	 */
	private RoleType roleType;

	/**
	 * 提现金额
	 */
	private BigDecimal withdrawAmount;

	/**
	 * 提现手机号码
	 */
	private String phone;

	/**
	 * 提现验证码
	 */
	private String smsCode;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}
}

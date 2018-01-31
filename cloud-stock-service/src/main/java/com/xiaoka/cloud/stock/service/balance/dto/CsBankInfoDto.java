package com.xiaoka.cloud.stock.service.balance.dto;

import com.xiaoka.stmt.soa.bank.RoleType;

import java.io.Serializable;

/**
 * @author zhangsong.
 * @date 2016/11/16 上午11:09
 */
public class CsBankInfoDto implements Serializable {

	/**
	 * 商家id
	 */
	private String roleId;

	private RoleType roleType;
	/**
	 * 银行类型 1005
	 */
	private String bankType;
	/**
	 * 银行名称 中国农业银行
	 */
	private String bankName;
	/**
	 * 银行卡账户名字 张三
	 */
	private String bankUser;
	/**
	 * 银行卡卡号
	 */
	private String bankNo;
	/**
	 * 银行分行 石桥支行
	 */
	private String bankBranch;
	/**
	 * 分行编码 12
	 */
	private String bankAreaCode;
	/**
	 * 城市名字 杭州
	 */
	private String bankCity;
	/**
	 * 城市编码 571
	 */
	private String bankCityCode;
	/**
	 * 账户类型 1
	 */
	private Integer bankAccType;

	private String phone;

	/**
	 * 请求来源
	 */
	private String source;

	/**
	 * 短信验证码， saveBankInfo 接口 可以不传，修改校验
	 * saveBankInfoWithSmsCheck 接口必传,修改校验在结算这边校验
	 */
	private String smsCode;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankUser() {
		return bankUser;
	}

	public void setBankUser(String bankUser) {
		this.bankUser = bankUser;
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

	public String getBankAreaCode() {
		return bankAreaCode;
	}

	public void setBankAreaCode(String bankAreaCode) {
		this.bankAreaCode = bankAreaCode;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getBankCityCode() {
		return bankCityCode;
	}

	public void setBankCityCode(String bankCityCode) {
		this.bankCityCode = bankCityCode;
	}

	public Integer getBankAccType() {
		return bankAccType;
	}

	public void setBankAccType(Integer bankAccType) {
		this.bankAccType = bankAccType;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"roleId\":\"").append(roleId).append('\"');
		sb.append(", \"roleType\":\"").append(roleType).append('\"');
		sb.append(", \"bankType\":\"").append(bankType).append('\"');
		sb.append(", \"bankName\":\"").append(bankName).append('\"');
		sb.append(", \"bankUser\":\"").append(bankUser).append('\"');
		sb.append(", \"bankNo\":\"").append(bankNo).append('\"');
		sb.append(", \"bankBranch\":\"").append(bankBranch).append('\"');
		sb.append(", \"bankAreaCode\":\"").append(bankAreaCode).append('\"');
		sb.append(", \"bankCity\":\"").append(bankCity).append('\"');
		sb.append(", \"bankCityCode\":\"").append(bankCityCode).append('\"');
		sb.append(", \"bankAccType\":").append(bankAccType);
		sb.append(", \"phone\":\"").append(phone).append('\"');
		sb.append(", \"source\":\"").append(source).append('\"');
		sb.append(", \"smsCode\":\"").append(smsCode).append('\"');
		sb.append('}');
		return sb.toString();
	}
}

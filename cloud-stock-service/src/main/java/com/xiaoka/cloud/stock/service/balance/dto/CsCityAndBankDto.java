package com.xiaoka.cloud.stock.service.balance.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 城市列表和银行列表汇总
 * Created by Luker on 2016/12/19.
 */
public class CsCityAndBankDto implements Serializable {

	/**
	 * 城市列表
	 */
	private List<CityDto> cityDtos;
	/**
	 * 银行列表
	 */
	private List<BankDto> bankDtos;

	public List<CityDto> getCityDtos() {
		return cityDtos;
	}

	public void setCityDtos(List<CityDto> cityDtos) {
		this.cityDtos = cityDtos;
	}

	public List<BankDto> getBankDtos() {
		return bankDtos;
	}

	public void setBankDtos(List<BankDto> bankDtos) {
		this.bankDtos = bankDtos;
	}

	@Override public String toString() {
		final StringBuilder sb = new StringBuilder("{");
		sb.append("  \"cityDtos\":").append(cityDtos);
		sb.append(", \"bankDtos\":").append(bankDtos);
		sb.append('}');
		return sb.toString();
	}
}

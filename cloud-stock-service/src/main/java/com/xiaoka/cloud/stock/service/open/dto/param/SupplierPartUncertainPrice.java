package com.xiaoka.cloud.stock.service.open.dto.param;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author zhouze
 * @date 2017/11/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SupplierPartUncertainPrice implements Serializable {
	private static final long serialVersionUID = 8418401278238290352L;

	/**
	 * 价格属性编码
	 */
	private String     priceCode;
	/**
	 * 价格属性值
	 */
	private BigDecimal priceValue;

	public String getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}

	public BigDecimal getPriceValue() {
		return priceValue;
	}

	public void setPriceValue(BigDecimal priceValue) {
		this.priceValue = priceValue;
	}
}

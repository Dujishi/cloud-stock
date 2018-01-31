package com.xiaoka.cloud.stock.service.stock.constant;

/**
 * @author zhouze
 * @date 2017/11/20
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum StockFlagEnum {
	/**
	 * 存在库存
	 */
	EXIST(1),
	/**
	 * 库存售罄
	 */
	OUT_OF_STOCK(2),
	/**
	 * 库存暂无
	 */
	NONE(3);


	private Integer code;

	public Integer getCode() {
		return code;
	}

	StockFlagEnum(Integer code) {
		this.code = code;
	}
}

package com.xiaoka.cloud.stock.core.supplier.constant;

/**
 * 搜索历史记录
 *
 * @author gancao 2017/11/22 16:37
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum SearchTypeEnum {

	VIN_CODE(1, "vin码查询"),
	OE_CODE(2, "oe码查询");

	private Integer type;
	private String desc;

	SearchTypeEnum(Integer type, String desc) {
		this.type = type;
		this.desc = desc;
	}

	public Integer getType() {
		return type;
	}

	public String getDesc() {
		return desc;
	}
}

package com.xiaoka.cloud.stock.service.epc.constant;

/**
 * Do something
 *
 * @author gancao 2017/12/31 21:49
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum CategoryTypeEnum {
	原厂分类(0),
	正时分类(1),
	零零汽分类(2);

	private Integer type;

	CategoryTypeEnum(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}
}

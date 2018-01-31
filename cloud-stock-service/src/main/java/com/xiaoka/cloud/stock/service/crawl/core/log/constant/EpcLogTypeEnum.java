package com.xiaoka.cloud.stock.service.crawl.core.log.constant;


/**
 * Do something
 *
 * @author gancao 2018/1/12 14:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum  EpcLogTypeEnum {
	正时("super_epc"),
	零零汽("seven_epc"),
	;

	private String type;

	EpcLogTypeEnum(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}
}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.constant;

/**
 * @author zhouze
 * @date 2017/12/22
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum VinPropertyGroupEnum {

	BRAND("品牌"),
	CAR_MODEL("车型"),
	YEAR("年型"),
	ENGINE("发动机"),
	GEAR_BOX("变速箱"),
	MARKET("市场");

	VinPropertyGroupEnum(String property) {
		this.property = property;
	}

	private String property;

	public String getProperty() {
		return property;
	}

}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.manger.constant;

/**
 * Do something
 *
 * @author gancao 2018/1/10 13:51
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum  ManagerTypeEnum {
	有效帐号(1),
	待检测帐号(2);

	private Integer type;

	ManagerTypeEnum(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return type;
	}

}

package com.xiaoka.cloud.stock.service.mqmessage.constant;

import java.util.Objects;

/**
 * EPC消息类型枚举
 * <pre>
 *     6的链路中包含789
 * </pre>
 *
 * @author gancao 2017/11/23 19:46
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum EPCMessageTypeEnum {
	VIN码同步通知(1, "/cloud-stock/model/vin"),
	车型同步通知(2, "/cloud-stock/model/model"),
	车型分类同步通知(3, "/cloud-stock/model/category"),
	车型分总成图片通知(4, "/cloud-stock/model/pic"),
	车型图号下的零件通知(5, "/cloud-stock/model/part"),
	车型零件通知(6, "/cloud-stock/model/part"),
	零件适用车型通知(7, "/cloud-stock/part/model"),
	零件4S店价格通知(8, "/cloud-stock/part/price"),
	零件替换件通知(9, "/cloud-stock/part/replace");

	//通知类型
	private Integer type;
	//分布式加锁的路径
	private String lockUrl;

	EPCMessageTypeEnum(Integer type, String lockUrl) {
		this.type = type;
		this.lockUrl = lockUrl;
	}

	public Integer getType() {
		return type;
	}

	public String getLockUrl() {
		return lockUrl;
	}

	public static EPCMessageTypeEnum getEPCMessageTypeEnumByType(Integer type) {
		if (Objects.nonNull(type)) {
			for (EPCMessageTypeEnum typeEnum : EPCMessageTypeEnum.values()) {
				if (Objects.equals(typeEnum.getType(), type)) {
					return typeEnum;
				}
			}
		}
		return null;
	}
}

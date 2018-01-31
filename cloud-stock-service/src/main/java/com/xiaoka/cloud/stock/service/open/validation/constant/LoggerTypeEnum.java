package com.xiaoka.cloud.stock.service.open.validation.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum LoggerTypeEnum {

	/**
	 * 空
	 */
	EMPTY(1) {
		@Override
		public String doLog(String fieldName, Integer flag) {
			if (null == flag) {
				LOGGER.warn("检查到参数为空,fieldName:{}", fieldName);
				return String.format("检查到参数为空,参数字段名：%s", fieldName);
			}
			LOGGER.warn("检查到参数为空,fieldName:{},标记值为:{}", fieldName, flag);
			return String.format("检查到参数为空,参数字段名：%s,标记值为:%s", fieldName, flag);
		}
	},
	/**
	 * 长度超出限制
	 */
	LENGTH_OUT_OF_BOUND(2) {
		@Override
		public String doLog(String fieldName, Integer flag) {
			if (null == flag) {
				LOGGER.warn("检查到参数长度超过限制,fieldName:{}", fieldName);
				return String.format("检查到参数超过限制,参数字段名：%s", fieldName);
			}
			LOGGER.warn("检查到参数超过限制,fieldName:{},标记值为:{}", fieldName, flag);
			return String.format("检查到参数超过限制,参数字段名：%s,标记值为:%s", fieldName, flag);
		}
	};

	private static final Logger LOGGER = LoggerFactory.getLogger(LoggerTypeEnum.class);

	private int type;

	public int getType() {
		return type;
	}

	LoggerTypeEnum(int type) {
		this.type = type;
	}

	/**
	 * 是否相等
	 *
	 * @param typeEnum
	 * @return
	 */
	public boolean equals(LoggerTypeEnum typeEnum) {
		return type == typeEnum.getType();
	}

	/**
	 * log special fieldName
	 *
	 * @param fieldName 参数名
	 */
	public String doLog(String fieldName) {
		return doLog(fieldName, null);
	}

	/**
	 * log special fieldName and flag
	 *
	 * @param fieldName 参数名
	 * @param flag      int类型标记值 - 【可标记数组索引等，由客户端决定】
	 */
	public abstract String doLog(String fieldName, Integer flag);
}

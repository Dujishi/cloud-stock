package com.xiaoka.cloud.stock.client.net.contants;

/**
 * @author zhouze
 * @date 2018/1/11
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum RequestTypeEnum {

	/**
	 * GET
	 */
	GET {
		@Override
		public String toString() {
			return "GET";
		}
	},
	/**
	 * POST
	 */
	POST {
		@Override
		public String toString() {
			return "POST";
		}
	}
}

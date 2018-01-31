package com.xiaoka.constants;

/**
 * @author zhouze
 * @date 2017/12/25
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum RequestTypeEnum {
	GET {
		@Override
		public String toString() {
			return "GET";
		}
	},
	POST {
		@Override
		public String toString() {
			return "POST";
		}
	};


}

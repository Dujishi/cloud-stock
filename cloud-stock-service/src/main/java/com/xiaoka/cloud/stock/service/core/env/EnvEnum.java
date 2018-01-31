package com.xiaoka.cloud.stock.service.core.env;

/**
 * Do something
 *
 * @author gancao 2018/1/15 18:15
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum EnvEnum {

	INT("int"),
	PRE("pre"),
	PROD("prod");

	private String env;

	EnvEnum(String env) {
		this.env = env;
	}

	public String getEnv() {
		return env;
	}
}

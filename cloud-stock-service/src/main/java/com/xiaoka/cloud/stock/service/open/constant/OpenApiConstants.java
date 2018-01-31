package com.xiaoka.cloud.stock.service.open.constant;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/11/7
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface OpenApiConstants {

	/**
	 * 接口时效性，默认60s
	 */
	Long VALID_TIME_INTERVAL = 60L;

	/**
	 * 编码
	 */
	String CHARSET = "UTF-8";

	/**
	 * AppId参数key
	 */
	String PARAM_APPID = "appId";

	/**
	 * 时间戳参数key
	 */
	String PARAM_VT = "vt";

	/**
	 * 签名参数key
	 */
	String PARAM_SIGN = "sign";

	/**
	 * 参数数组长度最大为500
	 */
	Long MAX_ARRAY_SIZE = 500L;

	/**
	 * 最大字符串长度
	 */
	Integer MAX_STRING_LENGTH_200 = 200;
	Integer MAX_STRING_LENGTH_100 = 100;
	Integer MAX_STRING_LENGTH_50  = 50;
	Integer MAX_STRING_LENGTH_10  = 10;
	/**
	 * 最大数组长度
	 */
	Integer MAX_ARRAY_LENGTH      = 500;
}

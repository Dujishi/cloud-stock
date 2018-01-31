package com.xiaoka.cloud.stock.client.app.bridge;

import com.xiaoka.freework.container.spring.config.ConfigCenter;
import com.xiaoka.freework.help.api.ApiResult;
import com.xiaoka.freework.utils.json.Jackson;

public class JSBridge {

	/**
	 * 调用 Spring 标准服务通道
	 * 
	 * @param service
	 *            定位服务，格式：{service}.{method}
	 * @param params
	 *            以JSON数组格式将多个参数传递进来
	 */
	public String call(String service, String params) {
		ApiResult<?> result = JsonServiceCaller.caller(service, params).call();
		return wrapperResult(result);
	}

	/**
	 * 根据指定key获取本机环境下的所有配置项信息
	 * 
	 * @param key
	 *            配置项
	 */
	public String config(String key) {
		return wrapperResult(ConfigCenter.global().getString(key));
	}

	// 对结果进行JSON包装
	private String wrapperResult(Object result) {
		if (!(result instanceof ApiResult)) {
			result = new ApiResult<Object>(result);
		}
		return Jackson.base().writeValueAsString(result);
	}
}

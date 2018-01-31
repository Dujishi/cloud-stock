package com.xiaoka.cloud.stock.service.open.utils;

import com.xiaoka.cloud.stock.service.open.dto.result.OpenApiResult;
import com.xiaoka.freework.utils.json.Jackson;

/**
 * 对外接口专用
 *
 * @author zhouze
 * @date 2017/11/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public class OpenApiWrapper {

	/**
	 * 返回成功
	 *
	 * @param code
	 * @param message
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> String success(String code, String message, final T obj) {
		OpenApiResult<T> apiResult = new OpenApiResult<>();
		apiResult.setData(obj);
		apiResult.setSuccess(true);
		apiResult.setMessage(message);
		apiResult.setErrCode(code);
		return Jackson.base().writeValueAsString(apiResult);
	}

	public static String success(String code, String message) {
		return success(code, message, null);
	}

	/**
	 * 返回失败
	 *
	 * @param code
	 * @param message
	 * @param obj
	 * @param <T>
	 * @return
	 */
	public static <T> String fail(String code, String message, final T obj) {
		OpenApiResult<T> apiResult = new OpenApiResult<>();
		apiResult.setData(obj);
		apiResult.setSuccess(false);
		apiResult.setMessage(message);
		apiResult.setErrCode(code);
		return Jackson.base().writeValueAsString(apiResult);
	}

	public static String fail(String code, String message) {
		return success(code, message, null);
	}

	private OpenApiWrapper() {
	}
}

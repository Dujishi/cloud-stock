package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.service.core.util.dto.ApiResult;
import com.xiaoka.freework.utils.json.Jackson;

import java.util.List;
import java.util.Objects;

public class TestUtil {

	public static String success(Object data) {
		return success(true, null, data);
	}

	/**
	 * 页面成功返回数据
	 *
	 * @param success 成功
	 * @param message 消息
	 * @param obj     传输数据
	 * @param <T>     传输数据类型
	 * @return
	 */
	private static <T> String success(Boolean success, String message, final T obj) {
		ApiResult<T> apiResult = new ApiResult<>();
		apiResult.setData(obj);
		apiResult.setSuccess(success);
		apiResult.setMessage(message);
		if (Objects.nonNull(obj) && obj instanceof List){//返回数据是集合则将size加上
			apiResult.setSize(((List) obj).size());
		}
		return Jackson.mobile().writeValueAsString(apiResult);
	}
}

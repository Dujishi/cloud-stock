package com.xiaoka.cloud.stock.service.open.constant;

import com.xiaoka.cloud.stock.service.open.utils.OpenApiWrapper;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouze
 * @date 2017/11/7
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum OpenApiReturnCodeEnum {

	/**
	 * 成功
	 */
	SUCCESS("200", "成功") {
		@Override
		public String asResponse() {
			return OpenApiWrapper.success(getCode(), getMessage());
		}
	},
	/**
	 * 失败
	 */
	FAIL_EXCEPTION("1", "服务器繁忙"),
	FAIL_OTHER("1000", "非法请求"),
	FAIL_PARAM_MISS("1001", "缺少必要的参数"),
	FAIL_PARAM_ILLEGAL("1002", "不合法的参数"),
	FAIL_SIGN("1003", "接口验签未通过"),
	FAIL_VALID_TIME("1004", "接口时效性验证未通过"),
	FAIL_PARAM_LENGTH_OUT_RANGE("1010", "参数长度超过限制"),
	FAIL_ILLEGALITY_APP_ID("1100", "非法的appId");

	private String code;

	private String message;

	OpenApiReturnCodeEnum(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	/**
	 * 返回异常
	 *
	 * @return
	 */
	public ApiException ifApiException() {
		return ifApiException(null);
	}

	/**
	 * 返回异常
	 *
	 * @param additional 附加信息,用于返回说明附加一些详细信息
	 * @return
	 */
	public ApiException ifApiException(String additional) {
		if (StringUtils.isBlank(additional)) {
			return new ApiException(code, message);
		}
		return new ApiException(code, message.concat(".[附加信息:").concat(additional).concat("]"));
	}

	/**
	 * 返回接口信息
	 *
	 * @return
	 */
	public String asResponse() {
		return OpenApiWrapper.fail(getCode(), getMessage());
	}

}

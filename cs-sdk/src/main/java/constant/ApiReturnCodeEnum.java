package constant;

/**
 * 返回code 说明
 *
 * @author zhouze
 * @date 2017/11/14
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum ApiReturnCodeEnum {

	/**
	 * 成功
	 */
	SUCCESS("200", "成功"),
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

	private String errCode;

	private String message;

	ApiReturnCodeEnum(String errCode, String message) {
		this.errCode = errCode;
		this.message = message;
	}

	public String getErrCode() {
		return errCode;
	}

	public String getMessage() {
		return message;
	}
}

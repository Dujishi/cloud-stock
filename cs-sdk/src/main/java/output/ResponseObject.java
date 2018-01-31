package output;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/11/11
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ResponseObject implements Serializable {

	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 消息码
	 */
	private String  errCode;
	/**
	 * 消息描述
	 */
	private String  message;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getErrCode() {
		return errCode;
	}

	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

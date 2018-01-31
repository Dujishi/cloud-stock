package com.xiaoka.cloud.stock.client.app.bridge;

import com.xiaoka.freework.help.api.ErrorMessage;

public enum JSONServiceError implements ErrorMessage {
	PARAM_ERR("输入参数异常：%s"),
	EXEC_ERR("执行出错：%s"),
	;

	private String message;
	
	private JSONServiceError(String message){
		this.message = message;
	}
	
	@Override
	public String getErrCode() {
		return this.toString();
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	
}

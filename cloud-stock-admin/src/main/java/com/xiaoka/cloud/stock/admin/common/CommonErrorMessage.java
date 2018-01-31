package com.xiaoka.cloud.stock.admin.common;


public enum CommonErrorMessage implements DefaultErrorMessage {
	PARAM_ERR("输入参数有误！"),
	;
	
	private String message;
	
	private CommonErrorMessage(String message){
		this.message = message;
	}
	
	@Override
	public String getMessage() {
		return this.message;
	}

	
}

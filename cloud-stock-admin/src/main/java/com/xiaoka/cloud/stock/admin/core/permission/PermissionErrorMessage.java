package com.xiaoka.cloud.stock.admin.core.permission;

import com.xiaoka.cloud.stock.admin.common.DefaultErrorMessage;

public enum PermissionErrorMessage implements DefaultErrorMessage {
	SYSTEM_DENY ("您无权限进入当前系统！"),
	USER_NOT_LOGIN ("当前用户信息异常，请重新登录！"),
	NOT_PERMISSION ("您无权进行当前操作！"),
	;
	
	private String message;
	
	private PermissionErrorMessage(String message){
		this.message = message;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	
}

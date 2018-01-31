package com.xiaoka.cloud.stock.admin.common;

import org.apache.commons.lang3.StringUtils;

import com.xiaoka.freework.help.api.ErrorMessage;

public interface DefaultErrorMessage extends ErrorMessage {
	String SYS_PREFIX = "CS_";
	
	@Override
	default String getErrCode() {
		return StringUtils.join(SYS_PREFIX, this.toString());
	}


	
}

package com.xiaoka.cloud.stock.service.open.validation.constraint;

import com.xiaoka.cloud.stock.service.open.validation.constant.LoggerTypeEnum;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CheckLegalUtil {

	private CheckLegalUtil() {
	}

	public static void stringLegal(String param, int length, LoggerCall loggerCall) {
		if (StringUtils.isBlank(param)) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.EMPTY);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_MISS.ifApiException(additionalInfo);
		}
		if (param.length() > length) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.LENGTH_OUT_OF_BOUND);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_ILLEGAL.ifApiException(additionalInfo);
		}
	}

	public static void stringLengthLegal(String param, int length, LoggerCall loggerCall) {
		if (null != param && param.length() > length) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.LENGTH_OUT_OF_BOUND);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_ILLEGAL.ifApiException(additionalInfo);
		}
	}

	public static void listLegal(List list, int size, LoggerCall loggerCall) {
		if (CollectionUtils.isEmpty(list)) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.EMPTY);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_MISS.ifApiException(additionalInfo);
		}
		if (list.size() > size) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.LENGTH_OUT_OF_BOUND);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_LENGTH_OUT_RANGE.ifApiException(additionalInfo);
		}
	}

	public static void listSizeLegal(List list, int size, LoggerCall loggerCall) {
		if (list.size() > size) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.LENGTH_OUT_OF_BOUND);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_LENGTH_OUT_RANGE.ifApiException(additionalInfo);
		}
	}

	public static void bigDecimalLegal(BigDecimal number, LoggerCall loggerCall) {
		if (null == number) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.EMPTY);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_MISS.ifApiException(additionalInfo);
		}
	}

	public static void integerLegal(Integer number, LoggerCall loggerCall) {
		if (null == number) {
			String additionalInfo = loggerCall.log(LoggerTypeEnum.EMPTY);
			throw OpenApiReturnCodeEnum.FAIL_PARAM_MISS.ifApiException(additionalInfo);
		}
	}

}

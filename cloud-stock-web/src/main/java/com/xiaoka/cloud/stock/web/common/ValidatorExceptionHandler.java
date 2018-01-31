package com.xiaoka.cloud.stock.web.common;

import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 参数校验异常处理
 *
 * @author gancao 2017/11/15 15:24
 * @see [相关类/方法]
 * @since [版本号]
 */
@ControllerAdvice
public class ValidatorExceptionHandler {

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public @ResponseBody String handleMethodArgumentNotValidException(MethodArgumentNotValidException error) {
		FieldError fieldError = error.getBindingResult().getFieldError();
		return ApiResultWrapper.fail(CloudStockErrorCode.CLOUD_STOCK_INVALID, fieldError.getDefaultMessage());
	}
}

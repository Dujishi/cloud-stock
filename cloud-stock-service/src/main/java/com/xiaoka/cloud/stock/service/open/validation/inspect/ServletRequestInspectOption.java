package com.xiaoka.cloud.stock.service.open.validation.inspect;

import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouze
 * @date 2017/11/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@Component
public class ServletRequestInspectOption extends ObjectInspectState {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringInspectOption.class);

	@Override
	void checkObjectParameter(Object object) {
		if (object instanceof HttpServletRequest) {
			//nothing  因为在签名拦截器已经做了校验，这里不再赘写
		} else {
			LOGGER.error("不支持将该类型转成HttpServletRequest，fail :{}", object.getClass().getTypeName());
			throw OpenApiReturnCodeEnum.FAIL_EXCEPTION.ifApiException();
		}
	}
}

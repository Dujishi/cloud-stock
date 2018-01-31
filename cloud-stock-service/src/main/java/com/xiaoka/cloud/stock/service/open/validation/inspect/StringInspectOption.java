package com.xiaoka.cloud.stock.service.open.validation.inspect;

import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
@Component
public class StringInspectOption extends ObjectInspectState {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringInspectOption.class);

	@Override
	void checkObjectParameter(Object object) {
		if (object instanceof String) {
			if (StringUtils.isBlank((String) object)) {
				LOGGER.error("object为空，fail :{}", object);
				throw OpenApiReturnCodeEnum.FAIL_PARAM_MISS.ifApiException();
			}
		} else {
			LOGGER.error("不支持将该类型转成String，fail :{}", object.getClass().getTypeName());
			throw OpenApiReturnCodeEnum.FAIL_EXCEPTION.ifApiException();
		}
	}
}

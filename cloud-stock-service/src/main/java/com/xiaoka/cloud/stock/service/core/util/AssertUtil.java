package com.xiaoka.cloud.stock.service.core.util;

import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.StringUtils;

/**
 * Do something
 *
 * @author gancao 2017/11/14 11:32
 * @see [相关类/方法]
 * @since [版本号]
 */
public class AssertUtil {

	public static void assertNotNullWithApiException(Object obj, String msg) {
		if(obj == null || (obj instanceof String && StringUtils.isBlank(obj.toString()))) {
			throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, msg);
		}
	}

	public static void assertNotNullWithApiException(Object obj, String code, String msg) {
		if(obj == null || (obj instanceof String && StringUtils.isBlank(obj.toString()))) {
			throw new ApiException(code, msg);
		}
	}

	public static void assertTrueWithApiException(boolean condition, String msg) {
		if(!condition) {
			throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, msg);
		}
	}

	public static void assertBrandAuthWithApiException(boolean condition, String msg) {
		if(!condition) {
			throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_BRAND_FORBID, msg);
		}
	}
}

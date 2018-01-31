package com.xiaoka.cloud.stock.service.core.util;

import org.apache.commons.lang3.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author zhouze
 * @date 2017/11/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RequestUtils {

	/**
	 * 根据参数名获取参数值
	 *
	 * @param request
	 * @param key     参数名
	 * @return
	 */
	public static String getParamVal(HttpServletRequest request, String key) {
		Map<String, String[]> parameterMap = request.getParameterMap();

		String[] paramValArray = parameterMap.get(key);
		return ArrayUtils.isNotEmpty(paramValArray) ? paramValArray[0] : "";
	}

	private RequestUtils() {
	}
}

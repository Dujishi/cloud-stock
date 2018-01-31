package com.xiaoka.cloud.stock.service.core.util;

import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 首页链接跳转
 *
 * @author gancao 2017/11/14 10:47
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RedirectUtil {

	private static final String LOGIN_URL = "/login";
	private static final Logger logger = LoggerFactory.getLogger(RedirectUtil.class);

	public static void toLogin(HttpServletRequest request,HttpServletResponse response,String callBackUrl) {
		PrintWriter writer;
		try {

			writer = response.getWriter();
			String url = LOGIN_URL;
			if(StringUtils.isNoneBlank(callBackUrl) && !"/".equals(callBackUrl)){
				url += "?callback="+callBackUrl;
			}
			writer.print(StringUtils
					.join("<script>top.location.href='", request.getContextPath(), url, "'</script>"));
		} catch (IOException e) {
			e.printStackTrace();
			throw new ApiException(CloudStockErrorCode.USER_NO_LOGIN, "用户未登录");
		}
	}

}

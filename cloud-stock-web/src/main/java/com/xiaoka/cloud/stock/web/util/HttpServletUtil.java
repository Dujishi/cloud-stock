package com.xiaoka.cloud.stock.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/30 16:04
 * @see [相关类/方法]
 * @since [版本号]
 */
public class HttpServletUtil {

	private static final Logger logger = LoggerFactory.getLogger(HttpServletUtil.class);

	public static void logCookieAndResponseHeader(HttpServletRequest request, HttpServletResponse response) {
		logger.info("####################请求的URL地址:{}####################", request.getRequestURI());
		if (Objects.nonNull(request.getCookies())){
			for (Cookie cookie : request.getCookies()) {
				logger.info("cookieName:{},cookieValue:{}", cookie.getName(), cookie.getValue());
			}
		}
		if (Objects.nonNull(response)){
			logger.info("=======cookie获取完毕=======");
			logger.info("------response start------");
			response.getHeaderNames().forEach(name -> logger.info("name:{},value:{}", name, response.getHeader(name)));
			logger.info("------response end------");
		}
		logger.info("####################结束当前打印####################");
	}
}

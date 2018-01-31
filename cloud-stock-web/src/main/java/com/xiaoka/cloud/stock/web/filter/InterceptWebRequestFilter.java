package com.xiaoka.cloud.stock.web.filter;

import com.xiaoka.cloud.stock.web.util.HttpServletUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author gancao
 * @date 2017/11/30
 */
public class InterceptWebRequestFilter implements Filter {
	private static final transient Logger LOGGER = LoggerFactory.getLogger(InterceptWebRequestFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//输出请求的cookie
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		LOGGER.info("!!!!!!进入应用!!!!!!");
		HttpServletUtil.logCookieAndResponseHeader(httpServletRequest, httpServletResponse);
		chain.doFilter(request, response);
		LOGGER.info("!!!!!!出应用!!!!!!");
		HttpServletUtil.logCookieAndResponseHeader(httpServletRequest, httpServletResponse);

	}

	@Override
	public void destroy() {

	}
}

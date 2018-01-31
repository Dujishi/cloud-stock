package com.xiaoka.cloud.stock.service.core.interceptor;

import com.google.common.collect.ImmutableList;
import com.xiaoka.cloud.stock.service.core.annotation.IpCheck;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierService;
import com.xiaoka.freework.container.context.ContainerContext;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 请求IP访问限制
 *
 * @author gancao 2017/11/13 14:56
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RequestIpAccessInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(RequestIpAccessInterceptor.class);
	//不检查ip限制的环境
	private static final List<String> NO_CHECK_ENV = ImmutableList.of("dev", "int");
	//当前环境
	private String env;
	@Resource
	private CloudSupplierService cloudSupplierService;

	@Override
	public void afterPropertiesSet() throws Exception {
		env = ContainerContext.get().getAppEnv();
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (NO_CHECK_ENV.contains(env)) {//测试和开发环境不作ip拦截，因为应用暂时拿不到真实的ip
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		if (noCheckIp(handlerMethod)){//不作IP校验
			return true;
		}
		String ip = getRealRemoteIp(request);
		logger.info("真实的IP地址为:" + ip);
		AssertUtil.assertTrueWithApiException(cloudSupplierService.isValidIp(ip), "您没有访问权限");
		return true;
	}

	private String getRealRemoteIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			int index = ip.indexOf(",");
			if (index != -1) {
				return ip.substring(0, index);
			} else {
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if (StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
			return ip;
		}
		return request.getRemoteAddr();
	}

	/**
	 * 通过注解获取是否需要校验IP
	 *
	 * @param handler
	 * @return
	 */
	private boolean noCheckIp(HandlerMethod handler) {
		IpCheck ipCheck = handler.getMethodAnnotation(IpCheck.class);
		if (null == ipCheck) {
			ipCheck = handler.getBeanType().getAnnotation(IpCheck.class);
		}
		return ipCheck != null && !ipCheck.check();
	}

}

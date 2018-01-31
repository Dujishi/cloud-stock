package com.xiaoka.cloud.stock.web.common;

import com.xiaoka.cloud.stock.service.core.annotation.SessionPermission;
import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.action.WildcardController;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import com.xiaoka.freework.container.context.ContainerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashSet;
import java.util.Set;

/**
 * 用户拦截器
 *
 * @author gancao 2017/11/13 14:12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SessionAuthInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private static final Logger logger = LoggerFactory.getLogger(SessionAuthInterceptor.class);
	private Set<String> whiteList;
	private String      env;

	@Override
	public void afterPropertiesSet() throws Exception {
		env = ContainerContext.get().getAppEnv();
		whiteList = new HashSet<>();
		whiteList.add("/");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {// 可能处理的是静态资源。无需登录检测
			return true;
		}

		HandlerMethod handlerMethod = (HandlerMethod) handler;
		Object controller = handlerMethod.getBean();
		if (controller instanceof WildcardController) {//页面请求不处理
			logger.info("页面请求返回");
			return true;
		}

		if (noSessionAuth(handlerMethod)) {
			return true;
		}
		CloudSupplierUserDto userDto = SessionUtil.getUser(request);
		AssertUtil.assertNotNullWithApiException(userDto, CloudStockErrorCode.USER_NO_LOGIN, "用户未登录");
		return true;
	}

	/**
	 * 不进行Session验证
	 *
	 * @param handler
	 * @return
	 */
	private boolean noSessionAuth(HandlerMethod handler) {
		//跳过session校验
		SessionPermission sessionPermission = handler.getMethodAnnotation(SessionPermission.class);// 从方法中获取
		if (sessionPermission == null) {// 从类定义中获取
			sessionPermission = handler.getBeanType().getAnnotation(SessionPermission.class);
		}
		//session不校验时
		return sessionPermission != null && !sessionPermission.check();
	}
}

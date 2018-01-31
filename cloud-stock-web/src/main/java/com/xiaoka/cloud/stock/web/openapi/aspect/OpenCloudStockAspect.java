package com.xiaoka.cloud.stock.web.openapi.aspect;

import com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam;
import com.xiaoka.cloud.stock.service.open.validation.inspect.ServletRequestInspectOption;
import com.xiaoka.cloud.stock.service.open.validation.inspect.StringInspectOption;
import com.xiaoka.cloud.stock.service.open.validation.inspect.SupplierSyncInspectOption;
import com.xiaoka.cloud.stock.service.open.validation.inspect.ValidationContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouze
 * @date 2017/11/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@Aspect
@Component
public class OpenCloudStockAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(OpenCloudStockAspect.class);

	@Resource
	SupplierSyncInspectOption   supplierSyncInspectOption;
	@Resource
	StringInspectOption         stringInspectOption;
	@Resource
	ServletRequestInspectOption servletRequestInspectOption;

	@Before("defineValidJoinPoint()")
	public void beforeValidParamAdvice(JoinPoint joinPoint) {
		Object[] objects = joinPoint.getArgs();
		LOGGER.info("开始进行参数校验");
		for (Object object : objects) {
			if (object instanceof SupplierSyncParam) {
				new ValidationContext(supplierSyncInspectOption)
						.handle(object);
			} else if (object instanceof String) {
				new ValidationContext(stringInspectOption)
						.handle(object);
			} else if (object instanceof HttpServletRequest) {
				new ValidationContext(servletRequestInspectOption)
						.handle(object);
			}
		}
		LOGGER.info("参数校验完成");
	}

	@Pointcut("execution(public * com.xiaoka.cloud.stock.web.openapi.*Controller.*(..))")
	public void defineValidJoinPoint() {
	}

}

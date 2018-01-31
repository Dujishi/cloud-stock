package com.xiaoka.cloud.stock.service.open.core;

import com.xiaoka.freework.utils.json.Jackson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouze
 * @date 2017/11/18
 * @see [相关类/方法]
 * @since [版本号]
 */
@Aspect
@Component
public class StockOpenApiAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockOpenApiAspect.class);


	@Around("defineJoinPoint()")
	private Object cacheProcess(ProceedingJoinPoint joinPoint) throws Throwable {
		long     start      = System.currentTimeMillis();
		Class<?> targetClz  = joinPoint.getTarget().getClass();
		String   methodName = joinPoint.getSignature().getName();

		LOGGER.info("开始执行{}方法,参数值为:{}", methodName, Jackson.mobile().writeValueAsString(joinPoint.getArgs()));

		Object object = joinPoint.proceed();
		long   cost   = System.currentTimeMillis() - start;
		LOGGER.info("服务类:{},方法:{},参数:{},耗时:{}ms", targetClz.getName(), methodName, Jackson.mobile().writeValueAsString(joinPoint.getArgs()), cost);
		return object;
	}

	@Pointcut("execution(public * com.xiaoka.cloud.stock.service.open.*Service.*(com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam,..))")
	public void defineJoinPoint() {
	}

}

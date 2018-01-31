package com.xiaoka.cloud.stock.service.core.aspect;

import com.xiaoka.cloud.stock.service.epc.EpcRedisService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Do something
 *
 * @author gancao 2017/11/28 10:20
 * @see [相关类/方法]
 * @since [版本号]
 */
@Aspect
@Component
public class EpcRepoAspect {

	private static Logger log = LoggerFactory.getLogger(EpcRepoAspect.class);

	@Resource
	private EpcRedisService epcRedisService;

	@Around("within(com.xiaoka.cloud.stock.core.epc.impl.*ServiceImpl)")
	private Object filter(ProceedingJoinPoint jp) throws Throwable {
		//repo层是否拦截
		if (epcRedisService.isFilterRepo()) {
			return null;
		}
		return jp.proceed();
	}
}

package com.xiaoka.cloud.stock.service.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 对外接口签名注解
 *
 * @author gancao 2017/11/13 14:53
 * @see [相关类/方法]
 * @since [版本号]
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiSignAuth {

	/**
	 * 需要校验签名，默认false
	 *
	 * @return
	 */
	boolean value() default false;
}

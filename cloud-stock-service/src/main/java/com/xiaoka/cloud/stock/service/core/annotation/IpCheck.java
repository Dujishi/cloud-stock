package com.xiaoka.cloud.stock.service.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Do something
 *
 * @author gancao 2017/11/16 11:58
 * @see [相关类/方法]
 * @since [版本号]
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface IpCheck {

	boolean check() default true;
}

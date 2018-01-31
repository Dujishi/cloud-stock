package com.xiaoka.cloud.stock.service.core.constraint;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zhouze
 * @date 2017/11/29
 * @see [相关类/方法]
 * @since [版本号]
 */
@Target({METHOD})
@Retention(RUNTIME)
public @interface RestrictValve {

	/**
	 * 设置是否启用拦截,默认启用
	 *
	 * @return
	 */
	boolean value() default true;

	/**
	 * 设置拦截过期间隔时间，默认 60 秒
	 * 请设置一个合理的时间
	 *
	 * @return
	 */
	int expire() default 60;

	/**
	 * 过期时间单位
	 * 注意，暂时仅支持 SECONDS、MINUTES、HOURS、DAYS
	 * 请勿设置毫秒、纳秒等其他级别的时间单位，其他时间单位都按SECONDS处理
	 *
	 * @return
	 */
	TimeUnit timeUnit() default TimeUnit.SECONDS;

	/**
	 * 拦截参数名称列表
	 * 假如设置该值，则判定只有该值参与限制
	 * 假如不设置该值，则忽略所有参数，判定该服务直接参与限制
	 * 该参数仅支持基础类型的参数数据，如String、int、double等，暂不支持复杂对象的类型
	 *
	 * @return
	 */
	String[] paramNames() default {};

}

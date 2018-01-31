package com.xiaoka.cloud.stock.admin.core.permission;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * <pre>
 * 权限拦截器的标注标识。
 * 在没有声明标注时，系统依然默认启用拦截逻辑的处理
 * @author winner
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface PermissionFilter {

	/**
	 * 访问该模块用户是否需要登录，与 {@link #login()} 同效
	 * @return 默认用户需要登录
	 */
	boolean value() default true;
	
	/**
	 * 访问该模块用户是否需要登录
	 * @return 默认用户需要登录
	 */
	boolean login() default true;
	
	/**
	 * 访问该模块是否启用URL权限授权处理
	 * @return 默认进行权限拦截
	 */
	boolean auth() default true;

}

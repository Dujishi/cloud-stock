package com.xiaoka.cloud.stock.web.util;

import com.xiaoka.cloud.stock.service.supplier.constant.SessionConstant;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Session工具类
 *
 * @author gancao 2017/11/11 15:37
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SessionUtil {

	public static CloudSupplierUserDto getUser(HttpServletRequest request) {
		return (CloudSupplierUserDto) request.getSession().getAttribute(SessionConstant.SESSION_USER);
	}

	/**
	 * 保存登录用户信息
	 *
	 * @param request
	 * @param user
	 */
	public static void login(HttpServletRequest request, CloudSupplierUserDto user) {
		request.getSession().setAttribute(SessionConstant.SESSION_USER, user);
	}

	/**
	 * 保存登录用户信息
	 *
	 * @param user
	 */
	public static void login(CloudSupplierUserDto user) {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if(request == null){
			return;
		}
		request.getSession().setAttribute(SessionConstant.SESSION_USER, user);
	}

	public static void logout(HttpServletRequest request) {
		request.getSession().removeAttribute(SessionConstant.SESSION_USER);
	}

	public static CloudSupplierUserDto getCurrentUser(){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
		if(request == null){
			return null;
		}
		return getCurrentUser(request);
	}

	public static Integer getSupplierId(HttpServletRequest request) {
		return getCurrentUser(request).getSupplierId();
	}

	public static String getPhone(HttpServletRequest request) {
		return getCurrentUser(request).getPhone();
	}

	private static CloudSupplierUserDto getCurrentUser(HttpServletRequest request) {
		return (CloudSupplierUserDto) request.getSession().getAttribute(SessionConstant.SESSION_USER);
	}
}

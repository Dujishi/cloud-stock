package com.xiaoka.cloud.stock.admin.core.permission;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.xiaoka.freework.container.action.WildcardPermission;
import com.xiaoka.freework.container.web.ResponseType;
import com.xiaoka.freework.container.web.ResponseType.Type;
import com.xiaoka.freework.container.web.ResponseTypeDecision;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.web.RequestUtil;
import com.xiaoka.ups.client.UpsClient;
import com.xiaoka.ups.client.context.RequestContextContainer;
import com.xiaoka.ups.client.dto.UserDto;
import com.xiaoka.ups.client.facade.ServiceApi;
import com.xiaoka.ups.client.message.UpsClientErrorMessage;
import com.xiaoka.ups.client.support.PermissionInterceptor.UserRecover;
import com.xiaoka.ups.client.support.UpsClientImpl;

/**
 * <pre>
 * 默认规则：
 * 1. 在没有 Permission 标注设置时，默认都必须进行权限拦截;
 * 2. 当controller没指定{@link ResponseType}，同时无法从HTTP请求头中解析出规则时，默认返回类型是JSON类型
 * 3. 用户信息获取：A.从Cookie中还原; B.从Session中还原;
 * 4. 还原用户角色信息;
 * 
 * @author winner
 *
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter implements InitializingBean {
	private static final String LOGIN_URL = "/ups/login?target=";
//	private static final String NO_PERMISSION_URL = "/ups/permission/no?target=";
	private static final String NO_PERMISSION_URL = "/ups/?target=";
	
	
	public enum SystemType {
		DEFAULT, // 默认传统系统
		DOMAIN // 领域系统
	}
	//
	private UserRecover recover = UserRecover.HEADER;

	private ServiceApi serviceApi;
	
	private SystemType systemType = SystemType.DEFAULT;

	
	@Override
	public void afterPropertiesSet() throws Exception {
		if (recover == null || (recover != UserRecover.HEADER && recover != UserRecover.SESSION && recover != UserRecover.API))
			throw new IllegalArgumentException("用户还原类型未正确指定，只能是 HEADER 或 SESSION");
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {// 可能处理的是静态资源。无需登录检测
			return true;
		}
		HandlerMethod handlerMethod = (HandlerMethod) handler;
		// 如果是框架的 controller ，不进行授权的判断
		if(StringUtils.startsWith(handlerMethod.getBeanType().getName(), "com.xiaoka.freework")){
			return true;
		}
		
		//设置当前环境上下文的 Reqest 对象
		UpsClientImpl.setRequest(request, getUserRecoverModel(request));//只要是的 HandlerMethod就设置

		RequestContextContainer.getInstance().put(serviceApi);

		boolean shouldUserLogin = true;
		boolean shouldAuthPath = true;
		if(handlerMethod.getBean() instanceof WildcardPermission){//通过通配符方式匹配权限
			WildcardPermission pagePermission = (WildcardPermission)handlerMethod.getBean();
			shouldUserLogin = pagePermission.shouldLogin(request);
			shouldAuthPath = pagePermission.shouldAuth(request);
		} else{//通过标注方式来匹配权限
			PermissionFilter permission = this.findPermission(handlerMethod);
			if (permission != null) {// 指明不过权限的，不需要登录检测
				shouldUserLogin = !(!permission.value() || !permission.login());
				shouldAuthPath = permission.auth();
			}
		}
		if(!shouldUserLogin){//如果不需要用户登录
			return true;
		}
		// 从Cookie或Session中获取用户信息
		UserDto user = Utils.get(UpsClient.class).getUser();
		if (user == null) {
			this.doNoLogin(request, response, handlerMethod);
			return false;
		}
		if(!shouldAuthPath){//如果不需要用户的授权判断
			return true;
		}
		// 检查用户权限
		if(Utils.get(UpsClient.class).checkPermission() == null){
			this.doNoPermission(request, response, handlerMethod);
			return false;
		}
		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
	        throws Exception {
		//将线程变量中的请求对象置为空
		UpsClientImpl.setRequest(null, null);
		RequestContextContainer.getInstance().cleanup();
	}

	/**
	 * 还原用户数据模型
	 * @return
     */
	protected UserRecover getUserRecoverModel(HttpServletRequest request) {
		return this.recover;
	}
	
	/**
	 * 还原用户数据
	 * @param handlerMethod
	 * @return
	 */
	private PermissionFilter findPermission(HandlerMethod handlerMethod) {
		PermissionFilter permission = handlerMethod.getMethodAnnotation(PermissionFilter.class);
		if(permission == null){
			permission = handlerMethod.getBeanType().getAnnotation(PermissionFilter.class);
		}
		return permission;
	}


	/**
	 * 在用户没登录时的处理
	 * 
	 * @param request
	 * @param response
	 * @param handlerMethod
	 */
	protected void doNoLogin(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
		// 获取返回的数据类型
		Type responseType = ResponseTypeDecision.decisionByRequest(request, handlerMethod);
		try {
			if (responseType == Type.PAGE) {
				if(systemType == SystemType.DOMAIN){//如果是领域系统
					PrintWriter writer = response.getWriter();
					try{
						writer.print(StringUtils.join("<script>top.location.href='", LOGIN_URL, URLEncoder.encode(RequestUtil.fullPathWithQuery(request), "UTF-8"), "'</script>"));
					}finally {
						writer.close();
					}
				} else {
					response.sendRedirect(StringUtils.join(LOGIN_URL, URLEncoder.encode(RequestUtil.fullPathWithQuery(request), "UTF-8")));
				}
			} else if (responseType == Type.JSON) {
				throw new ApiException(UpsClientErrorMessage.USER_NO_LOGIN);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 在用户没权限时的处理
	 * 
	 * @param request
	 * @param response
	 * @param handlerMethod
	 */
	protected void doNoPermission(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
		// 获取返回的数据类型
		Type responseType = ResponseTypeDecision.decisionByRequest(request, handlerMethod);
		try {
			if (responseType == Type.PAGE) {
				response.sendRedirect(StringUtils.join(NO_PERMISSION_URL, URLEncoder.encode(RequestUtil.fullPathWithQuery(request), "UTF-8")));
			} else if (responseType == Type.JSON) {
				throw new ApiException(UpsClientErrorMessage.USER_NO_PERMISSION);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void setRecover(UserRecover recover) {
		this.recover = recover;
	}

	public void setSystemType(SystemType systemType) {
		this.systemType = systemType;
	}

	public void setServiceApi(ServiceApi serviceApi) {
		this.serviceApi = serviceApi;
	}
}

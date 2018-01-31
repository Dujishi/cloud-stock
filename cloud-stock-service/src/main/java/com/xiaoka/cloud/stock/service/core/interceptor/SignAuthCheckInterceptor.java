package com.xiaoka.cloud.stock.service.core.interceptor;

import com.xiaoka.cloud.stock.service.core.annotation.ApiSignAuth;
import com.xiaoka.cloud.stock.service.core.util.RequestUtils;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiConstants;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import com.xiaoka.cloud.stock.service.open.utils.ApiSignUtil;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierService;
import com.xiaoka.freework.utils.web.RequestUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zhouze
 * @date 2017/11/13
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SignAuthCheckInterceptor extends HandlerInterceptorAdapter implements InitializingBean {

	private static final Logger LOGGER = LoggerFactory.getLogger(SignAuthCheckInterceptor.class);

	@Resource
	CloudSupplierService cloudSupplierService;

	@Override
	public void afterPropertiesSet() throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		//不需要校验签名时
		if (!acquireSignAuthVal((HandlerMethod) handler)) {
			return true;
		}

		final String requestBody = RequestUtil.getBody(request, OpenApiConstants.CHARSET);

		final String url   = RequestUtil.getServletPath(request);
		final String appId = RequestUtils.getParamVal(request, OpenApiConstants.PARAM_APPID);
		final String vt    = RequestUtils.getParamVal(request, OpenApiConstants.PARAM_VT);
		final String sign  = RequestUtils.getParamVal(request, OpenApiConstants.PARAM_SIGN);
		final String key   = cloudSupplierService.getKeyByAppId(appId);
		if (StringUtils.isBlank(key)) {
			LOGGER.info("根据AppId：{}获取key失败", appId);
			throw OpenApiReturnCodeEnum.FAIL_ILLEGALITY_APP_ID.ifApiException();
		}
		if (StringUtils.isBlank(vt)) {
			LOGGER.info("接口时效性验证未通过,{}", vt);
			throw OpenApiReturnCodeEnum.FAIL_VALID_TIME.ifApiException();
		}

		final String actualUnSign = url.concat(appId).concat(key).concat(vt).concat(requestBody);
		final String actualSign   = ApiSignUtil.generateApiSign(actualUnSign);

		if (StringUtils.isBlank(actualSign) || StringUtils.isBlank(sign)) {
			LOGGER.info("[签名验证失败empty] ====> 传入签名:{},实际签名:{},url:{},data:{},appId:{},vt:{},key:{}",
					sign, actualSign, url, requestBody, appId, vt, key);
			throw OpenApiReturnCodeEnum.FAIL_SIGN.ifApiException();
		}

		if (!actualSign.equals(sign)) {
			LOGGER.info("[签名验证失败empty] ====> 传入签名:{},实际签名:{},url:{},data:{},appId:{},vt:{},key:{}",
					sign, actualSign, url, requestBody, appId, vt, key);
			throw OpenApiReturnCodeEnum.FAIL_SIGN.ifApiException();
		}

		LOGGER.info("[签名验证成功] ====> 传入签名:{},实际签名:{}", sign, actualSign);
		return true;
	}


	/**
	 * 通过注解获取是否需要签名校验
	 *
	 * @param handler
	 * @return
	 */
	private boolean acquireSignAuthVal(HandlerMethod handler) {
		ApiSignAuth apiSignAuth = handler.getMethodAnnotation(ApiSignAuth.class);
		if (null == apiSignAuth) {
			apiSignAuth = handler.getBeanType().getAnnotation(ApiSignAuth.class);
		}
		if (null == apiSignAuth) {
			return false;
		}
		return apiSignAuth.value();
	}


}

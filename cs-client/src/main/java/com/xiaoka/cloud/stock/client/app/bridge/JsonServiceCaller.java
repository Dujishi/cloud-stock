package com.xiaoka.cloud.stock.client.app.bridge;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopProxyUtils;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.xiaoka.freework.container.context.ContainerContext;
import com.xiaoka.freework.help.api.ApiAsserts;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.help.api.ApiResult;
import com.xiaoka.freework.utils.json.Jackson;

public class JsonServiceCaller {
	private static Logger logger = LoggerFactory.getLogger(JsonServiceCaller.class);

	public static JsonServiceCaller caller(String service, String params) {
		return new JsonServiceCaller(service, params);
	}

	private TargetMethod targetMethod;
	private Map<String, TargetMethod> methodMap = Collections.synchronizedMap(new HashMap<>());
	private String[] arguments;
	private ApiResult<?> result;

	private JsonServiceCaller(String service, String params) {
		try {
			this.initialize(service, params);
		} catch (ApiException e) {
			result = new ApiResult<String>(e.getErrCode(), e.getMessage(), ExceptionUtils.getStackTrace(e));
			logger.warn("初始化失败", e);
		}
	}

	public ApiResult<?> call() {
		if(result != null) {
			return result;
		}
		try {
			this.targetCall();
		} catch (Exception e) {
			ApiException ae = (e instanceof ApiException)? (ApiException)e : new ApiException(JSONServiceError.EXEC_ERR, e.getMessage());
			result = new ApiResult<String>(ae.getErrCode(), ae.getMessage(), ExceptionUtils.getStackTrace(e));
			logger.warn("执行失败", e);
		}
		return result;
	}

	private void initialize(String service, String params) {
		ApiAsserts.notBlank(service, JSONServiceError.PARAM_ERR, "服务名称不能为空");
		String[] targets = StringUtils.split(service, ".");
		ApiAsserts.isTrue(targets != null && targets.length == 2, JSONServiceError.PARAM_ERR, "服务名称格式为 {service}.{method}");
		arguments = this.buildCallArgs(params);
		String methodKey = StringUtils.join(service, "(", String.valueOf(arguments.length), ")");
		targetMethod = methodMap.get(methodKey);
		if (targetMethod == null) {
			synchronized (methodMap) {
				targetMethod = methodMap.get(methodKey);
				if (targetMethod == null) {
					String serviceName = targets[0];
					String methodName = targets[1];
					targetMethod = new TargetMethod();
					// 查找服务
					targetMethod.bean = ContainerContext.get().getContext().getBean(serviceName);
					ApiAsserts.notNull(targetMethod.bean, JSONServiceError.PARAM_ERR, StringUtils.join("找不到目标服务 [", serviceName, "]"));
					// 查找方法
					targetMethod.method = this.findMethod(targetMethod.bean, methodName, arguments.length);
					ApiAsserts.notNull(targetMethod.method, JSONServiceError.PARAM_ERR, StringUtils.join("找不到目标服务方法 [", methodKey, "]"));
					methodMap.put(methodKey, targetMethod);
				}
			}
		}
	}

	private void targetCall() throws Exception {
		if (arguments.length == 0) {
			result = new ApiResult<>(targetMethod.invoke(null));
		} else {
			Type[] paramTypes = targetMethod.method.getGenericParameterTypes();
			Object[] args = new Object[paramTypes.length];
			for (int i = 0; i < paramTypes.length; i++) {
				Type type = paramTypes[i];
				if (type instanceof ParameterizedType) {
					ParameterizedType paramType = (ParameterizedType) type;
					args[i] = this.transferObject(arguments[i], (Class<?>)paramType.getRawType(), paramType.getActualTypeArguments());
				} else {
					args[i] = this.transferObject(arguments[i], (Class<?>)type, null);
				}
			}
			result = new ApiResult<>(targetMethod.invoke(args));
		}
	}

	/**
	 * 构造请求参数，通过 JsonNode 来解析
	 */
	private String[] buildCallArgs(String params) {
		String[] args = new String[] {};
		if (StringUtils.isNotBlank(params)) {
			try {
				JsonNode nodes = Jackson.simple().getObjectMapper().readTree(params);
				if (nodes.isArray()) {
					List<String> jsonArgs = new ArrayList<String>();
					for (int i = 0; nodes.has(i); i++) {
						JsonNode node = nodes.get(i);
						jsonArgs.add(node.toString());
					}
					args = jsonArgs.toArray(new String[] {});
				} else {
					args = new String[] { nodes.toString() };
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
		return args;
	}

	private Method findMethod(Object bean, String methodName, int argsLen) {
		Class<?> targetClass = AopProxyUtils.ultimateTargetClass(bean);
		for (Method method : targetClass.getMethods()) {
			if (StringUtils.equals(method.getName(), methodName)) {
				if (method.getGenericParameterTypes().length == argsLen) {
					return method;
				}
			}
		}
		return null;
	}

	private Object transferObject(String data, Class<?> paramType, Type[] genericTypes) {
		Object obj = null;
		if (Collection.class.isAssignableFrom(paramType)) {
			if (genericTypes == null || genericTypes.length < 1) {
				throw new IllegalArgumentException("对于Collection类型必须有泛化类型");
			}
			@SuppressWarnings("unchecked")
			CollectionType collectionType = Jackson.base().getTypeFactory().constructCollectionType(
					(Class<? extends Collection<?>>) paramType, Jackson.base().getTypeFactory().constructType(genericTypes[0]));
			obj = Jackson.simple().readValue(data, collectionType);
		} else if (Map.class.isAssignableFrom(paramType)) {
			if (genericTypes == null || genericTypes.length != 2) {
				throw new IllegalArgumentException("参数中Map对象的泛型定义必须是key和value的两个类型");
			}
			@SuppressWarnings("unchecked")
			MapType mapType = Jackson.base().getTypeFactory().constructMapType((Class<? extends Map<?, ?>>) paramType,
					Jackson.base().getTypeFactory().constructType(genericTypes[0]),
					Jackson.base().getTypeFactory().constructType(genericTypes[1]));
			obj = Jackson.simple().readValue(data, mapType);
		} else { // 其他JAVA对象的泛型处理
			JavaType[] types = null;
			if (genericTypes != null) {
				types = new JavaType[genericTypes.length];
				for (int j = 0; j < genericTypes.length; j++) {
					types[j] = Jackson.base().getTypeFactory().constructType(genericTypes[j]);
				}
			}
			JavaType javaType = Jackson.base().getTypeFactory().constructSimpleType(paramType, types);
			obj = Jackson.simple().readValue(data, javaType);
		}
		return obj;
	}

	private static class TargetMethod {
		private Object bean;
		private Method method;

		Object invoke(Object[] args) throws Exception {
			return args == null ? method.invoke(bean) : method.invoke(bean, args);
		}
	}

}

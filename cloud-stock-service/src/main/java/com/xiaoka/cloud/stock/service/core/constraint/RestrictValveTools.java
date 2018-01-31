package com.xiaoka.cloud.stock.service.core.constraint;

import com.google.common.collect.Maps;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;

/**
 * @author zhouze
 * @date 2017/11/30
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RestrictValveTools {
	private static final Logger LOGGER = LoggerFactory.getLogger(RestrictValveTools.class);

	private RestrictValveTools() {
	}

	private static final int INITIAL_VALUE  = 0;
	private static final int INTERVAL_VALUE = 1;

	private static AtomicInteger flagCount = new AtomicInteger(INITIAL_VALUE);
	private static volatile RestrictValveTools instance;

	private Map<String, RestrictValve> restrictMap = new ConcurrentHashMap<>();

	private final Object sync_map = new Object();

	static RestrictValveTools singleInstance() {
		if (null == instance) {
			synchronized (RestrictValveTools.class) {
				if (null == instance) {
					instance = new RestrictValveTools();
					LOGGER.info("init RestrictTools success,flag:{}", flagCount.addAndGet(INTERVAL_VALUE));
				}
			}
		}
		return instance;
	}

	RestrictValve getRestrictAttribute(MethodSignature methodSign) {
		final String  mapKey        = buildClazzMethodKey(methodSign);
		RestrictValve restrictValve = restrictMap.get(mapKey);
		if (null == restrictValve) {
			LOGGER.info("根据key:{}值未取到任何RestrictValve对象", mapKey);
			restrictValve = methodSign.getMethod().getAnnotation(RestrictValve.class);
			if (null == restrictValve) {
				return null;
			}
			synchronized (sync_map) {
				restrictMap.putIfAbsent(mapKey, restrictValve);
			}
			LOGGER.info("根据key:{}值写入RestrictValve对象:{}", mapKey, restrictValve.toString());
		}
		return restrictValve;
	}

	String buildCacheKey(Object[] args, MethodSignature methodSign) {
		RestrictValve restrictValve = getRestrictAttribute(methodSign);
		checkRestrictArguments(methodSign, restrictValve);

		String[]            restrictParams = restrictValve.paramNames();
		Map<String, Object> paramValMap    = getParamValByName(args, methodSign);

		StringBuilder cacheKeySb = new StringBuilder(buildClazzMethodKey(methodSign))
				.append("=>")
				.append("{");
		//当restrictParams不为空时，意味着带参数限制
		if (ArrayUtils.isNotEmpty(restrictParams)) {
			Arrays.stream(restrictParams).forEach(paramName -> {
				Object object = paramValMap.get(paramName);
				cacheKeySb.append(paramName)
						.append(":")
						.append(String.valueOf(object))
						.append(",");
			});
		}
		cacheKeySb.append("}");
		String cacheKey = cacheKeySb.toString();
		LOGGER.info("当前生成的CacheKey为：{}", cacheKey);
		return cacheKey;
	}

	private String buildClazzMethodKey(MethodSignature methodSign) {
		String longMethodSign = StringUtils.deleteWhitespace(methodSign.getMethod().toGenericString().toLowerCase());
		//这里取方法短签名与长签名hashCode的拼接字符串，这样能为缩短key字符长度,也能大概率减小碰撞概率，是为了在长度和碰撞几率上做一个平衡
		String clazzMethodKey = methodSign.toShortString().concat(",").concat(String.valueOf(methodSign.getParameterNames().length))
				.concat(",").concat(String.valueOf(longMethodSign.hashCode()));
		LOGGER.info("当前生成的ClazzMethodKey为：{}", clazzMethodKey);
		return clazzMethodKey;
	}

	private Map<String, Object> getParamValByName(Object[] args, MethodSignature methodSign) {
		Map<String, Object> maps = Maps.newHashMap();

		String[] parameterNameArray = methodSign.getParameterNames();
		if (ArrayUtils.isNotEmpty(parameterNameArray) && ArrayUtils.isNotEmpty(args)) {
			for (int i = 0; i < parameterNameArray.length; i++) {
				maps.put(parameterNameArray[i], args[i]);
			}
		}
		return maps;
	}

	private Predicate<String> checkParamPredicate(String[] allParams) {
		return restrictParamName -> Arrays.stream(allParams).noneMatch(x -> Objects.equals(restrictParamName, x));
	}

	private void checkRestrictArguments(MethodSignature methodSign, RestrictValve restrictValve) {
		String[] restrictParams = restrictValve.paramNames();
		String[] allParams      = methodSign.getParameterNames();

		boolean checkParamEmptyFlag = ArrayUtils.isEmpty(allParams) && ArrayUtils.isNotEmpty(restrictParams);
		boolean checkParamMismatchFlag = ArrayUtils.isNotEmpty(allParams) && ArrayUtils.isNotEmpty(restrictParams)
				&& Arrays.stream(restrictParams).anyMatch(checkParamPredicate(allParams));

		if (checkParamEmptyFlag || checkParamMismatchFlag) {
			LOGGER.error("配置的参数名与当前服务方法签名不一致,服务方法:{}", methodSign.toLongString());
			//发现签名与配置不一致时则抛出异常，及早抛出，避免非正常逻辑继续执行
			throw new ApiException("-1", "配置的参数名与当前服务方法签名不一致");
		}
	}

}

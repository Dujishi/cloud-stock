package com.xiaoka.cloud.stock.service.core.constraint;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.service.core.constraint.cache.ValveCachePo;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.lock.ClusterLock;
import com.xiaoka.mid.task.util.DateUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * 限制repo,service方法使用，通过切面方式侵入
 * 此处@RestrictMethod必须标记在具体实现方法上才会生效
 * 针对个别接口服务,可以限制方法在特定时间段内是否继续调用
 *
 * @author zhouze
 * @date 2017/11/29
 * @see RestrictValve
 * @since 0.0.1
 */
@Aspect
@Component
public class RestrictMethodAspect {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestrictMethodAspect.class);

	@Resource
	private RestrictCacheService restrictCacheRedisStringImpl;

	private static final int    ACQUIRE_TIME = 10;
	private static final String LOCK_PATH    = "/cloud-stock/RestrictMethodAspect/doRestrictValve";

	@Around("defineJoinPoint()")
	private Object doRestrictValve(ProceedingJoinPoint joinPoint) throws Throwable {
		if (!(joinPoint.getSignature() instanceof MethodSignature)) {
			LOGGER.error("该方法不能使用该限制，请检查该服务方法: {}", joinPoint.getSignature().toLongString());
			return joinPoint.proceed();
		}

		MethodSignature methodSign    = (MethodSignature) joinPoint.getSignature();
		RestrictValve   restrictValve = RestrictValveTools.singleInstance().getRestrictAttribute(methodSign);

		//验证该方法是否能够批准其继续执行
		if (null == restrictValve || !restrictValve.value()) {
			return joinPoint.proceed();
		}
		Object[] args = joinPoint.getArgs();
		LOGGER.info("该方法进入是否限制继续执行验证,方法签名:{},参数:{},RestrictValve:{}",
				methodSign.toLongString(), JSON.toJSONString(args), restrictValve.toString());

		//生成key
		String key = RestrictValveTools.singleInstance().buildCacheKey(args, methodSign);

		//读取并验证阀门是否已过期
		ValveCachePo valveCachePo = restrictCacheRedisStringImpl.queryRestrictValve(key);
		if (checkExpireRestrict(restrictValve, valveCachePo)) {
			LOGGER.info("[shut valve]未批准该方法继续执行，方法签名:{},参数:{},RestrictValve:{}，valveCachePo：{}",
					methodSign.toLongString(), JSON.toJSONString(args), restrictValve.toString(), JSON.toJSONString(valveCachePo));
			return null;
		}

		ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(LOCK_PATH, key);
		try {
			if (locker.acquire(ACQUIRE_TIME, TimeUnit.SECONDS)) {
				boolean flag = locker.execute(() -> {
					ValveCachePo syncValveCachePo = restrictCacheRedisStringImpl.queryRestrictValve(key);
					if (checkExpireRestrict(restrictValve, syncValveCachePo)) {
						LOGGER.info("[shut valve]未批准该方法继续执行，方法签名:{},参数:{},RestrictValve:{}，syncValveCachePo：{}",
								methodSign.toLongString(), JSON.toJSONString(args), restrictValve.toString(), JSON.toJSONString(syncValveCachePo));
						return false;
					}
					ValveCachePo newCache = buildValveCachePo(methodSign, args, key, restrictValve);
					restrictCacheRedisStringImpl.writeRestrictValve(key, newCache);
					LOGGER.info("将阀门key写入缓存成功，方法签名:{},参数:{},RestrictValve:{}，写入的key:{},写入的newCache：{}",
							methodSign.toLongString(), JSON.toJSONString(args), restrictValve.toString(), key, JSON.toJSONString(newCache));
					return true;
				});
				if (!flag) {
					return null;
				}
			}
		} catch (Exception e) {
			LOGGER.error("集群锁中处理遇到异常:{}", key, e);
			throw e;
		}

		return joinPoint.proceed();
	}

	/**
	 * 验证是否超过有效期
	 *
	 * @param restrictValve
	 * @param valveCachePo
	 * @return
	 */
	private boolean checkExpireRestrict(RestrictValve restrictValve, ValveCachePo valveCachePo) {
		//是否在有效期内，未过期的数据则直接拦截
		if (null != valveCachePo) {
			boolean expireFlag = expireRestrictValue(restrictValve, valveCachePo);
			if (expireFlag) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 生成缓存的阀门信息
	 *
	 * @param methodSign 方法签名
	 * @param key        标识阀门的唯一key
	 * @return
	 */
	private ValveCachePo buildValveCachePo(MethodSignature methodSign, Object[] args, String key, RestrictValve restrictValve) {
		ValveCachePo newCache = new ValveCachePo();
		newCache.setKey(key);
		newCache.setMethodSign(methodSign.toLongString());
		newCache.setLastTime(System.currentTimeMillis());
		newCache.setUpdateTime(DateUtil.format(new Date()));
		newCache.setClassName(methodSign.getDeclaringType().getSimpleName());
		newCache.setMethodName(methodSign.getMethod().getName());
		newCache.setParamJson(JSON.toJSONString(args));
		newCache.setExpire(restrictValve.expire());
		newCache.setTimeUnit(restrictValve.timeUnit());
		return newCache;
	}

	/**
	 * 计算缓存内信息是否期满
	 * 注：毫秒级比较
	 *
	 * @param restrictValve
	 * @param valveCachePo
	 * @return true:在有效期内;false:已经失效
	 */
	private boolean expireRestrictValue(RestrictValve restrictValve, ValveCachePo valveCachePo) {
		long interval      = restrictValve.expire();
		long lastTime      = valveCachePo.getLastTime();
		long currentMillis = System.currentTimeMillis();
		long intervalMillis;
		switch (restrictValve.timeUnit()) {
			case DAYS:
				intervalMillis = interval * 24 * 60 * 60 * 1000L;
				break;
			case HOURS:
				intervalMillis = interval * 60 * 60 * 1000L;
				break;
			case MINUTES:
				intervalMillis = interval * 60 * 1000L;
				break;
			case SECONDS:
				intervalMillis = interval * 1000L;
				break;
			default:
				intervalMillis = interval * 1000L;
				break;
		}
		boolean expireFlag = (currentMillis - lastTime) <= intervalMillis;
		LOGGER.info("[正在验证是否过期]->当前时间：{}ms，上一次有效时间：{}ms，间隔时间：{}ms，是否过期：{}",
				currentMillis, lastTime, intervalMillis, expireFlag);
		return expireFlag;
	}


	@Pointcut("(@within(org.springframework.stereotype.Service)||@within(org.springframework.stereotype.Repository))" +
			"&&@annotation(com.xiaoka.cloud.stock.service.core.constraint.RestrictValve)")
	public void defineJoinPoint() {
	}

}

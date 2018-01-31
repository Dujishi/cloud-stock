package com.xiaoka.cloud.stock.service.core.constraint.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.core.constraint.RestrictCacheService;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 考虑使用String结构实现是因为String结构简单，能利用redis的expire功能有效节省内存、规避并发问题
 *
 * @author zhouze
 * @date 2017/12/6
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("restrictCacheRedisStringImpl")
public class RestrictCacheRedisStringImpl implements RestrictCacheService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RestrictCacheRedisStringImpl.class);

	private static final String VALVE_HASH_KEY = "CLOUD_STOCK_RESTRICT_VALVE_STRING";

	@Resource(name = "containerRedisTemplate")
	private RedisOperations<String, String> redisTemplate;

	@Override
	public ValveCachePo queryRestrictValve(String key) {
		String actualKey = buildActualKey(key);
		LOGGER.info("[执行queryRestrictValve方法]此时根据key:{}获取到actualKey为:{}", key, actualKey);
		BoundValueOperations<String, String> boundValueOperations = redisTemplate.boundValueOps(actualKey);

		String cacheJson = boundValueOperations.get();
		if (StringUtils.isBlank(cacheJson)) {
			return null;
		}
		return Jackson.base().readValue(cacheJson, new TypeReference<ValveCachePo>() {
		});
	}

	@Override
	public void writeRestrictValve(String key, ValveCachePo newCache) {
		String actualKey = buildActualKey(key);
		LOGGER.info("[执行writeRestrictValve方法]此时根据key:{}获取到actualKey为:{}", key, actualKey);
		BoundValueOperations<String, String> boundHashOperations = redisTemplate.boundValueOps(actualKey);

		String cacheJson = Jackson.base().writeValueAsString(newCache);
		boundHashOperations.set(cacheJson);
		boundHashOperations.expire(newCache.getExpire(), newCache.getTimeUnit());
		LOGGER.info("[执行writeRestrictValve方法完成]此时根据key:{}获取到actualKey为:{},设置失效时间为:{}", key, actualKey, cacheJson);
	}

	private String buildActualKey(String key) {
		return VALVE_HASH_KEY.concat("_").concat(key);
	}

}

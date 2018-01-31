package com.xiaoka.cloud.stock.service.core.constraint.cache;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.core.constraint.RestrictCacheService;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 该实现是Hash结构，如果要使用该实现，需要额外提供watch监视已无效的数据，及时删除，这个watch之后删除需要考虑并发
 * 且hash结构消耗更多内存，所以在此基础上考虑换成{@link RestrictCacheRedisStringImpl}实现
 *
 * @author zhouze
 * @date 2017/12/2
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("restrictCacheRedisHashImpl")
public class RestrictCacheRedisHashImpl implements RestrictCacheService {
	private static Logger LOGGER = LoggerFactory.getLogger(RestrictCacheRedisHashImpl.class);

	private static final String VALVE_HASH_KEY = "CLOUD_STOCK_RESTRICT_VALVE_HASH";

	@Resource(name = "containerRedisTemplate")
	private RedisOperations<String, String> redisTemplate;


	@Override
	public ValveCachePo queryRestrictValve(String key) {
		String actualKey = buildActualKey(key);
		LOGGER.info("[执行queryRestrictValve方法]此时根据key:{}获取到actualKey为:{}", key, actualKey);
		BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(actualKey);

		String cacheJson = boundHashOperations.get(key);
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
		BoundHashOperations<String, String, String> boundHashOperations = redisTemplate.boundHashOps(actualKey);

		String cacheJson = Jackson.base().writeValueAsString(newCache);
		boundHashOperations.put(key, cacheJson);
	}

	private String buildActualKey(String key) {
		return VALVE_HASH_KEY.concat("_").concat(String.valueOf(module(key)));
	}

}

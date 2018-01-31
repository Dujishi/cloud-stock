package com.xiaoka.cloud.stock.service.epc.impl;

import com.xiaoka.cloud.stock.service.epc.EpcRedisService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Do something
 *
 * @author gancao 2017/11/28 10:22
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class EpcRedisServiceImpl implements EpcRedisService{

	private static final String FILTER_REPO_KEY = "filter_epc";
	private static Logger log = LoggerFactory.getLogger(EpcRedisServiceImpl.class);

	@Resource(name = "containerRedisTemplate")
	private RedisOperations<String, String> redisTemplate;

	@Override
	public boolean isFilterRepo() {
		try {
			String value = redisTemplate.boundValueOps(FILTER_REPO_KEY).get();
			if (StringUtils.isNotBlank(value) && "1".equals(value)){
				return true;
			}
		}catch (Exception e){
			log.error("redis获取filter_epc出错", e);
		}
		return false;
	}

	@Override
	public boolean isFilterEpc() {
		try {
			String value = redisTemplate.boundValueOps(FILTER_REPO_KEY).get();
			if (StringUtils.isNotBlank(value) && "2".equals(value)){
				return true;
			}
		}catch (Exception e){
			log.error("redis获取filter_epc出错", e);
		}
		return false;
	}

	@Override
	public String openRepoFilter() {
		redisTemplate.boundValueOps(FILTER_REPO_KEY).set("1");
		return redisTemplate.boundValueOps(FILTER_REPO_KEY).get();
	}

	@Override
	public String openSuperEpcFilter() {
		redisTemplate.boundValueOps(FILTER_REPO_KEY).set("2");
		return redisTemplate.boundValueOps(FILTER_REPO_KEY).get();
	}

	@Override
	public void closeFilter() {
		redisTemplate.delete(FILTER_REPO_KEY);
	}
}

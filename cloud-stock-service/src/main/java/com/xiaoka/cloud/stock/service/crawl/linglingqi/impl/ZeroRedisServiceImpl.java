package com.xiaoka.cloud.stock.service.crawl.linglingqi.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroRedisService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/12/25 11:09
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroRedisServiceImpl implements ZeroRedisService {

	private static final String ZERO_SUB_LIST_KEY = "zero_sub_list";
	//帐号管理
	private static final String ZERO_ACCOUNT_MAP = "zero_account_map";
	private static final String ZERO_ERROR_ACCOUNT_MAP = "zero_error_account_map";
	private static final Splitter SPLITTER = Splitter.on(",").trimResults();

	private static Logger log = LoggerFactory.getLogger(ZeroRedisServiceImpl.class);

	@Resource(name = "containerRedisTemplate")
	private RedisOperations<String, String> redisTemplate;

	@Override
	public List<String> getSubList() {
		try {
			String value = redisTemplate.boundValueOps(ZERO_SUB_LIST_KEY).get();
			if (StringUtils.isNotBlank(value)) {
				return SPLITTER.splitToList(value);
			}
		} catch (Exception e) {
			log.error("redis获取zero_sub_list出错", e);
		}
		return null;
	}

	@Override
	public Map<Integer, AccountMsgDto> getAccountMap(Integer type) {
		Preconditions.checkNotNull(type, "帐号管理类型不能为空");
		Map<Integer, AccountMsgDto> result = Maps.newHashMap();
		Map<String, String> map = this.getBoundHashOperations(type).entries();
		if (Objects.nonNull(map) && !map.isEmpty()) {
			map.forEach((k, v) -> {
				try {
					AccountMsgDto accountMsgDto = Jackson.base().readValue(v, new TypeReference<AccountMsgDto>() {
					});
					if (Objects.nonNull(accountMsgDto)) {
						result.put(Integer.valueOf(k), accountMsgDto);
					}
				} catch (Exception e) {
					log.error("AccountMsgDto对象转换异常", e);
				}
			});
		}
		return result;
	}

	@Override
	public void putAccount(Integer supplierId, AccountMsgDto accountMsgDto, Integer type) {
		Preconditions.checkNotNull(supplierId, "供应商id不能为空");
		Preconditions.checkNotNull(accountMsgDto, "帐号信息不能为空");
		Preconditions.checkNotNull(type, "帐号管理类型不能为空");
		this.getBoundHashOperations(type).put(supplierId.toString(), Jackson.mobile().writeValueAsString(accountMsgDto));
	}

	@Override
	public void removeAccount(Integer supplierId, Integer type) {
		Preconditions.checkNotNull(supplierId, "供应商id不能为空");
		Preconditions.checkNotNull(type, "帐号管理类型不能为空");
		this.getBoundHashOperations(type).delete(supplierId.toString());
	}

	@Override
	public AccountMsgDto getAccountBySupplierId(Integer supplierId, Integer type) {
		Preconditions.checkNotNull(supplierId, "供应商id不能为空");
		Preconditions.checkNotNull(type, "帐号管理类型不能为空");
		String value = this.getBoundHashOperations(type).get(supplierId.toString());
		if (StringUtils.isNotBlank(value)) {
			try {
				AccountMsgDto accountMsgDto = Jackson.base().readValue(value, new TypeReference<AccountMsgDto>() {
				});
				if (Objects.nonNull(accountMsgDto)) {
					return accountMsgDto;
				}
			} catch (Exception e) {
				log.error("AccountMsgDto对象转换异常", e);
			}
		}
		return null;
	}

	private BoundHashOperations<String, String, String> getBoundHashOperations(Integer type) {
		if (Objects.equals(1, type)){
			return redisTemplate.boundHashOps(ZERO_ACCOUNT_MAP);
		}
		return redisTemplate.boundHashOps(ZERO_ERROR_ACCOUNT_MAP);
	}
}

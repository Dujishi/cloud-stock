package com.xiaoka.cloud.stock.client.business.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.xiaoka.cloud.stock.client.business.BusinessBridgeService;
import com.xiaoka.cloud.stock.client.business.PermissionBridgeService;
import com.xiaoka.cloud.stock.client.business.core.ErpStrategyContext;
import com.xiaoka.cloud.stock.client.business.core.ErpFactory;
import com.xiaoka.cloud.stock.client.business.dto.StorageDto;
import com.xiaoka.cloud.stock.client.business.dto.StrategyDto;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author zhouze
 * @date 2018/1/15
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class BusinessBridgeServiceImpl implements BusinessBridgeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BusinessBridgeServiceImpl.class);

	@Resource
	private PermissionBridgeService permissionBridgeService;

	@Override
	public List<StorageDto> getStorageByCodes(List<String> codes) {
		if (CollectionUtils.isEmpty(codes)) {
			return Collections.emptyList();
		}
		StrategyDto strategyDto = permissionBridgeService.getStrategy();
		if (null == strategyDto) {
			LOGGER.info("无策略标识对象，不予执行");
			return Collections.emptyList();
		}

		ErpStrategyContext context = ErpFactory.instance(strategyDto.getFlag());
		if (null == context) {
			LOGGER.info("策略标识未找到相应的执行策略，不予执行");
			return Collections.emptyList();
		}
		return context.selectStorageByCodes(codes);
	}

	@Override
	public <T> void createGoodsOrder(T t) {

	}
}

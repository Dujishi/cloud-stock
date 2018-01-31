package com.xiaoka.cloud.stock.client.business.core;

import com.xiaoka.cloud.stock.client.business.core.rongyi.strategy.RongyiDataStrategy;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouze
 * @date 2018/1/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ErpFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErpFactory.class);

	public static ErpStrategyContext instance(String flag) {
		if (StringUtils.isBlank(flag)) {
			LOGGER.info("策略标识为空，不予执行");
			return null;
		}

		switch (flag) {
			case "RY":
				return new ErpStrategyContext(new RongyiDataStrategy());
			default:
				throw new UnsupportedOperationException("无该策略行为");
		}
	}

}

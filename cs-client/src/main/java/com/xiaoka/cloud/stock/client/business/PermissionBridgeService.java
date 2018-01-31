package com.xiaoka.cloud.stock.client.business;

import com.xiaoka.cloud.stock.client.business.dto.StrategyDto;

/**
 * 登陆桥接服务
 *
 * @author zhouze
 * @date 2018/1/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface PermissionBridgeService {

	/**
	 * 注册信息
	 *
	 * @return
	 */
	boolean registerStrategy(StrategyDto strategyDto);

	/**
	 * 可以重新注册
	 *
	 * @return
	 */
	boolean reRegister();

	/**
	 * 获取采集策略
	 *
	 * @return
	 */
	StrategyDto getStrategy();

}

package com.xiaoka.cloud.stock.service.epc;

import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2018/1/15 14:19
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface EpcAggregateService {

	/**
	 * 统计正时epc今日总调用次数
	 */
	long aggregateSuperEpcByToday();

	/**
	 * 统计正时epc各接口调用次数
	 */
	Map<String, Long> aggregateDetailSuperEpcByToday();

	/**
	 * 统计零零汽epc今日帐号调用次数
	 */
	Map<Integer, Long> aggregateZeroEpcByToday();
}

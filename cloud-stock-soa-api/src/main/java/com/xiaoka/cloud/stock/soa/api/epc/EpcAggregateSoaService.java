package com.xiaoka.cloud.stock.soa.api.epc;

import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2018/1/19 15:01
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface EpcAggregateSoaService {

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

package com.xiaoka.cloud.stock.soa.api.test;

/**
 * @author zhouze
 * @date 2017/11/27
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ErpNatureTestSoaService {

	/**
	 * 测试
	 */
	void testInsertCount();

	void doPlanAThreads(Integer init);

	void doPlanBThreads(Integer init);

	Integer testStockSlave();

}

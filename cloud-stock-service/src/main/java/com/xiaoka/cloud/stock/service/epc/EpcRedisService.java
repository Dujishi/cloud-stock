package com.xiaoka.cloud.stock.service.epc;

/**
 * Do something
 *
 * @author gancao 2017/11/28 10:21
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface EpcRedisService {

	boolean isFilterRepo();

	boolean isFilterEpc();

	String openRepoFilter();

	String openSuperEpcFilter();

	void closeFilter();
}

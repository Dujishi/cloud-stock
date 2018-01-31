package com.xiaoka.cloud.stock.soa.api.epc;

/**
 * Do something
 *
 * @author gancao 2017/12/2 9:46
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface EpcQueryRedisSoaService {

	String openRepoFilter();

	String openSuperEpcFilter();

	void closeFilter();
}

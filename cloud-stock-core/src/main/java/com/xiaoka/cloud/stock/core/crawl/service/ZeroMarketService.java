package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroMarketEntity;

/**
 * for car brand
 * ZeroMarketService
 *
 * @author zhouze
 */
public interface ZeroMarketService {
	List<ZeroMarketEntity> getListByAnd(ZeroMarketEntity zeroMarket);

	int delete(ZeroMarketEntity zeroMarket);

	void insert(ZeroMarketEntity zeroMarket);

	int updateBySelective(ZeroMarketEntity zeroMarket);

}

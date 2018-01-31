package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroPartPriceEntity;

/**
 * for car brand
 * ZeroPartPriceService
 *
 * @author zhouze
 */
public interface ZeroPartPriceService {
	List<ZeroPartPriceEntity> getListByAnd(ZeroPartPriceEntity zeroPartPrice);

	int delete(ZeroPartPriceEntity zeroPartPrice);

	void insert(ZeroPartPriceEntity zeroPartPrice);

	int updateBySelective(ZeroPartPriceEntity zeroPartPrice);

	List<ZeroPartPriceEntity> selectByConditions(List<ZeroPartPriceEntity> subPartPrices);

	void insertList(List<ZeroPartPriceEntity> insertEntities);

	void updateList(List<ZeroPartPriceEntity> updateEntities);
}

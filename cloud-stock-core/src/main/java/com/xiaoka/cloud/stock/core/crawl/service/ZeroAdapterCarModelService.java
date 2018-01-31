package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAdapterCarModelEntity;

/**
 * for car brand
 * ZeroAdapterCarModelService
 *
 * @author zhouze
 */
public interface ZeroAdapterCarModelService {
	/**
	 * @param zeroAdapterCarModel
	 * @return
	 */
	List<ZeroAdapterCarModelEntity> getListByAnd(ZeroAdapterCarModelEntity zeroAdapterCarModel);

	/**
	 * @param zeroAdapterCarModel
	 * @return
	 */
	int delete(ZeroAdapterCarModelEntity zeroAdapterCarModel);

	/**
	 * @param zeroAdapterCarModel
	 */
	void insert(ZeroAdapterCarModelEntity zeroAdapterCarModel);

	/**
	 * @param zeroAdapterCarModel
	 * @return
	 */
	int updateBySelective(ZeroAdapterCarModelEntity zeroAdapterCarModel);

	List<ZeroAdapterCarModelEntity> selectByConditions(List<ZeroAdapterCarModelEntity> subPartPrices);

	void insertList(List<ZeroAdapterCarModelEntity> insertEntities);

	void updateList(List<ZeroAdapterCarModelEntity> updateEntities);
}

package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelEntity;

/**
 * for car brand
 * ZeroCarModelService
 *
 * @author zhouze
 */
public interface ZeroCarModelService {
	List<ZeroCarModelEntity> getListByAnd(ZeroCarModelEntity zeroCarModel);

	int delete(ZeroCarModelEntity zeroCarModel);

	void insert(ZeroCarModelEntity zeroCarModel);

	int updateBySelective(ZeroCarModelEntity zeroCarModel);

	/**
	 * 根据车型和品牌查询
	 *
	 * @param carModelParams
	 * @return
	 */
	List<ZeroCarModelEntity> selectByCarModels(List<ZeroCarModelEntity> carModelParams);

	/**
	 * 添加列表
	 *
	 * @param insertEntities
	 */
	void insertList(List<ZeroCarModelEntity> insertEntities);
}

package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroBrandEntity;

/**
 * for car brand
 * ZeroBrandService
 *
 * @author zhouze
 */
public interface ZeroBrandService {
	/**
	 * @param zeroBrand
	 * @return
	 */
	List<ZeroBrandEntity> getListByAnd(ZeroBrandEntity zeroBrand);

	/**
	 * @param zeroBrand
	 * @return
	 */
	int delete(ZeroBrandEntity zeroBrand);

	/**
	 * @param zeroBrand
	 */
	void insert(ZeroBrandEntity zeroBrand);

	/**
	 * @param zeroBrand
	 * @return
	 */
	int updateBySelective(ZeroBrandEntity zeroBrand);

	/**
	 * 根据品牌查询列表
	 *
	 * @param brands
	 * @return
	 */
	List<ZeroBrandEntity> selectByBrands(List<String> brands);

	/**
	 * 插入列表
	 *
	 * @param insertEntities
	 */
	void insertList(List<ZeroBrandEntity> insertEntities);
}

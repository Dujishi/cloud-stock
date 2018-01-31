package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroPartInfoEntity;

/**
 * for car brand
 * ZeroPartInfoService
 *
 * @author zhouze
 */
public interface ZeroPartInfoService {
	List<ZeroPartInfoEntity> getListByAnd(ZeroPartInfoEntity zeroPartInfo);

	int delete(ZeroPartInfoEntity zeroPartInfo);

	void insert(ZeroPartInfoEntity zeroPartInfo);

	int updateBySelective(ZeroPartInfoEntity zeroPartInfo);

	/**
	 * 根据列表查询
	 * pid、brand作为条件
	 *
	 * @param subPartInfos
	 * @return
	 */
	List<ZeroPartInfoEntity> selectByConditions(List<ZeroPartInfoEntity> subPartInfos);

	/**
	 * batch insert
	 *
	 * @param insertEntities
	 */
	void insertList(List<ZeroPartInfoEntity> insertEntities);

	void updateList(List<ZeroPartInfoEntity> updateEntities);
}

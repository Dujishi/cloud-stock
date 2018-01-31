package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupPartsEntity;

/**
 * for car brand
 * ZeroSubGroupPartsService
 *
 * @author zhouze
 */
public interface ZeroSubGroupPartsService {
	List<ZeroSubGroupPartsEntity> getListByAnd(ZeroSubGroupPartsEntity zeroSubGroupParts);

	int delete(ZeroSubGroupPartsEntity zeroSubGroupParts);

	void insert(ZeroSubGroupPartsEntity zeroSubGroupParts);

	int updateBySelective(ZeroSubGroupPartsEntity zeroSubGroupParts);

	/**
	 * 根据条件查询
	 * groupId,subGroupId,pid
	 *
	 * @param subGroupParts
	 * @return
	 */
	List<ZeroSubGroupPartsEntity> selectByConditions(List<ZeroSubGroupPartsEntity> subGroupParts);

	/**
	 * batch insert
	 *
	 * @param insertEntities
	 */
	void insertList(List<ZeroSubGroupPartsEntity> insertEntities);
}

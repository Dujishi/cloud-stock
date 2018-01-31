package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupEntity;

/**
 * for car brand
 * ZeroSubGroupService
 *
 * @author zhouze
 */
public interface ZeroSubGroupService {
	List<ZeroSubGroupEntity> getListByAnd(ZeroSubGroupEntity zeroSubGroup);

	int delete(ZeroSubGroupEntity zeroSubGroup);

	void insert(ZeroSubGroupEntity zeroSubGroup);

	int updateBySelective(ZeroSubGroupEntity zeroSubGroup);

	/**
	 * 查询是否有已存在的子组信息
	 *
	 * @param subGroupEntities
	 * @return
	 */
	List<ZeroSubGroupEntity> selectHasExistedSubGroup(List<ZeroSubGroupEntity> subGroupEntities);

	/**
	 * 插入列表
	 *
	 * @param insertSubGroups
	 */
	void insertList(List<ZeroSubGroupEntity> insertSubGroups);
}

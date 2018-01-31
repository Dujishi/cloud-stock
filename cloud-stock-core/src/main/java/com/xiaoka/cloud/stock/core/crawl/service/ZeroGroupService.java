package com.xiaoka.cloud.stock.core.crawl.service;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroGroupEntity;

import java.util.List;

/**
 * for car brand
 * ZeroGroupService
 *
 * @author zhouze
 */
public interface ZeroGroupService {
	List<ZeroGroupEntity> getListByAnd(ZeroGroupEntity zeroGroup);

	int delete(ZeroGroupEntity zeroGroup);

	Integer insert(ZeroGroupEntity zeroGroup);

	int updateBySelective(ZeroGroupEntity zeroGroup);

	List<ZeroGroupEntity> selectByGroupName(String groupName, Integer cId);

	List<ZeroGroupEntity> selectByList(List<ZeroGroupEntity> groupParams);
}

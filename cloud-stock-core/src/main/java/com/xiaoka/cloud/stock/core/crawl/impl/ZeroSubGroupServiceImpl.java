package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.List;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroSubGroupRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroSubGroupService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroSubGroupService
 *
 * @author zhouze
 */
@Service
public class ZeroSubGroupServiceImpl implements ZeroSubGroupService {
	private Logger logger = LoggerFactory.getLogger(ZeroSubGroupService.class);
	@Resource
	private ZeroSubGroupRepo repo;

	@Override
	public void insert(ZeroSubGroupEntity zeroSubGroup) {
		repo.insert(zeroSubGroup);
	}


	@Override
	public int delete(ZeroSubGroupEntity zeroSubGroup) {
		int result;
		if ((result = repo.delete(zeroSubGroup)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroSubGroupEntity zeroSubGroup) {
		int result;
		if ((result = repo.updateBySelective(zeroSubGroup)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroSubGroupEntity> selectHasExistedSubGroup(List<ZeroSubGroupEntity> subGroupEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(subGroupEntities), "零件组参数不能为空");

		return repo.selectHasExistedSubGroup(subGroupEntities);
	}

	@Override
	public void insertList(List<ZeroSubGroupEntity> insertSubGroups) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(insertSubGroups),"insertSubGroups参数为空");

		repo.insertList(insertSubGroups);
	}


	@Override
	public List<ZeroSubGroupEntity> getListByAnd(ZeroSubGroupEntity zeroSubGroup) {
		return repo.select(zeroSubGroup);
	}


}

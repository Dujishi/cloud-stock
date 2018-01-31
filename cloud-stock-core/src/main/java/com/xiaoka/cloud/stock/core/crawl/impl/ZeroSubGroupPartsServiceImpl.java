package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroSubGroupPartsRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupPartsEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroSubGroupPartsService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroSubGroupPartsService
 *
 * @author zhouze
 */
@Service
public class ZeroSubGroupPartsServiceImpl implements ZeroSubGroupPartsService {
	private Logger logger = LoggerFactory.getLogger(ZeroSubGroupPartsService.class);
	@Resource
	private ZeroSubGroupPartsRepo repo;

	@Override
	public void insert(ZeroSubGroupPartsEntity zeroSubGroupParts) {
		repo.insert(zeroSubGroupParts);
	}


	@Override
	public int delete(ZeroSubGroupPartsEntity zeroSubGroupParts) {
		int result;
		if ((result = repo.delete(zeroSubGroupParts)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroSubGroupPartsEntity zeroSubGroupParts) {
		int result;
		if ((result = repo.updateBySelective(zeroSubGroupParts)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroSubGroupPartsEntity> selectByConditions(List<ZeroSubGroupPartsEntity> subGroupParts) {
		if (CollectionUtils.isEmpty(subGroupParts)){
			return Collections.emptyList();
		}

		return repo.selectByConditions(subGroupParts);
	}

	@Override
	public void insertList(List<ZeroSubGroupPartsEntity> insertEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(insertEntities),"insertEntities param is null or empty");

		repo.insertList(insertEntities);
	}


	@Override
	public List<ZeroSubGroupPartsEntity> getListByAnd(ZeroSubGroupPartsEntity zeroSubGroupParts) {
		return repo.select(zeroSubGroupParts);
	}


}

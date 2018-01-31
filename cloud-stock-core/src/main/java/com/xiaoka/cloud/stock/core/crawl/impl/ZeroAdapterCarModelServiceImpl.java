package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroAdapterCarModelRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAdapterCarModelEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroAdapterCarModelService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroAdapterCarModelService
 *
 * @author zhouze
 */
@Service
public class ZeroAdapterCarModelServiceImpl implements ZeroAdapterCarModelService {
	private Logger logger = LoggerFactory.getLogger(ZeroAdapterCarModelService.class);
	@Resource
	private ZeroAdapterCarModelRepo repo;

	@Override
	public void insert(ZeroAdapterCarModelEntity zeroAdapterCarModel) {
		repo.insert(zeroAdapterCarModel);
	}


	@Override
	public int delete(ZeroAdapterCarModelEntity zeroAdapterCarModel) {
		int result;
		if ((result = repo.delete(zeroAdapterCarModel)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroAdapterCarModelEntity zeroAdapterCarModel) {
		int result;
		if ((result = repo.updateBySelective(zeroAdapterCarModel)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroAdapterCarModelEntity> selectByConditions(List<ZeroAdapterCarModelEntity> subPartPrices) {
		if (CollectionUtils.isEmpty(subPartPrices)){
			return Collections.emptyList();
		}

		return repo.selectByConditions(subPartPrices);
	}

	@Override
	public void insertList(List<ZeroAdapterCarModelEntity> insertEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(insertEntities));

		repo.insertList(insertEntities);
	}

	@Override
	public void updateList(List<ZeroAdapterCarModelEntity> updateEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(updateEntities));

		repo.updateList(updateEntities);
	}


	@Override
	public List<ZeroAdapterCarModelEntity> getListByAnd(ZeroAdapterCarModelEntity zeroAdapterCarModel) {
		return repo.select(zeroAdapterCarModel);
	}


}

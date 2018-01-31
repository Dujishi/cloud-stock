package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroCarModelRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroCarModelService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroCarModelService
 *
 * @author zhouze
 */
@Service
public class ZeroCarModelServiceImpl implements ZeroCarModelService {
	private Logger logger = LoggerFactory.getLogger(ZeroCarModelService.class);
	@Resource
	private ZeroCarModelRepo repo;

	@Override
	public void insert(ZeroCarModelEntity zeroCarModel) {
		repo.insert(zeroCarModel);
	}


	@Override
	public int delete(ZeroCarModelEntity zeroCarModel) {
		int result;
		if ((result = repo.delete(zeroCarModel)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroCarModelEntity zeroCarModel) {
		int result;
		if ((result = repo.updateBySelective(zeroCarModel)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroCarModelEntity> getListByAnd(ZeroCarModelEntity zeroCarModel) {
		return repo.select(zeroCarModel);
	}

	@Override
	public List<ZeroCarModelEntity> selectByCarModels(List<ZeroCarModelEntity> carModelParams) {
		if (CollectionUtils.isEmpty(carModelParams)) {
			return Collections.emptyList();
		}

		return repo.selectByCarModels(carModelParams);
	}

	@Override
	public void insertList(List<ZeroCarModelEntity> insertEntities) {
		if (CollectionUtils.isEmpty(insertEntities)) {
			logger.info("插入列表参数insertEntities为空");
			return;
		}
		repo.insertList(insertEntities);
	}

}

package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroPartPriceRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroPartPriceEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroPartPriceService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroPartPriceService
 *
 * @author zhouze
 */
@Service
public class ZeroPartPriceServiceImpl implements ZeroPartPriceService {
	private Logger logger = LoggerFactory.getLogger(ZeroPartPriceService.class);
	@Resource
	private ZeroPartPriceRepo repo;

	@Override
	public void insert(ZeroPartPriceEntity zeroPartPrice) {
		repo.insert(zeroPartPrice);
	}


	@Override
	public int delete(ZeroPartPriceEntity zeroPartPrice) {
		int result;
		if ((result = repo.delete(zeroPartPrice)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroPartPriceEntity zeroPartPrice) {
		int result;
		if ((result = repo.updateBySelective(zeroPartPrice)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroPartPriceEntity> selectByConditions(List<ZeroPartPriceEntity> subPartPrices) {
		if (CollectionUtils.isEmpty(subPartPrices)){
			return Collections.emptyList();
		}

		return repo.selectByConditions(subPartPrices);
	}

	@Override
	public void insertList(List<ZeroPartPriceEntity> insertEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(insertEntities));

		repo.insertList(insertEntities);
	}

	@Override
	public void updateList(List<ZeroPartPriceEntity> updateEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(updateEntities));

		repo.updateList(updateEntities);
	}


	@Override
	public List<ZeroPartPriceEntity> getListByAnd(ZeroPartPriceEntity zeroPartPrice) {
		return repo.select(zeroPartPrice);
	}


}

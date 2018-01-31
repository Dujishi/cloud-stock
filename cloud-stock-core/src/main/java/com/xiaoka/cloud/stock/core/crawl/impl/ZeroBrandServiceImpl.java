package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.ZeroBrandRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroBrandEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroBrandService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroBrandService
 *
 * @author zhouze
 */
@Service
public class ZeroBrandServiceImpl implements ZeroBrandService {
	private Logger logger = LoggerFactory.getLogger(ZeroBrandService.class);
	@Resource
	private ZeroBrandRepo repo;

	@Override
	public void insert(ZeroBrandEntity zeroBrand) {
		repo.insert(zeroBrand);
	}


	@Override
	public int delete(ZeroBrandEntity zeroBrand) {
		int result;
		if ((result = repo.delete(zeroBrand)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroBrandEntity zeroBrand) {
		int result;
		if ((result = repo.updateBySelective(zeroBrand)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroBrandEntity> selectByBrands(List<String> brands) {
		if (CollectionUtils.isEmpty(brands)) {
			return Collections.emptyList();
		}
		return repo.selectByBrands(brands);
	}

	@Override
	public void insertList(List<ZeroBrandEntity> insertEntities) {
		if (CollectionUtils.isEmpty(insertEntities)){
			logger.info("参数insertEntities为空");
			return;
		}
		repo.insertList(insertEntities);
	}


	@Override
	public List<ZeroBrandEntity> getListByAnd(ZeroBrandEntity zeroBrand) {
		return repo.select(zeroBrand);
	}


}

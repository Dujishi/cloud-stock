package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroPartInfoRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroPartInfoEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroPartInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroPartInfoService
 *
 * @author zhouze
 */
@Service
public class ZeroPartInfoServiceImpl implements ZeroPartInfoService {
	private Logger logger = LoggerFactory.getLogger(ZeroPartInfoService.class);
	@Resource
	private ZeroPartInfoRepo repo;

	@Override
	public void insert(ZeroPartInfoEntity zeroPartInfo) {
		repo.insert(zeroPartInfo);
	}


	@Override
	public int delete(ZeroPartInfoEntity zeroPartInfo) {
		int result;
		if ((result = repo.delete(zeroPartInfo)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroPartInfoEntity zeroPartInfo) {
		int result;
		if ((result = repo.updateBySelective(zeroPartInfo)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroPartInfoEntity> selectByConditions(List<ZeroPartInfoEntity> subPartInfos) {
		if (CollectionUtils.isEmpty(subPartInfos)) {
			return Collections.emptyList();
		}

		return repo.selectByConditions(subPartInfos);
	}

	@Override
	public void insertList(List<ZeroPartInfoEntity> insertEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(insertEntities));

		repo.insertList(insertEntities);
	}

	@Override
	public void updateList(List<ZeroPartInfoEntity> updateEntities) {

		Preconditions.checkArgument(CollectionUtils.isNotEmpty(updateEntities));

		repo.updateList(updateEntities);
	}


	@Override
	public List<ZeroPartInfoEntity> getListByAnd(ZeroPartInfoEntity zeroPartInfo) {
		return repo.select(zeroPartInfo);
	}


}

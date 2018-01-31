package com.xiaoka.cloud.stock.core.crawl.impl;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroGroupRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroGroupEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroGroupService;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * for car brand
 * ZeroGroupService
 *
 * @author zhouze
 */
@Service
public class ZeroGroupServiceImpl implements ZeroGroupService {
	private Logger logger = LoggerFactory.getLogger(ZeroGroupService.class);
	@Resource
	private ZeroGroupRepo repo;

	@Override
	public Integer insert(ZeroGroupEntity zeroGroup) {
		return repo.insert(zeroGroup);
	}


	@Override
	public int delete(ZeroGroupEntity zeroGroup) {
		int result;
		if ((result = repo.delete(zeroGroup)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroGroupEntity zeroGroup) {
		int result;
		if ((result = repo.updateBySelective(zeroGroup)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroGroupEntity> selectByGroupName(String groupName, Integer cId) {
		Preconditions.checkArgument(StringUtils.isNotBlank(groupName), "参数groupName为空");
		Preconditions.checkArgument(Objects.nonNull(cId), "参数cId为空");
		return repo.selectByGroupName(groupName,cId);
	}

	@Override
	public List<ZeroGroupEntity> selectByList(List<ZeroGroupEntity> groupParams) {
		if (CollectionUtils.isEmpty(groupParams)){
			return Collections.emptyList();
		}

		return repo.selectByList(groupParams);
	}


	@Override
	public List<ZeroGroupEntity> getListByAnd(ZeroGroupEntity zeroGroup) {
		return repo.select(zeroGroup);
	}


}

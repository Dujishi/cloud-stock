package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.crawl.dto.ReplaceImgUrlDto;
import com.xiaoka.cloud.stock.core.crawl.repo.ZeroSubGroupImgRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupImgEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroSubGroupImgService;
import com.xiaoka.freework.help.page.PageList;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroSubGroupImgService
 *
 * @author zhouze
 */
@Service
public class ZeroSubGroupImgServiceImpl implements ZeroSubGroupImgService {
	private Logger logger = LoggerFactory.getLogger(ZeroSubGroupImgService.class);
	@Resource
	private ZeroSubGroupImgRepo repo;

	@Override
	public void insert(ZeroSubGroupImgEntity zeroSubGroupImg) {
		repo.insert(zeroSubGroupImg);
	}


	@Override
	public int delete(ZeroSubGroupImgEntity zeroSubGroupImg) {
		int result;
		if ((result = repo.delete(zeroSubGroupImg)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroSubGroupImgEntity zeroSubGroupImg) {
		int result;
		if ((result = repo.updateBySelective(zeroSubGroupImg)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}

	@Override
	public List<ZeroSubGroupImgEntity> selectByConditions(List<ZeroSubGroupImgEntity> subGroupImgs) {
		if (CollectionUtils.isEmpty(subGroupImgs)) {
			return Collections.emptyList();
		}

		return repo.selectByConditions(subGroupImgs);
	}

	@Override
	public void insertList(List<ZeroSubGroupImgEntity> insertEntities) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(insertEntities), "插入参数insertEntities为空");

		repo.insertList(insertEntities);
	}

	@Override
	public PageList<ZeroSubGroupImgEntity> selectPageByCondition(PageList pageList) {
		Preconditions.checkArgument(Objects.nonNull(pageList), "参数pageList为空");

		Integer count = repo.selectSumCount(pageList);
		pageList.setTotalSize(count);
		if (count <= 0) {
			return pageList;
		}
		List<ZeroSubGroupImgEntity> subGroupImgs = repo.selectPage(pageList);
		pageList.setData(subGroupImgs);
		return pageList;
	}

	@Override
	public void updateImgUrls(List<ReplaceImgUrlDto> list) {
		repo.updateImgUrls(list);
	}


	@Override
	public List<ZeroSubGroupImgEntity> getListByAnd(ZeroSubGroupImgEntity zeroSubGroupImg) {
		return repo.select(zeroSubGroupImg);
	}


}

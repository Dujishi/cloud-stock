package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.ZeroCarYearRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarYearEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroCarYearService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroCarYearService
 *
 * @author zhouze
 */
@Service
public class ZeroCarYearServiceImpl implements ZeroCarYearService {
	private Logger logger = LoggerFactory.getLogger(ZeroCarYearService.class);
	@Resource
	private ZeroCarYearRepo repo;

	@Override
	public void insert(ZeroCarYearEntity zeroCarYear) {
		repo.insert(zeroCarYear);
	}


	@Override
	public int delete(ZeroCarYearEntity zeroCarYear) {
		int result;
		if ((result = repo.delete(zeroCarYear)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroCarYearEntity zeroCarYear) {
		int result;
		if ((result = repo.updateBySelective(zeroCarYear)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public List<ZeroCarYearEntity> getListByAnd(ZeroCarYearEntity zeroCarYear) {
		return repo.select(zeroCarYear);
	}


}

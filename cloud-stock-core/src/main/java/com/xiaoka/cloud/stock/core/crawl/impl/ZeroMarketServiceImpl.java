package com.xiaoka.cloud.stock.core.crawl.impl;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.ZeroMarketRepo;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroMarketEntity;
import com.xiaoka.cloud.stock.core.crawl.service.ZeroMarketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import com.xiaoka.freework.help.api.ApiException;


/**
 * for car brand
 * ZeroMarketService
 *
 * @author zhouze
 */
@Service
public class ZeroMarketServiceImpl implements ZeroMarketService {
	private Logger logger = LoggerFactory.getLogger(ZeroMarketService.class);
	@Resource
	private ZeroMarketRepo repo;

	@Override
	public void insert(ZeroMarketEntity zeroMarket) {
		repo.insert(zeroMarket);
	}


	@Override
	public int delete(ZeroMarketEntity zeroMarket) {
		int result;
		if ((result = repo.delete(zeroMarket)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public int updateBySelective(ZeroMarketEntity zeroMarket) {
		int result;
		if ((result = repo.updateBySelective(zeroMarket)) != 1) {
			throw new ApiException("-1", "TODO");
		}
		return result;
	}


	@Override
	public List<ZeroMarketEntity> getListByAnd(ZeroMarketEntity zeroMarket) {
		return repo.select(zeroMarket);
	}


}

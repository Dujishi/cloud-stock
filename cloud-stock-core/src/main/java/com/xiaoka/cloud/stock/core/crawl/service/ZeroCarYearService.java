package com.xiaoka.cloud.stock.core.crawl.service;

import java.util.List;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarYearEntity;

/**
 * for car brand
 * ZeroCarYearService
 *
 * @author zhouze
 */
public interface ZeroCarYearService {
	List<ZeroCarYearEntity> getListByAnd(ZeroCarYearEntity zeroCarYear);

	int delete(ZeroCarYearEntity zeroCarYear);

	void insert(ZeroCarYearEntity zeroCarYear);

	int updateBySelective(ZeroCarYearEntity zeroCarYear);

}

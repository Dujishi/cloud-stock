package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarYearEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * ZeroCarYearRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroCarYearRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroCarYearEntity> select(ZeroCarYearEntity param) {
		return commonDao.mapper(ZeroCarYearEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroCarYearEntity param) {
		return commonDao.mapper(ZeroCarYearEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroCarYearEntity param) {
		commonDao.mapper(ZeroCarYearEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroCarYearEntity param) {
		return commonDao.mapper(ZeroCarYearEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

}

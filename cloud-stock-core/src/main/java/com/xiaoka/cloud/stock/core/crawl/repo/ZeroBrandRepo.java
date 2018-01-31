package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroBrandEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * ZeroBrandRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroBrandRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroBrandEntity> select(ZeroBrandEntity param) {
		return commonDao.mapper(ZeroBrandEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroBrandEntity param) {
		return commonDao.mapper(ZeroBrandEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroBrandEntity param) {
		commonDao.mapper(ZeroBrandEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroBrandEntity param) {
		return commonDao.mapper(ZeroBrandEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroBrandEntity> selectByBrands(List<String> brands) {
		return commonDao.mapper(ZeroBrandEntity.class).source(MASTER).sql("selectByBrands").session()
				.selectList(brands);
	}

	public Integer insertList(List<ZeroBrandEntity> insertEntities) {
		return commonDao.mapper(ZeroBrandEntity.class).source(MASTER)
				.sql("insertList").session()
				.insert(insertEntities);
	}
}

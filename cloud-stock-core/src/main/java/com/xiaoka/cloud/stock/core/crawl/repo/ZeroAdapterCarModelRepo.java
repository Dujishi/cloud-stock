package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAdapterCarModelEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * ZeroAdapterCarModelRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroAdapterCarModelRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroAdapterCarModelEntity> select(ZeroAdapterCarModelEntity param) {
		return commonDao.mapper(ZeroAdapterCarModelEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroAdapterCarModelEntity param) {
		return commonDao.mapper(ZeroAdapterCarModelEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroAdapterCarModelEntity param) {
		commonDao.mapper(ZeroAdapterCarModelEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroAdapterCarModelEntity param) {
		return commonDao.mapper(ZeroAdapterCarModelEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroAdapterCarModelEntity> selectByConditions(List<ZeroAdapterCarModelEntity> subPartPrices) {
		return commonDao.mapper(ZeroAdapterCarModelEntity.class).source(MASTER).sql("selectByConditions").session()
				.selectList(subPartPrices);
	}

	public Integer insertList(List<ZeroAdapterCarModelEntity> insertEntities) {
		return commonDao.mapper(ZeroAdapterCarModelEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertEntities);
	}

	public Integer updateList(List<ZeroAdapterCarModelEntity> updateEntities) {
		return commonDao.mapper(ZeroAdapterCarModelEntity.class).source(MASTER).sql("updateList").session()
				.update(updateEntities);
	}
}

package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 *
 * ZeroCarModelRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroCarModelRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroCarModelEntity> select(ZeroCarModelEntity param) {
		return commonDao.mapper(ZeroCarModelEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroCarModelEntity param) {
		return commonDao.mapper(ZeroCarModelEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroCarModelEntity param) {
		commonDao.mapper(ZeroCarModelEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroCarModelEntity param) {
		return commonDao.mapper(ZeroCarModelEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroCarModelEntity> selectByCarModels(List<ZeroCarModelEntity> list) {
		return commonDao.mapper(ZeroCarModelEntity.class).source(MASTER).sql("selectByCarModels").session()
				.selectList(list);
	}

	public Integer insertList(List<ZeroCarModelEntity> insertEntities) {
		return commonDao.mapper(ZeroCarModelEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertEntities);
	}
}

package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.UserBrandEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * UserBrandRepo
 *
 * @author suqin
 */
@Repository
public class UserBrandRepo {
	@Resource
	private CommonDao commonDao;

	public List<UserBrandEntity> select(UserBrandEntity param) {
		return commonDao.mapper(UserBrandEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(UserBrandEntity param) {
		return commonDao.mapper(UserBrandEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(UserBrandEntity param) {
		commonDao.mapper(UserBrandEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<UserBrandEntity> param) {
		commonDao.mapper(UserBrandEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public int updateBySelective(UserBrandEntity param) {
		return commonDao.mapper(UserBrandEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}
}

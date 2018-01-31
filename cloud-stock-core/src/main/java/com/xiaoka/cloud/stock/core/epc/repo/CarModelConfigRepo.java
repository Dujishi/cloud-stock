package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelConfigEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarModelConfigRepo
 *
 * @author suqin
 */
@Repository
public class CarModelConfigRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarModelConfigEntity> select(CarModelConfigEntity param) {
		return commonDao.mapper(CarModelConfigEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(CarModelConfigEntity param) {
		return commonDao.mapper(CarModelConfigEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarModelConfigEntity param) {
		commonDao.mapper(CarModelConfigEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<CarModelConfigEntity> params) {
		commonDao.mapper(CarModelConfigEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(params);
	}

	public int updateBySelective(CarModelConfigEntity param) {
		return commonDao.mapper(CarModelConfigEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public List<CarModelConfigEntity> selectByModelIds(List<Integer> modelIds) {
		return commonDao.mapper(CarModelConfigEntity.class).source(MASTER).sql("selectByModelIds").session()
				.selectList(modelIds);
	}

	public List<CarModelConfigEntity> selectByModelIdsForSlave(List<Integer> modelIds) {
		return commonDao.mapper(CarModelConfigEntity.class).source(SLAVE).sql("selectByModelIds").session()
		                .selectList(modelIds);
	}
}

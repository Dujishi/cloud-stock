package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelVinEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarModelVinRepo
 *
 * @author suqin
 */
@Repository
public class CarModelVinRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarModelVinEntity> select(CarModelVinEntity param) {
		return commonDao.mapper(CarModelVinEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<CarModelVinEntity> selectByVinModelIdList(List<CarModelVinEntity> param) {
		return commonDao.mapper(CarModelVinEntity.class).source(SLAVE).sql("selectByVinModelIdList").session()
		                .selectList(param);
	}

	public int delete(CarModelVinEntity param) {
		return commonDao.mapper(CarModelVinEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarModelVinEntity param) {
		commonDao.mapper(CarModelVinEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarModelVinEntity param) {
		return commonDao.mapper(CarModelVinEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public int batchInsert(List<CarModelVinEntity> carModelVinEntityList) {
		return commonDao.mapper(CarModelVinEntity.class).source(MASTER).sql("batchInsert").session()
		                .insert(carModelVinEntityList);
	}


}

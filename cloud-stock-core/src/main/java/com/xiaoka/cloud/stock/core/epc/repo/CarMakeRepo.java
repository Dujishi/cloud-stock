package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarMakeEntity;
import com.xiaoka.freework.cache.annotation.ServiceCache;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarMakeRepo
 *
 * @author suqin
 */
@Repository
public class CarMakeRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarMakeEntity> select(CarMakeEntity param) {
		return commonDao.mapper(CarMakeEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<CarMakeEntity> selectByNames(List<String> param) {
		return commonDao.mapper(CarMakeEntity.class).source(SLAVE).sql("selectByNames").session()
		                .selectList(param);
	}

	public int delete(CarMakeEntity param) {
		return commonDao.mapper(CarMakeEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarMakeEntity param) {
		commonDao.mapper(CarMakeEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarMakeEntity param) {
		return commonDao.mapper(CarMakeEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public int batchInsert(List<CarMakeEntity> carMakeEntityList) {
		return commonDao.mapper(CarMakeEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(carMakeEntityList);
	}

	@ServiceCache(expire = 60 * 20)
	public List<CarMakeEntity> selectAllCarMakes() {
		return commonDao.mapper(CarMakeEntity.class).source(SLAVE).sql("selectAllCarMakes").session()
				.selectList();
	}
}

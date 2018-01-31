package com.xiaoka.cloud.stock.core.region;

import com.xiaoka.cloud.stock.core.region.entity.CarCityEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CarCityRepo
 *
 * @author auto-generate
 */
@Repository
public class CarCityRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarCityEntity> select(CarCityEntity param) {
		return commonDao.mapper(CarCityEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(CarCityEntity param) {
		return commonDao.mapper(CarCityEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarCityEntity param) {
		commonDao.mapper(CarCityEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarCityEntity param) {
		return commonDao.mapper(CarCityEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

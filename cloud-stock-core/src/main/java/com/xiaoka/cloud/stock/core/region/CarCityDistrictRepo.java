package com.xiaoka.cloud.stock.core.region;

import com.xiaoka.cloud.stock.core.region.entity.CarCityDistrictEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CarCityDistrictRepo
 *
 * @author auto-generate
 */
@Repository
public class CarCityDistrictRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarCityDistrictEntity> select(CarCityDistrictEntity param) {
		return commonDao.mapper(CarCityDistrictEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(CarCityDistrictEntity param) {
		return commonDao.mapper(CarCityDistrictEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarCityDistrictEntity param) {
		commonDao.mapper(CarCityDistrictEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarCityDistrictEntity param) {
		return commonDao.mapper(CarCityDistrictEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

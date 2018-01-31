package com.xiaoka.cloud.stock.core.region;

import com.xiaoka.cloud.stock.core.region.entity.CarProvinceEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CarProvinceRepo
 *
 * @author auto-generate
 */
@Repository
public class CarProvinceRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarProvinceEntity> select(CarProvinceEntity param) {
		return commonDao.mapper(CarProvinceEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(CarProvinceEntity param) {
		return commonDao.mapper(CarProvinceEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarProvinceEntity param) {
		commonDao.mapper(CarProvinceEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarProvinceEntity param) {
		return commonDao.mapper(CarProvinceEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

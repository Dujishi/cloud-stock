package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarBrandEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarSeriesEntity;
import com.xiaoka.freework.cache.annotation.ServiceCache;
import com.xiaoka.freework.container.dao.CommonDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarSeriesRepo
 *
 * @author suqin
 */
@Repository
public class CarSeriesRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarSeriesEntity> select(CarSeriesEntity param) {
		return commonDao.mapper(CarSeriesEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(CarSeriesEntity param) {
		return commonDao.mapper(CarSeriesEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(CarSeriesEntity param) {
		commonDao.mapper(CarSeriesEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int batchInsert(List<CarSeriesEntity> params) {
		return commonDao.mapper(CarSeriesEntity.class).source(MASTER).sql("batchInsert").session()
				.insert(params);
	}

	public int updateBySelective(CarSeriesEntity param) {
		return commonDao.mapper(CarSeriesEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	@ServiceCache(expire = 60 * 20)
	public List<CarSeriesEntity> selectAllCarSeries() {
		List<CarSeriesEntity> carSeriesEntities = commonDao.mapper(CarSeriesEntity.class).source(SLAVE).sql("selectAllCarSeries").session()
				.selectList();
		if (CollectionUtils.isEmpty(carSeriesEntities)) {
			return Collections.emptyList();
		}
		return carSeriesEntities;
	}

	public List<CarSeriesEntity> selectDistinctSeriesByBrand(Integer brandId) {
		return commonDao.mapper(CarSeriesEntity.class).source(SLAVE).sql("selectDistinctSeriesByBrand").session()
		                       .selectList(brandId);
	}
}

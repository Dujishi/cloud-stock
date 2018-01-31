package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardAssemblyEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarModelCategoryRepo
 *
 * @author suqin
 */
@Repository
public class CarModelCategoryRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarModelCategoryEntity> select(CarModelCategoryEntity param) {
		return commonDao.mapper(CarModelCategoryEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(CarModelCategoryEntity param) {
		return commonDao.mapper(CarModelCategoryEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarModelCategoryEntity param) {
		commonDao.mapper(CarModelCategoryEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<CarModelCategoryEntity> param) {
		commonDao.mapper(CarModelCategoryEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}


	public int updateBySelective(CarModelCategoryEntity param) {
		return commonDao.mapper(CarModelCategoryEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

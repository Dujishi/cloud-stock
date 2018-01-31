package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardAssemblyEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardSubAssemblyEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * StandardAssemblyRepo
 *
 * @author suqin
 */
@Repository
public class StandardAssemblyRepo {
	@Resource
	private CommonDao commonDao;

	public List<StandardAssemblyEntity> select(StandardAssemblyEntity param) {
		return commonDao.mapper(StandardAssemblyEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<Integer> selectIdByIds(List<Integer> param) {
		return commonDao.mapper(StandardAssemblyEntity.class).source(SLAVE).sql("selectIdByIds").session()
		                .selectList(param);
	}

	public List<StandardAssemblyEntity> selectByIds(List<Integer> param) {
		return commonDao.mapper(StandardAssemblyEntity.class).source(SLAVE).sql("selectByIds").session()
		                .selectList(param);
	}

	public int delete(StandardAssemblyEntity param) {
		return commonDao.mapper(StandardAssemblyEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(StandardAssemblyEntity param) {
		commonDao.mapper(StandardAssemblyEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<StandardAssemblyEntity> param) {
		commonDao.mapper(StandardAssemblyEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public int updateBySelective(StandardAssemblyEntity param) {
		return commonDao.mapper(StandardAssemblyEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

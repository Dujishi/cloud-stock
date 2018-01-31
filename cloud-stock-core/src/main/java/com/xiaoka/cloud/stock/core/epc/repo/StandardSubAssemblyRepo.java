package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardSubAssemblyEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * StandardSubAssemblyRepo
 *
 * @author suqin
 */
@Repository
public class StandardSubAssemblyRepo {
	@Resource
	private CommonDao commonDao;

	public List<StandardSubAssemblyEntity> select(StandardSubAssemblyEntity param) {
		return commonDao.mapper(StandardSubAssemblyEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<Integer> selectIdByIds(List<Integer> param) {
		return commonDao.mapper(StandardSubAssemblyEntity.class).source(SLAVE).sql("selectIdByIds").session()
		                .selectList(param);
	}

	public List<Integer> selectSusAssemblyIdByCategoryId(Integer categoryId){
		return commonDao.mapper(StandardSubAssemblyEntity.class).source(SLAVE).sql("selectSusAssemblyIdByCategoryId").session()
		                .selectList(categoryId);
	}

	public int delete(StandardSubAssemblyEntity param) {
		return commonDao.mapper(StandardSubAssemblyEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(StandardSubAssemblyEntity param) {
		commonDao.mapper(StandardSubAssemblyEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<StandardSubAssemblyEntity> param) {
		commonDao.mapper(StandardSubAssemblyEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public int updateBySelective(StandardSubAssemblyEntity param) {
		return commonDao.mapper(StandardSubAssemblyEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

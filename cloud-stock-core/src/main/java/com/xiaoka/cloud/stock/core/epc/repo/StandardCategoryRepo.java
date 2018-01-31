package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardPartEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * StandardCategoryRepo
 *
 * @author suqin
 */
@Repository
public class StandardCategoryRepo {
	@Resource
	private CommonDao commonDao;

	public List<StandardCategoryEntity> select(StandardCategoryEntity param) {
		return commonDao.mapper(StandardCategoryEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<StandardCategoryEntity> selectAll() {
		return commonDao.mapper(StandardCategoryEntity.class).source(SLAVE).sql("selectAll").session()
		                .selectList();
	}

	public List<String> selectIdByNames(List<String> param) {
		return commonDao.mapper(StandardCategoryEntity.class).source(SLAVE).sql("selectIdByNames").session()
		                .selectList(param);
	}

	public int delete(StandardCategoryEntity param) {
		return commonDao.mapper(StandardCategoryEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(StandardCategoryEntity param) {
		commonDao.mapper(StandardCategoryEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<StandardCategoryEntity> param) {
		commonDao.mapper(StandardCategoryEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public boolean checkIsExist(StandardCategoryEntity param) {
		Integer i = commonDao.mapper(StandardCategoryEntity.class).source(SLAVE).sql("checkIsExist").session()
		                .selectOne(param);
		return i != null && i == 1 ? true : false;
	}

	public int updateBySelective(StandardCategoryEntity param) {
		return commonDao.mapper(StandardCategoryEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

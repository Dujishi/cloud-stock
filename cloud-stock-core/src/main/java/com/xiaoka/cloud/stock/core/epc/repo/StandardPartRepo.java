package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardPartEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * StandardPartRepo
 *
 * @author suqin
 */
@Repository
public class StandardPartRepo {
	@Resource
	private CommonDao commonDao;

	public List<StandardPartEntity> select(StandardPartEntity param) {
		return commonDao.mapper(StandardPartEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<Integer> selectIdByIds(List<Integer> param) {
		return commonDao.mapper(StandardPartEntity.class).source(SLAVE).sql("selectIdByIds").session()
		                .selectList(param);
	}

	public int delete(StandardPartEntity param) {
		return commonDao.mapper(StandardPartEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(StandardPartEntity param) {
		commonDao.mapper(StandardPartEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<StandardPartEntity> param) {
		commonDao.mapper(StandardPartEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public int updateBySelective(StandardPartEntity param) {
		return commonDao.mapper(StandardPartEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public List<StandardPartEntity> getPartIdByNames(List<String> partNameList){
		return commonDao.mapper(StandardPartEntity.class).source(SLAVE).sql("getPartIdByNames").session()
		                .selectList(partNameList);
	}

}

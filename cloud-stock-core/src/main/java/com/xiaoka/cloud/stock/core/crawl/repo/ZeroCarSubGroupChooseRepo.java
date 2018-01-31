package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarSubGroupChooseEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car sub group
 * ZeroCarSubGroupChooseRepo
 *
 * @author gancao
 */
@Repository
public class ZeroCarSubGroupChooseRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroCarSubGroupChooseEntity> select(ZeroCarSubGroupChooseEntity param) {
		return commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<ZeroCarSubGroupChooseEntity> batchSelectByAuth(List<String> authList) {
		return commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("batchSelectByAuth").session()
		                .selectList(authList);
	}

	public int getSubGroupChooseCountByCId(Integer cId){
		return commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("getSubGroupChooseCountByCId").session()
		                .selectOne(cId);
	}

	public int getSubGroupCountByCId(Integer cId){
		return commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("getSubGroupCountByCId").session()
		                .selectOne(cId);
	}

	public int delete(ZeroCarSubGroupChooseEntity param) {
		return commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(ZeroCarSubGroupChooseEntity param) {
		commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<ZeroCarSubGroupChooseEntity> entityList) {
		commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(entityList);
	}

	public int updateBySelective(ZeroCarSubGroupChooseEntity param) {
		return commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public void deleteData(){
		commonDao.mapper(ZeroCarSubGroupChooseEntity.class).source(MASTER).sql("deleteData").session()
		         .delete();
	}

}

package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car choose
 * ZeroCarChooseRepo
 *
 * @author gancao
 */
@Repository
public class ZeroCarModelChooseRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroCarModelChooseEntity> select(ZeroCarModelChooseEntity param) {
		return commonDao.mapper(ZeroCarModelChooseEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public int delete(ZeroCarModelChooseEntity param) {
		return commonDao.mapper(ZeroCarModelChooseEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(ZeroCarModelChooseEntity param) {
		commonDao.mapper(ZeroCarModelChooseEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(ZeroCarModelChooseEntity param) {
		return commonDao.mapper(ZeroCarModelChooseEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public void deleteData(){
		commonDao.mapper(ZeroCarModelChooseEntity.class).source(MASTER).sql("deleteData").session()
		         .delete();
	}

}

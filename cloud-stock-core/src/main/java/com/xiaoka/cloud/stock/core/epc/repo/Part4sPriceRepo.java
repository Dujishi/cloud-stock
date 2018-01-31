package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.Part4sPriceEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardAssemblyEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * Part4sPriceRepo
 *
 * @author suqin
 */
@Repository
public class Part4sPriceRepo {
	@Resource
	private CommonDao commonDao;

	public List<Part4sPriceEntity> select(Part4sPriceEntity param) {
		return commonDao.mapper(Part4sPriceEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<Part4sPriceEntity> selectByPartCodes(List<String> param) {
		return commonDao.mapper(Part4sPriceEntity.class).source(SLAVE).sql("selectByPartCodes").session()
		                .selectList(param);
	}

	public int delete(Part4sPriceEntity param) {
		return commonDao.mapper(Part4sPriceEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public int batchDelete(List<Part4sPriceEntity> param) {
		return commonDao.mapper(Part4sPriceEntity.class).source(MASTER).sql("batchDelete").session()
		                .update(param);
	}

	public void insert(Part4sPriceEntity param) {
		commonDao.mapper(Part4sPriceEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<Part4sPriceEntity> param) {
		commonDao.mapper(Part4sPriceEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public int updateBySelective(Part4sPriceEntity param) {
		return commonDao.mapper(Part4sPriceEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

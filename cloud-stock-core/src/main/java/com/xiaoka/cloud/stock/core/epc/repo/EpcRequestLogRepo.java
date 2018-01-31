package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.EpcRequestLogEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;

/**
 * Do something
 *
 * @author gancao 2017/11/21 10:42
 * @see [相关类/方法]
 * @since [版本号]
 */
@Repository
public class EpcRequestLogRepo {

	@Resource
	private CommonDao commonDao;

	public void insert(EpcRequestLogEntity param) {
		commonDao.mapper(EpcRequestLogEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}
}

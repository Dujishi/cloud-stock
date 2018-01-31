package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudCompanyStrategyPropertyEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CloudCompanyStrategyPropertyRepo
 *
 * @author zhouze
 */
@Repository
public class CloudCompanyStrategyPropertyRepo {
	@Resource
	private CommonDao commonDao;

	public List<CloudCompanyStrategyPropertyEntity> select(CloudCompanyStrategyPropertyEntity param) {
		return commonDao.mapper(CloudCompanyStrategyPropertyEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(CloudCompanyStrategyPropertyEntity param) {
		return commonDao.mapper(CloudCompanyStrategyPropertyEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(CloudCompanyStrategyPropertyEntity param) {
		commonDao.mapper(CloudCompanyStrategyPropertyEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(CloudCompanyStrategyPropertyEntity param) {
		return commonDao.mapper(CloudCompanyStrategyPropertyEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<CloudCompanyStrategyPropertyEntity> selectByStrategyId(Integer strategyId) {
		return commonDao.mapper(CloudCompanyStrategyPropertyEntity.class).source(SLAVE)
				.sql("selectByStrategyId").session()
				.selectList(strategyId);
	}

}

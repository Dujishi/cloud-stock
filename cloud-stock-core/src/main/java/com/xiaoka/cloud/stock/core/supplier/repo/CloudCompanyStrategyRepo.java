package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudCompanyStrategyEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CloudCompanyStrategyRepo
 *
 * @author zhouze
 */
@Repository
public class CloudCompanyStrategyRepo {
	@Resource
	private CommonDao commonDao;

	public List<CloudCompanyStrategyEntity> select(CloudCompanyStrategyEntity param) {
		return commonDao.mapper(CloudCompanyStrategyEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(CloudCompanyStrategyEntity param) {
		return commonDao.mapper(CloudCompanyStrategyEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(CloudCompanyStrategyEntity param) {
		commonDao.mapper(CloudCompanyStrategyEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(CloudCompanyStrategyEntity param) {
		return commonDao.mapper(CloudCompanyStrategyEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<CloudCompanyStrategyEntity> selectByCompanyId(Integer companyId) {
		return commonDao.mapper(CloudCompanyStrategyEntity.class).source(SLAVE)
				.sql("selectByCompanyId").session()
				.selectList(companyId);
	}
}

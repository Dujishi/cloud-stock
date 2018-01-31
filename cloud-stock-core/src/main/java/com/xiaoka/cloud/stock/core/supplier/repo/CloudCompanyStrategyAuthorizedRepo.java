package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudCompanyStrategyAuthorizedEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CloudCompanyStrategyAuthorizedRepo
 *
 * @author zhouze
 */
@Repository
public class CloudCompanyStrategyAuthorizedRepo {
	@Resource
	private CommonDao commonDao;

	public List<CloudCompanyStrategyAuthorizedEntity> select(CloudCompanyStrategyAuthorizedEntity param) {
		return commonDao.mapper(CloudCompanyStrategyAuthorizedEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(CloudCompanyStrategyAuthorizedEntity param) {
		return commonDao.mapper(CloudCompanyStrategyAuthorizedEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(CloudCompanyStrategyAuthorizedEntity param) {
		commonDao.mapper(CloudCompanyStrategyAuthorizedEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(CloudCompanyStrategyAuthorizedEntity param) {
		return commonDao.mapper(CloudCompanyStrategyAuthorizedEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<CloudCompanyStrategyAuthorizedEntity> selectByPhone(String phone) {
		return commonDao.mapper(CloudCompanyStrategyAuthorizedEntity.class)
				.source(SLAVE).sql("selectByPhone").session()
				.selectList(phone);
	}

}

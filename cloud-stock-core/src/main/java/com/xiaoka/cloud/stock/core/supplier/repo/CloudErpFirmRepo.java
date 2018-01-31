package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudErpFirmEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.utils.common.UUIDGenerator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudErpFirmRepo {

	//前缀
	public static final String APP_ID_PREFIX = "xk";
	//基础id
	public static final Long BASE_ID = 100000000000L;

	@Resource
	private CommonDao commonDao;

	public String getErpKeyByAppId(String appId) {
		return commonDao.mapper(CloudErpFirmEntity.class).source(SLAVE).sql("getErpKeyByAppId").session()
		                .selectOne(appId);
	}

	public void insert(CloudErpFirmEntity entity) {
		entity.setKey(UUIDGenerator.generate());
		entity.setAppId("0");
		commonDao.mapper(CloudErpFirmEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
		entity.setAppId(getAppId(entity.getId()));
		this.update(entity);
	}

	public void update(CloudErpFirmEntity entity) {
		commonDao.mapper(CloudErpFirmEntity.class).source(MASTER).sql("update").session()
		         .update(entity);
	}

	public void deleteErpFirmById(Integer id) {
		commonDao.mapper(CloudErpFirmEntity.class).source(MASTER).sql("deleteErpFirmById").session()
		         .update(id);
	}

	private String getAppId(Integer id) {
		return APP_ID_PREFIX + String.valueOf(BASE_ID + id);
	}

}
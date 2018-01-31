package com.xiaoka.cloud.stock.core.supplier.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudErpSupplierMapperEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudErpSupplierMapperRepo {

	@Resource
	private CommonDao commonDao;

	public Integer getSupplierId(String appId, String outSupplierId){
		Map<String, String> map = Maps.newHashMap();
		map.put("appId", appId);
		map.put("outSupplierId", outSupplierId);
		return commonDao.mapper(CloudErpSupplierMapperEntity.class).source(SLAVE).sql("getSupplierId").session()
		                .selectOne(map);
	}

	public void insert(CloudErpSupplierMapperEntity entity){
		commonDao.mapper(CloudErpSupplierMapperEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
	}

	public void update(CloudErpSupplierMapperEntity entity){
		commonDao.mapper(CloudErpSupplierMapperEntity.class).source(MASTER).sql("update").session()
		         .update(entity);
	}



}
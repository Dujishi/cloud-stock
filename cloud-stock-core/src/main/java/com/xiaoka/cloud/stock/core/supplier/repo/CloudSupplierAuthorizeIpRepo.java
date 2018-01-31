package com.xiaoka.cloud.stock.core.supplier.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierAuthorizeIpEntity;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierUserEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudSupplierAuthorizeIpRepo {

	@Resource
	private CommonDao commonDao;

	public void insert(CloudSupplierAuthorizeIpEntity entity){
		commonDao.mapper(CloudSupplierAuthorizeIpEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
	}

	public int getCountByIp(String ip){
		return commonDao.mapper(CloudSupplierAuthorizeIpEntity.class).source(SLAVE).sql("getCountByIp").session()
		                .selectOne(ip);
	}
	public int getCountByIpAndSupplierId(String ip, Integer supplierId){
		Map<String, Object> map = Maps.newHashMap();
		map.put("ip", ip);
		map.put("supplierId", supplierId);
		return commonDao.mapper(CloudSupplierAuthorizeIpEntity.class).source(SLAVE).sql("getCountByIpAndSupplierId").session()
		                .selectOne(map);
	}

	public void update(CloudSupplierAuthorizeIpEntity entity){
		commonDao.mapper(CloudSupplierAuthorizeIpEntity.class).source(MASTER).sql("update").session()
		         .update(entity);
	}

	public void deleteSupplierAuthorizeById(Integer id){
		commonDao.mapper(CloudSupplierUserEntity.class).source(MASTER).sql("deleteSupplierAuthorizeById").session()
		         .update(id);
	}

}
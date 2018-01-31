package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierCompanyEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudSupplierCompanyRepo {

	@Resource
	private CommonDao commonDao;

	public CloudSupplierCompanyEntity selectBySupplierId(Integer supplierId){
		return commonDao.mapper(CloudSupplierCompanyEntity.class).source(SLAVE).sql("selectBySupplierId").session()
		                .selectOne(supplierId);
	}

	public void insert(CloudSupplierCompanyEntity entity){
		commonDao.mapper(CloudSupplierCompanyEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
	}

	public void update(CloudSupplierCompanyEntity entity){
		commonDao.mapper(CloudSupplierCompanyEntity.class).source(MASTER).sql("update").session()
		         .update(entity);
	}

	public void deleteSubSupplier(Integer id){
		commonDao.mapper(CloudSupplierCompanyEntity.class).source(MASTER).sql("update").session()
		         .update(id);
	}

}
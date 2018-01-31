package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierCompanySubEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudSupplierCompanySubRepo {

	@Resource
	private CommonDao commonDao;

	public void insert(CloudSupplierCompanySubEntity entity){
		commonDao.mapper(CloudSupplierCompanySubEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
	}

	public CloudSupplierCompanySubEntity selectBySupplierId(Integer supplierId){
		return commonDao.mapper(CloudSupplierCompanySubEntity.class).source(SLAVE).sql("selectBySupplierId").session()
		                .selectOne(supplierId);
	}

	public List<Integer> selectAllSubSupplierIdById(Integer id){
		return commonDao.mapper(CloudSupplierCompanySubEntity.class).source(SLAVE).sql("selectAllSubSupplierIdById").session()
		                .selectList(id);
	}

	public void update(CloudSupplierCompanySubEntity entity){
		commonDao.mapper(CloudSupplierCompanySubEntity.class).source(MASTER).sql("update").session()
		         .update(entity);
	}

	public void deleteSubSupplier(Integer id){
		commonDao.mapper(CloudSupplierCompanySubEntity.class).source(MASTER).sql("deleteSubSupplier").session()
		         .update(id);
	}

}
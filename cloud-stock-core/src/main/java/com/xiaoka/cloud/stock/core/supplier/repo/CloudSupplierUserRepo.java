package com.xiaoka.cloud.stock.core.supplier.repo;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierUserEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudSupplierUserRepo {

	@Resource
	private CommonDao commonDao;

	public void insert(CloudSupplierUserEntity entity){
		commonDao.mapper(CloudSupplierUserEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
	}

	public int countByPhone(String phone){
		return commonDao.mapper(CloudSupplierUserEntity.class).source(MASTER).sql("countByPhone").session()
		                .selectOne(phone);
	}

	public CloudSupplierUserEntity selectById(Integer id){
		return commonDao.mapper(CloudSupplierUserEntity.class).source(SLAVE).sql("selectById").session()
		                .selectOne(id);
	}

	public CloudSupplierUserEntity selectByPhone(String phone){
		return commonDao.mapper(CloudSupplierUserEntity.class).source(SLAVE).sql("selectByPhone").session()
		                .selectOne(phone);
	}

	public List<CloudSupplierUserEntity> selectBySupplierId(Integer supplierId){
		return commonDao.mapper(CloudSupplierUserEntity.class).source(SLAVE).sql("selectBySupplierId").session()
		                .selectList(supplierId);
	}

	public void update(CloudSupplierUserEntity entity){
		commonDao.mapper(CloudSupplierUserEntity.class).source(MASTER).sql("update").session()
		         .update(entity);
	}

	public void deleteSupplierUser(Integer id){
		commonDao.mapper(CloudSupplierUserEntity.class).source(MASTER).sql("deleteSupplierUser").session()
		         .update(id);
	}

}
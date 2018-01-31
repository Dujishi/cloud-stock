package com.xiaoka.cloud.stock.core.stock.repo;

import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceUncertainEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.data.datasource.RoutingDataSourceDecision;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/14
 * @see [相关类/方法]
 * @since [版本号]
 */
@Repository
public class CloudPartPriceUncertainRepo {

	@Resource
	private CommonDao commonDao;

	/**
	 * 根据条件查询云仓配件价格
	 * 主库查询，用于数据即时性要求
	 *
	 * @param partPrices 内部商家Id、外部库存Id、外部配件id
	 * @return
	 */
	public List<CloudPartPriceUncertainEntity> queryCloudPartPriceByConditionList(List<CloudPartPriceUncertainEntity> partPrices) {
		return commonDao.mapper(CloudPartPriceUncertainEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("queryCloudPartPriceUncertainByConditionList")
				.session()
				.selectList(partPrices);
	}

	/**
	 * 批量插入数据
	 *
	 * @param insertCloudPriceList
	 * @return
	 * @throws DuplicateKeyException
	 */
	public Integer insertCloudPartsPriceEntities(List<CloudPartPriceUncertainEntity> insertCloudPriceList) {
		return commonDao.mapper(CloudPartPriceUncertainEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("insertCloudPartsPriceUncertainEntities")
				.session()
				.insert(insertCloudPriceList);
	}

	/**
	 * 批量修改数据
	 *
	 * @param updateCloudPriceList
	 * @return
	 */
	public Integer casUpdateCloudPartPriceEntities(List<CloudPartPriceUncertainEntity> updateCloudPriceList) {
		return commonDao.mapper(CloudPartPriceUncertainEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("casUpdateCloudPartPriceUncertainEntities")
				.session()
				.update(updateCloudPriceList);
	}


}

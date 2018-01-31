package com.xiaoka.cloud.stock.core.stock.repo;

import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.data.datasource.RoutingDataSourceDecision;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/14
 * @see [相关类/方法]
 * @since [版本号]
 */
@Repository
public class CloudPartPriceRepo {

	@Resource
	private CommonDao commonDao;

	/**
	 * 根据条件查询云仓配件价格
	 * 主库查询，用于数据即时性要求
	 *
	 * @param partPrices 内部商家Id、外部库存Id、外部配件id
	 * @return
	 */
	public List<CloudPartPriceEntity> queryCloudPartPriceByConditionList(List<CloudPartPriceEntity> partPrices) {
		return commonDao.mapper(CloudPartPriceEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("queryCloudPartPriceByConditionList")
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
	public Integer insertCloudPartsPriceEntities(List<CloudPartPriceEntity> insertCloudPriceList) {
		return commonDao.mapper(CloudPartPriceEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("insertCloudPartsPriceEntities")
				.session()
				.insert(insertCloudPriceList);
	}

	/**
	 * 批量修改数据
	 *
	 * @param updateCloudPriceList
	 * @return
	 */
	public Integer casUpdateCloudPartPriceEntities(List<CloudPartPriceEntity> updateCloudPriceList) {
		return commonDao.mapper(CloudPartPriceEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("casUpdateCloudPartPriceEntities")
				.session()
				.update(updateCloudPriceList);
	}

	/**
	 * 根据库存配件主键id列表查询价格列表
	 * 查询从库
	 *
	 * @param partIds
	 */
	public List<CloudPartPriceEntity> queryPartsPriceByPartIds(List<Integer> partIds) {
		if (CollectionUtils.isEmpty(partIds)) {
			return Collections.emptyList();
		}
		return commonDao.mapper(CloudPartPriceEntity.class)
				.source(RoutingDataSourceDecision.Source.SLAVE)
				.sql("queryPartsPriceByPartIds")
				.session()
				.selectList(partIds);
	}
}

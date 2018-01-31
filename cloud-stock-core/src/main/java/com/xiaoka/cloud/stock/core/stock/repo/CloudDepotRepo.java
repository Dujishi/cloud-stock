package com.xiaoka.cloud.stock.core.stock.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudDepotEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.data.datasource.RoutingDataSourceDecision;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author zhouze
 * @date 2017/11/14
 * @see [相关类/方法]
 * @since [版本号]
 */
@Repository
public class CloudDepotRepo {


	@Resource
	private CommonDao commonDao;

	/**
	 * 根据外部仓库id和外部供应商id查询仓库
	 * 主库查询，用于数据即时性要求
	 *
	 * @param cDepotId 外部仓库id
	 * @param shopId   内部供应商id
	 * @return
	 */
	public CloudDepotEntity queryDepotByCondition(String cDepotId, String shopId) {
		Map<String, String> maps = Maps.newHashMap();
		maps.put("cDepotId", cDepotId);
		maps.put("shopId", shopId);
		return commonDao.mapper(CloudDepotEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("queryDepotByCondition")
				.session()
				.selectOne(maps);
	}

	/**
	 * 根据外部仓库id和外部供应商id列表查询仓库
	 * 主库查询，用于数据即时性要求
	 *
	 * @param list 外部仓库id、外部供应商id 集合
	 * @return
	 */
	public List<CloudDepotEntity> queryDepotByConditionList(List<CloudDepotEntity> list) {
		return commonDao.mapper(CloudDepotEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("queryDepotByConditionList")
				.session()
				.selectList(list);
	}

	/**
	 * 批量插入，返回批量主键值
	 *
	 * @param list
	 * @return
	 * @throws DuplicateKeyException
	 */
	public Integer insertCloudDepotEntities(List<CloudDepotEntity> list) {
		return commonDao.mapper(CloudDepotEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("insertCloudDepotEntities")
				.session()
				.insert(list);
	}

	/**
	 * 修改云仓仓库信息
	 *
	 * @param updateCloudDepotList
	 * @return
	 */
	public Integer casUpdateCloudDepotEntities(List<CloudDepotEntity> updateCloudDepotList) {
		return commonDao.mapper(CloudDepotEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("casUpdateCloudDepotEntities")
				.session()
				.update(updateCloudDepotList);
	}

	/**
	 * 查询仓库列表
	 * 查询从库
	 *
	 * @param depotIds 仓库主键id列表
	 * @return
	 */
	public List<CloudDepotEntity> queryDepotsByIds(List<Integer> depotIds) {
		if (CollectionUtils.isEmpty(depotIds)) {
			return Collections.emptyList();
		}
		return commonDao.mapper(CloudDepotEntity.class)
				.source(RoutingDataSourceDecision.Source.SLAVE)
				.sql("queryDepotsByIds")
				.session()
				.selectList(depotIds);
	}
}

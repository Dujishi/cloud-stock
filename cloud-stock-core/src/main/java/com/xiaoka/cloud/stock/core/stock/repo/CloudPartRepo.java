package com.xiaoka.cloud.stock.core.stock.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.stock.annotion.SpecialFunction;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.data.datasource.RoutingDataSourceDecision;
import com.xiaoka.freework.help.page.PageList;
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
public class CloudPartRepo {

	@Resource
	private CommonDao commonDao;

	/**
	 * 根据条件查询云仓库存
	 * 主库查询，用于数据即时性要求
	 *
	 * @param cloudPartEntities 内部商家Id、外部库存Id、外部配件id
	 * @return
	 */
	public List<CloudPartEntity> queryCloudPartsByConditionList(List<CloudPartEntity> cloudPartEntities) {
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("queryCloudPartsByConditionList")
				.session()
				.selectList(cloudPartEntities);
	}

	public List<CloudPartEntity> selectByOeNo(String oeNo) {
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("selectByOeNo")
				.session()
				.selectList(oeNo);
	}

	/**
	 * 批量插入，返回批量主键值
	 *
	 * @param list
	 * @return
	 * @throws DuplicateKeyException
	 */
	public Integer insertCloudPartEntities(List<CloudPartEntity> list) {
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("insertCloudPartEntities")
				.session()
				.insert(list);
	}

	/**
	 * 批量修改
	 *
	 * @param list
	 * @return
	 */
	public Integer casUpdateCloudPartEntities(List<CloudPartEntity> list) {
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("casUpdateCloudPartEntities")
				.session()
				.update(list);
	}

	/**
	 * 查询库存配件信息列表
	 * 查询从库
	 *
	 * @param codes
	 * @param shopIds
	 * @return
	 */
	public List<CloudPartEntity> queryStockPartInfoListByShopIdsAndOeNoes(List<String> codes, List<Integer> shopIds) {
		Map<String, Object> maps = Maps.newHashMap();
		maps.put("codes", codes);
		maps.put("shopIds", shopIds);
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.SLAVE)
				.sql("queryStockPartInfoListByShopIdsAndOeNoes")
				.session()
				.selectList(maps);
	}

	/**
	 * 根据codes查询排除shopIds以外的其他商家存在库存的数据
	 * 查询从库
	 *
	 * @param codes   oeNo
	 * @param shopIds 内部商家id列表
	 */
	public List<CloudPartEntity> queryExistStockPartsByOeNosExcludeShopIds(List<String> codes, List<Integer> shopIds) {
		if (CollectionUtils.isEmpty(codes) || CollectionUtils.isEmpty(shopIds)) {
			return Collections.emptyList();
		}
		Map<String, Object> maps = Maps.newHashMap();
		maps.put("codes", codes);
		maps.put("shopIds", shopIds);
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.SLAVE)
				.sql("queryExistStockPartsByOeNosExcludeShopIds")
				.session()
				.selectList(maps);
	}


	public Integer queryStockPartCountByShopIdsAndOeNoes(List<String> codes, List<Integer> shopIds) {
		if (CollectionUtils.isEmpty(codes) || CollectionUtils.isEmpty(shopIds)) {
			return null;
		}
		Map<String, Object> maps = Maps.newHashMap();
		maps.put("codes", codes);
		maps.put("shopIds", shopIds);
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.SLAVE)
				.sql("queryStockPartCountByShopIdsAndOeNoes")
				.session()
				.selectOne(maps);
	}

	/**
	 * 查询存在库存分页数据
	 * 必要条件:shopId
	 *
	 * @param pageList
	 * @return
	 */
	@SpecialFunction
	public List<CloudPartEntity> queryExistsStockPageList(PageList<CloudPartEntity> pageList) {
		return commonDao.mapper(CloudPartEntity.class)
				.source(RoutingDataSourceDecision.Source.SLAVE)
				.sql("queryExistsStockPageList")
				.session()
				.selectList(pageList);
	}
}

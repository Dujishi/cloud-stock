package com.xiaoka.cloud.stock.core.epc.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * PartReplaceRepo
 *
 * @author suqin
 */
@Repository
public class PartReplaceRepo {
	@Resource
	private CommonDao commonDao;

	public List<PartReplaceEntity> select(PartReplaceEntity param) {
		return commonDao.mapper(PartReplaceEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<PartReplaceEntity> selectByPartCodeList(List<String> partCodeList, String brandName){
		Map<String, Object> map = Maps.newHashMap();
		map.put("partCodeList", partCodeList);
		map.put("brandName", brandName);
		return commonDao.mapper(PartReplaceEntity.class).source(SLAVE).sql("selectByPartCodeList").session()
		                .selectList(map);
	}

	public int delete(PartReplaceEntity param) {
		return commonDao.mapper(PartReplaceEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(PartReplaceEntity param) {
		commonDao.mapper(PartReplaceEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(PartReplaceEntity param) {
		return commonDao.mapper(PartReplaceEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public int batchInsert(List<PartReplaceEntity> partReplaceEntities) {
		return commonDao.mapper(PartReplaceEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(partReplaceEntities);
	}

	public List<PartReplaceEntity> selectByCodeList(List<PartReplaceEntity> partReplaceEntityList) {
		return commonDao.mapper(PartReplaceEntity.class)
				.source(MASTER)
				.sql("selectByCodeList").session()
				.selectList(partReplaceEntityList);
	}
}

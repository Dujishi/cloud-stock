package com.xiaoka.cloud.stock.core.supplier.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSearchLogEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

@Repository
public class CloudSearchLogRepo {

	@Resource
	private CommonDao commonDao;

	public void insert(CloudSearchLogEntity entity){
		commonDao.mapper(CloudSearchLogEntity.class).source(MASTER).sql("insert").session()
		         .insert(entity);
	}

	public List<CloudSearchLogEntity> selectLateLog(Integer userId, Integer searchType, String content){
		Map<String, Object> map = Maps.newHashMap();
		map.put("userId", userId);
		map.put("searchType", searchType);
		if (StringUtils.isNotBlank(content)){
			map.put("content", content);
		}
		return commonDao.mapper(CloudSearchLogEntity.class).source(SLAVE).sql("selectLateLog").session()
		                .selectList(map);
	}

	public void delete(Integer id){
		commonDao.mapper(CloudSearchLogEntity.class).source(MASTER).sql("delete").session()
		         .update(id);
	}


}
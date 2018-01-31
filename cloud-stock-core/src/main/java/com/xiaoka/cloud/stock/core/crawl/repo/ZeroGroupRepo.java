package com.xiaoka.cloud.stock.core.crawl.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroGroupEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroGroupRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroGroupRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroGroupEntity> select(ZeroGroupEntity param) {
		return commonDao.mapper(ZeroGroupEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroGroupEntity param) {
		return commonDao.mapper(ZeroGroupEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public Integer insert(ZeroGroupEntity param) {
		 commonDao.mapper(ZeroGroupEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
		return param.getId();
	}

	public int updateBySelective(ZeroGroupEntity param) {
		return commonDao.mapper(ZeroGroupEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroGroupEntity> selectByGroupName(String groupName, Integer cId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("groupName", groupName);
		map.put("cId", cId);
		return commonDao.mapper(ZeroGroupEntity.class).source(MASTER)
				.sql("selectByGroupName").session()
				.selectList(map);
	}

	public List<ZeroGroupEntity> selectByList(List<ZeroGroupEntity> groupParams) {
		return commonDao.mapper(ZeroGroupEntity.class).source(SLAVE)
				.sql("selectByList").session()
				.selectList(groupParams);
	}
}

package com.xiaoka.cloud.stock.core.epc.repo;


import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartCodeEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * @author zhouze
 */
@Repository
public class PartCodeRepo {


	private static final Integer DEFAULT_PAGE_NUM  = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 500;

	@Resource
	private CommonDao commonDao;

	public List<PartCodeEntity> select(PartCodeEntity param) {
		return commonDao.mapper(PartCodeEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(PartCodeEntity param) {
		return commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(PartCodeEntity param) {
		commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(PartCodeEntity param) {
		return commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<PartCodeEntity> selectListByCodes(List<String> codes) {
		return commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("selectListByCodes").session()
				.selectList(codes);
	}

	public void insertList(List<PartCodeEntity> insertList) {
		commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertList);
	}

	public int selectCount() {
		return commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("selectCount").session()
				.selectOne();
	}

	public List<PartCodeEntity> selectPage(Integer pageNumber, Integer pageSize) {
		int                  start    = (pageNumber == null || pageNumber <= 0) ? DEFAULT_PAGE_NUM : pageNumber;
		int                  offset   = (pageSize == null || pageSize < 0) ? DEFAULT_PAGE_SIZE : pageSize;
		Map<String, Integer> maps = new HashMap<>();
		maps.put("start", (start - 1) * offset);
		maps.put("offset", offset);
		return commonDao.mapper(PartCodeEntity.class).source(MASTER).sql("selectPage").session()
				.selectList(maps);
	}
}

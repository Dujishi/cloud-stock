package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.PartModelEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * PartModelRepo
 *
 * @author suqin
 */
@Repository
public class PartModelRepo {
	@Resource
	private CommonDao commonDao;

	public List<PartModelEntity> select(PartModelEntity param) {
		return commonDao.mapper(PartModelEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(PartModelEntity param) {
		return commonDao.mapper(PartModelEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(PartModelEntity param) {
		commonDao.mapper(PartModelEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(PartModelEntity param) {
		return commonDao.mapper(PartModelEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<PartModelEntity> queryPartModelListByCode(String code) {
		return commonDao.mapper(PartModelEntity.class).source(SLAVE)
				.sql("queryPartModelListByCode")
				.session()
				.selectList(code);
	}

	public Integer insertList(List<PartModelEntity> insertList) {
		return commonDao.mapper(PartModelEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertList);
	}
}

package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupPartsEntity;
import org.omg.CORBA.INTERNAL;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroSubGroupPartsRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroSubGroupPartsRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroSubGroupPartsEntity> select(ZeroSubGroupPartsEntity param) {
		return commonDao.mapper(ZeroSubGroupPartsEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroSubGroupPartsEntity param) {
		return commonDao.mapper(ZeroSubGroupPartsEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroSubGroupPartsEntity param) {
		commonDao.mapper(ZeroSubGroupPartsEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroSubGroupPartsEntity param) {
		return commonDao.mapper(ZeroSubGroupPartsEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroSubGroupPartsEntity> selectByConditions(List<ZeroSubGroupPartsEntity> subGroupParts) {
		return commonDao.mapper(ZeroSubGroupPartsEntity.class).source(MASTER).sql("selectByConditions").session()
				.selectList(subGroupParts);
	}

	public Integer insertList(List<ZeroSubGroupPartsEntity> insertEntities) {
		return commonDao.mapper(ZeroSubGroupPartsEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertEntities);
	}
}

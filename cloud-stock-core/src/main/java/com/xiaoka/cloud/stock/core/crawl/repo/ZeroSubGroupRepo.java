package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroSubGroupEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroSubGroupRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroSubGroupRepo {

	@Resource
	private CommonDao commonDao;

	public List<ZeroSubGroupEntity> select(ZeroSubGroupEntity param) {
		return commonDao.mapper(ZeroSubGroupEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroSubGroupEntity param) {
		return commonDao.mapper(ZeroSubGroupEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroSubGroupEntity param) {
		commonDao.mapper(ZeroSubGroupEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroSubGroupEntity param) {
		return commonDao.mapper(ZeroSubGroupEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroSubGroupEntity> selectHasExistedSubGroup(List<ZeroSubGroupEntity> subGroupEntities) {
		return commonDao.mapper(ZeroSubGroupEntity.class).source(MASTER).sql("selectHasExistedSubGroup").session()
				.selectList(subGroupEntities);
	}

	public Integer insertList(List<ZeroSubGroupEntity> insertSubGroups) {
		return commonDao.mapper(ZeroSubGroupEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertSubGroups);
	}
}

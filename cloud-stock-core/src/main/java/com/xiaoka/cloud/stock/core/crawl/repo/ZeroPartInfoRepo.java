package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroPartInfoEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroPartInfoRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroPartInfoRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroPartInfoEntity> select(ZeroPartInfoEntity param) {
		return commonDao.mapper(ZeroPartInfoEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroPartInfoEntity param) {
		return commonDao.mapper(ZeroPartInfoEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroPartInfoEntity param) {
		commonDao.mapper(ZeroPartInfoEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroPartInfoEntity param) {
		return commonDao.mapper(ZeroPartInfoEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroPartInfoEntity> selectByConditions(List<ZeroPartInfoEntity> subPartInfos) {
		return commonDao.mapper(ZeroPartInfoEntity.class).source(MASTER).sql("selectByConditions").session()
				.selectList(subPartInfos);
	}

	public Integer insertList(List<ZeroPartInfoEntity> insertEntities) {
		return commonDao.mapper(ZeroPartInfoEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertEntities);
	}

	public Integer updateList(List<ZeroPartInfoEntity> updateEntities) {
		return commonDao.mapper(ZeroPartInfoEntity.class).source(MASTER).sql("updateList").session()
				.update(updateEntities);
	}
}

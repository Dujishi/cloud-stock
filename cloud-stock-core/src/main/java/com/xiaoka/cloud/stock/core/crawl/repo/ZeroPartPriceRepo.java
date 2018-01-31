package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroPartPriceEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroPartPriceRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroPartPriceRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroPartPriceEntity> select(ZeroPartPriceEntity param) {
		return commonDao.mapper(ZeroPartPriceEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroPartPriceEntity param) {
		return commonDao.mapper(ZeroPartPriceEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroPartPriceEntity param) {
		commonDao.mapper(ZeroPartPriceEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroPartPriceEntity param) {
		return commonDao.mapper(ZeroPartPriceEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<ZeroPartPriceEntity> selectByConditions(List<ZeroPartPriceEntity> subPartPrices) {
		return commonDao.mapper(ZeroPartPriceEntity.class).source(MASTER).sql("selectByConditions").session()
				.selectList(subPartPrices);
	}

	public Integer insertList(List<ZeroPartPriceEntity> insertEntities) {
		return commonDao.mapper(ZeroPartPriceEntity.class).source(MASTER).sql("insertList").session()
				.insert(insertEntities);
	}

	public Integer updateList(List<ZeroPartPriceEntity> updateEntities) {
		return commonDao.mapper(ZeroPartPriceEntity.class).source(MASTER).sql("updateList").session()
				.update(updateEntities);
	}

}

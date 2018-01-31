package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroMarketEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * ZeroMarketRepo
 *
 * @author zhouze
 */
@Repository
public class ZeroMarketRepo {
	@Resource
	private CommonDao commonDao;

	public List<ZeroMarketEntity> select(ZeroMarketEntity param) {
		return commonDao.mapper(ZeroMarketEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public int delete(ZeroMarketEntity param) {
		return commonDao.mapper(ZeroMarketEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

	public void insert(ZeroMarketEntity param) {
		commonDao.mapper(ZeroMarketEntity.class).source(MASTER).sql("insert").session()
				.insert(param);
	}

	public int updateBySelective(ZeroMarketEntity param) {
		return commonDao.mapper(ZeroMarketEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

}

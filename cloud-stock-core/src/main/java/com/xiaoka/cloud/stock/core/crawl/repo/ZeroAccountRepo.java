package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for account
 * ZeroAccountRepo
 *
 * @author gancao
 */
@Repository
public class ZeroAccountRepo {
	@Resource
	private CommonDao commonDao;

	public int accountAmount() {
		return commonDao.mapper(ZeroAccountEntity.class).source(SLAVE).sql("accountAmount").session()
		                .selectOne();
	}

	public List<ZeroAccountEntity> select(ZeroAccountEntity param) {
		return commonDao.mapper(ZeroAccountEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<ZeroAccountEntity> selectProxyAccount() {
		return commonDao.mapper(ZeroAccountEntity.class).source(SLAVE).sql("selectProxyAccount").session()
		                .selectList();
	}

	public List<ZeroAccountEntity> selectNoIp() {
		return commonDao.mapper(ZeroAccountEntity.class).source(SLAVE).sql("selectNoIp").session()
		                .selectList();
	}

	public int delete(ZeroAccountEntity param) {
		return commonDao.mapper(ZeroAccountEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(ZeroAccountEntity param) {
		commonDao.mapper(ZeroAccountEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(ZeroAccountEntity param) {
		return commonDao.mapper(ZeroAccountEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public void updateErrorIp(Integer id) {
		commonDao.mapper(ZeroAccountEntity.class).source(MASTER).sql("updateErrorIp").session()
		         .update(id);
	}

}

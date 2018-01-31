package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelAccountEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;

/**
 * for car model account
 * ZeroCarModelAccountRepo
 *
 * @author gancao
 *
 */
@Repository
public class ZeroCarModelAccountRepo {
     @Resource
     private CommonDao commonDao;

    public List<ZeroCarModelAccountEntity> select(ZeroCarModelAccountEntity param){
        return commonDao.mapper(ZeroCarModelAccountEntity.class).source(MASTER).sql("select").session()
        .selectList(param);
    }

    public int delete(ZeroCarModelAccountEntity param){
        return commonDao.mapper(ZeroCarModelAccountEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(ZeroCarModelAccountEntity param){
        commonDao.mapper(ZeroCarModelAccountEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(ZeroCarModelAccountEntity param){
        return commonDao.mapper(ZeroCarModelAccountEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

}

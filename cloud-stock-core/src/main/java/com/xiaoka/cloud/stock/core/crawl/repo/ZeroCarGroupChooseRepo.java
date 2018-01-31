package com.xiaoka.cloud.stock.core.crawl.repo;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarGroupChooseEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car group
 * ZeroCarGroupChooseRepo
 *
 * @author gancao
 *
 */
@Repository
public class ZeroCarGroupChooseRepo {
     @Resource
     private CommonDao commonDao;

    public List<ZeroCarGroupChooseEntity> select(ZeroCarGroupChooseEntity param){
        return commonDao.mapper(ZeroCarGroupChooseEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public List<ZeroCarGroupChooseEntity> batchSelectByAuth(List<String> authList){
        return commonDao.mapper(ZeroCarGroupChooseEntity.class).source(SLAVE).sql("batchSelectByAuth").session()
                        .selectList(authList);
    }

    public int delete(ZeroCarGroupChooseEntity param){
        return commonDao.mapper(ZeroCarGroupChooseEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(ZeroCarGroupChooseEntity param){
        commonDao.mapper(ZeroCarGroupChooseEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public void batchInsert(List<ZeroCarGroupChooseEntity> entityList){
        commonDao.mapper(ZeroCarGroupChooseEntity.class).source(MASTER).sql("batchInsert").session()
                 .insert(entityList);
    }

    public int updateBySelective(ZeroCarGroupChooseEntity param){
        return commonDao.mapper(ZeroCarGroupChooseEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

    public void deleteData(){
        commonDao.mapper(ZeroCarGroupChooseEntity.class).source(MASTER).sql("deleteData").session()
                 .delete();
    }

}

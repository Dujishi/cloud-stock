package com.xiaoka.cloud.stock.core.order;

import com.xiaoka.cloud.stock.core.order.entity.CsIndentEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CsIndentRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CsIndentRepo {
     @Resource
     private CommonDao commonDao;

    public List<CsIndentEntity> select(CsIndentEntity param){
        return commonDao.mapper(CsIndentEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public int delete(CsIndentEntity param){
        return commonDao.mapper(CsIndentEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public int updateIndentName(CsIndentEntity param){
        return commonDao.mapper(CsIndentEntity.class).source(MASTER).sql("updateIndentName").session()
                        .update(param);
    }

    public void insert(CsIndentEntity param){
        commonDao.mapper(CsIndentEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(CsIndentEntity param){
        return commonDao.mapper(CsIndentEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

}

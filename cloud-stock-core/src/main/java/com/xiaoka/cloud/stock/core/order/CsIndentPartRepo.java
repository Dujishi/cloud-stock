package com.xiaoka.cloud.stock.core.order;

import com.xiaoka.cloud.stock.core.order.entity.CsIndentPartEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CsIndentPartRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CsIndentPartRepo {
     @Resource
     private CommonDao commonDao;

    public List<CsIndentPartEntity> select(CsIndentPartEntity param){
        return commonDao.mapper(CsIndentPartEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public int delete(CsIndentPartEntity param){
        return commonDao.mapper(CsIndentPartEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(CsIndentPartEntity param){
        commonDao.mapper(CsIndentPartEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(CsIndentPartEntity param){
        return commonDao.mapper(CsIndentPartEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

}

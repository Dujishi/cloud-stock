package com.xiaoka.cloud.stock.core.order;

import com.xiaoka.cloud.stock.core.order.entity.CsOrderPartEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CsOrderPartRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CsOrderPartRepo {
     @Resource
     private CommonDao commonDao;

    public List<CsOrderPartEntity> select(CsOrderPartEntity param){
        return commonDao.mapper(CsOrderPartEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public List<CsOrderPartEntity> statAmountByOrderNos(List<String> orderNos){
        return commonDao.mapper(CsOrderPartEntity.class).source(SLAVE).sql("statAmountByOrderNos").session()
        .selectList(orderNos);
    }

    public int delete(CsOrderPartEntity param){
        return commonDao.mapper(CsOrderPartEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(CsOrderPartEntity param){
        commonDao.mapper(CsOrderPartEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(CsOrderPartEntity param){
        return commonDao.mapper(CsOrderPartEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

}

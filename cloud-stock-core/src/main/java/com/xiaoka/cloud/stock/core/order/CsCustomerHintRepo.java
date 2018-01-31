package com.xiaoka.cloud.stock.core.order;

import com.xiaoka.cloud.stock.core.order.entity.CsCustomerHintEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CsCustomerHintRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CsCustomerHintRepo {
     @Resource
     private CommonDao commonDao;

    public List<CsCustomerHintEntity> select(CsCustomerHintEntity param){
        return commonDao.mapper(CsCustomerHintEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public int delete(CsCustomerHintEntity param){
        return commonDao.mapper(CsCustomerHintEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(CsCustomerHintEntity param){
        commonDao.mapper(CsCustomerHintEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(CsCustomerHintEntity param){
        return commonDao.mapper(CsCustomerHintEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

}

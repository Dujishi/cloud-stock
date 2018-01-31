package com.xiaoka.cloud.stock.core.order;

import com.xiaoka.cloud.stock.core.order.entity.CsOrderEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CsOrderRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CsOrderRepo {
     @Resource
     private CommonDao commonDao;

    public List<CsOrderEntity> select(CsOrderEntity param){
        return commonDao.mapper(CsOrderEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public List<CsOrderEntity> selectByQueryStr(Map<String, Object> paramMap){
        paramMap.put("isValid", true);
        return commonDao.mapper(CsOrderEntity.class).source(SLAVE).sql("selectByQueryStr").session()
                        .selectList(paramMap);
    }

    public int countByQueryStr(Map<String, Object> paramMap){
        paramMap.put("isValid", true);
        return commonDao.mapper(CsOrderEntity.class).source(SLAVE).sql("countByQueryStr").session()
                        .selectOne(paramMap);
    }

    public int delete(CsOrderEntity param){
        return commonDao.mapper(CsOrderEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(CsOrderEntity param){
        commonDao.mapper(CsOrderEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(CsOrderEntity param){
        return commonDao.mapper(CsOrderEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

}

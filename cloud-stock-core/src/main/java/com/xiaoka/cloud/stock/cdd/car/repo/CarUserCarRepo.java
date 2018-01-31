package com.xiaoka.cloud.stock.cdd.car.repo;

import com.xiaoka.cloud.stock.cdd.car.entity.CarUserCarEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CarUserCarRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CarUserCarRepo {
     @Resource
     private CommonDao cddDao;

    public List<CarUserCarEntity> select(CarUserCarEntity param){
        return cddDao.mapper(CarUserCarEntity.class).source(SLAVE).sql("select").session()
                     .selectList(param);
    }

    public int delete(CarUserCarEntity param){
        return cddDao.mapper(CarUserCarEntity.class).source(MASTER).sql("delete").session()
                     .update(param);
    }

    public void insert(CarUserCarEntity param){
        cddDao.mapper(CarUserCarEntity.class).source(MASTER).sql("insert").session()
              .insert(param);
    }

    public int updateBySelective(CarUserCarEntity param){
        return cddDao.mapper(CarUserCarEntity.class).source(MASTER).sql("updateBySelective").session()
                     .update(param);
    }

    public List<CarUserCarEntity> selectByModelId(CarUserCarEntity carUserCarEntity) {
        return cddDao.mapper(CarUserCarEntity.class).source(SLAVE).sql("selectByModelId").session()
                     .selectList(carUserCarEntity);
    }
}

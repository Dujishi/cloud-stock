package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicMarkEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CarModelPicMarkRepo
 *
 * @author suqin
 *
 */
@Repository
public class CarModelPicMarkRepo {
     @Resource
     private CommonDao commonDao;

    public List<CarModelPicMarkEntity> select(CarModelPicMarkEntity param){
        return commonDao.mapper(CarModelPicMarkEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public int delete(CarModelPicMarkEntity param){
        return commonDao.mapper(CarModelPicMarkEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(CarModelPicMarkEntity param){
        commonDao.mapper(CarModelPicMarkEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(CarModelPicMarkEntity param){
        return commonDao.mapper(CarModelPicMarkEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

    public List<CarModelPicMarkEntity> selectByList(List<CarModelPicMarkEntity> carModelPartEntities) {
        return commonDao.mapper(CarModelPicMarkEntity.class).source(SLAVE)
                .sql("selectByList").session()
                .selectList(carModelPartEntities);
    }
}

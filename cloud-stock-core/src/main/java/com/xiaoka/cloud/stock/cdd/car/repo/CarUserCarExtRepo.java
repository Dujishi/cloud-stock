package com.xiaoka.cloud.stock.cdd.car.repo;

import com.xiaoka.cloud.stock.cdd.car.entity.CarUserCarExtEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * CarUserCarExtRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class CarUserCarExtRepo {
     @Resource
     private CommonDao cddDao;

    public List<CarUserCarExtEntity> select(CarUserCarExtEntity param){
        return cddDao.mapper(CarUserCarExtEntity.class).source(SLAVE).sql("select").session()
                     .selectList(param);
    }

    public int delete(CarUserCarExtEntity param){
        return cddDao.mapper(CarUserCarExtEntity.class).source(MASTER).sql("delete").session()
                     .update(param);
    }

    public void insert(CarUserCarExtEntity param){
        cddDao.mapper(CarUserCarExtEntity.class).source(MASTER).sql("insert").session()
              .insert(param);
    }

    public int updateBySelective(CarUserCarExtEntity param){
        return cddDao.mapper(CarUserCarExtEntity.class).source(MASTER).sql("updateBySelective").session()
                     .update(param);
    }

	public List<Integer> selectByUserCarIdList(Set<Integer> carIdList) {
        return cddDao.mapper(CarUserCarExtEntity.class).source(SLAVE).sql("selectByUserCarIdList").session()
                     .selectList(new ArrayList<>(carIdList));
	}
}

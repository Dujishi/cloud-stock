package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.VinEpcResultEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * VinEpcResultRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class VinEpcResultRepo {
     @Resource
     private CommonDao commonDao;

    public List<VinEpcResultEntity> select(VinEpcResultEntity param){
        return commonDao.mapper(VinEpcResultEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public List<VinEpcResultEntity> selectByVinAndSource(String vin, String source) {
        VinEpcResultEntity vinEpcResultEntityParam = new VinEpcResultEntity();
        vinEpcResultEntityParam.setVin(vin);
        vinEpcResultEntityParam.setSource(source);
        vinEpcResultEntityParam.setIsValid(true);
        //				vinEpcResultEntityParam.setHasEpc(true);
        //				vinEpcResultEntityParam.setHasModelData(true);
        return select(vinEpcResultEntityParam);
    }

    public int delete(VinEpcResultEntity param){
        return commonDao.mapper(VinEpcResultEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(VinEpcResultEntity param){
        commonDao.mapper(VinEpcResultEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public int updateBySelective(VinEpcResultEntity param){
        return commonDao.mapper(VinEpcResultEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

	public int countInvokedToday(String source) {
        return commonDao.mapper(VinEpcResultEntity.class).source(SLAVE).sql("countInvokedToday").session()
                        .selectOne(source);
	}
}

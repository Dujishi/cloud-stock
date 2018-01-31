package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.VinSampleEntity;
import org.springframework.stereotype.Repository;
import com.xiaoka.freework.container.dao.CommonDao;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * for car brand
 * VinSampleRepo
 *
 * @author auto-generate
 *
 */
@Repository
public class VinSampleRepo {
     @Resource
     private CommonDao commonDao;

    public List<VinSampleEntity> select(VinSampleEntity param){
        return commonDao.mapper(VinSampleEntity.class).source(SLAVE).sql("select").session()
        .selectList(param);
    }

    public int delete(VinSampleEntity param){
        return commonDao.mapper(VinSampleEntity.class).source(MASTER).sql("delete").session()
        .update(param);
    }

    public void insert(VinSampleEntity param){
        commonDao.mapper(VinSampleEntity.class).source(MASTER).sql("insert").session()
        .insert(param);
    }

    public void batchInsertIgnore(List<VinSampleEntity> params){
        commonDao.mapper(VinSampleEntity.class).source(MASTER).sql("batchInsertIgnore").session()
                 .insert(params);
    }

    public int updateBySelective(VinSampleEntity param){
        return commonDao.mapper(VinSampleEntity.class).source(MASTER).sql("updateBySelective").session()
        .update(param);
    }

	public int countVinSample(Map<String, Object> paramMap) {
        return commonDao.mapper(VinSampleEntity.class).source(SLAVE).sql("countVinSample").session()
                        .selectOne(paramMap);
	}

    public List<VinSampleEntity> selectVinSample(Map<String, Object> paramMap) {
        return commonDao.mapper(VinSampleEntity.class).source(SLAVE).sql("selectVinSample").session()
                        .selectList(paramMap);
    }
}

package com.xiaoka.cloud.stock.core.epc.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarModelPicRepo
 *
 * @author suqin
 */
@Repository
public class CarModelPicRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarModelPicEntity> select(CarModelPicEntity param) {
		return commonDao.mapper(CarModelPicEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<CarModelPicEntity> selectByModelIdAndSubAssemblyName(Integer carModelId, List<String> subAssemblyNameList, Integer type){
		Map<String, Object> map = Maps.newHashMap();
		map.put("modelId", carModelId);
		map.put("subAssemblyNameList", subAssemblyNameList);
		map.put("type", type);
		return commonDao.mapper(CarModelPicEntity.class).source(SLAVE).sql("selectByModelIdAndSubAssemblyName").session()
		                .selectList(map);
	}

	public List<CarModelPicEntity> selectGroupPicByModelId(Integer carModelId){
		return commonDao.mapper(CarModelPicEntity.class).source(MASTER).sql("selectGroupPicByModelId").session()
		                .selectList(carModelId);
	}

	public int delete(CarModelPicEntity param) {
		return commonDao.mapper(CarModelPicEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarModelPicEntity param) {
		commonDao.mapper(CarModelPicEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public void batchInsert(List<CarModelPicEntity> param) {
		commonDao.mapper(CarModelPicEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(param);
	}

	public int updateBySelective(CarModelPicEntity param) {
		return commonDao.mapper(CarModelPicEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

}

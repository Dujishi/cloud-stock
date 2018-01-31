package com.xiaoka.cloud.stock.core.epc.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarModelPartRepo
 *
 * @author suqin
 */
@Repository
public class CarModelPartRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarModelPartEntity> select(CarModelPartEntity param) {
		return commonDao.mapper(CarModelPartEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<CarModelPartEntity> getCarPartByCarIdAndPartId(Integer modelId, List<Integer> partIdList){
		Map<String, Object> map = Maps.newHashMap();
		map.put("modelId", modelId);
		map.put("partIdList", partIdList);
		return commonDao.mapper(CarModelPartEntity.class).source(SLAVE).sql("getCarPartByCarIdAndPartId").session()
		                .selectList(map);
	}

	public int delete(CarModelPartEntity param) {
		return commonDao.mapper(CarModelPartEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarModelPartEntity param) {
		commonDao.mapper(CarModelPartEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarModelPartEntity param) {
		return commonDao.mapper(CarModelPartEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public int batchInsert(List<CarModelPartEntity> carModelPartEntityList) {
		return commonDao.mapper(CarModelPartEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(carModelPartEntityList);
	}

	public List<CarModelPartEntity> selectGroupByPicNumAndPicName(Integer modelId) {
		Map<String, Object> map = Maps.newHashMap();
		map.put("modelId", modelId);
		return commonDao.mapper(CarModelPartEntity.class).source(SLAVE).sql("selectGroupByPicNumAndPicName").session()
		                .selectList(map);
	}

	public List<CarModelPartEntity> selectListByCodesAndModelId(List<CarModelPartEntity> carModelPartEntityList) {
		return commonDao.mapper(CarModelPartEntity.class).source(MASTER)
				.sql("selectListByCodesAndModelId").session()
				.selectList(carModelPartEntityList);
	}
}

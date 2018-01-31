package com.xiaoka.cloud.stock.core.epc.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarModelRepo
 *
 * @author suqin
 */
@Repository
public class CarModelRepo {

	private static final Integer DEFAULT_PAGE_NUM  = 1;
	private static final Integer DEFAULT_PAGE_SIZE = 500;
	@Resource
	private CommonDao commonDao;

	public List<CarModelEntity> select(CarModelEntity param) {
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("select").session()
				.selectList(param);
	}

	public List<CarModelEntity> selectByIds(List<Integer> modelIdList) {
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectByIds").session()
				.selectList(modelIdList);
	}

	public int delete(CarModelEntity param) {
		return commonDao.mapper(CarModelEntity.class).source(MASTER).sql("delete").session()
				.update(param);
	}

//	public void insert(CarModelEntity param) {
//		commonDao.mapper(CarModelEntity.class).source(MASTER).sql("insert").session()
//		         .insert(param);
//	}

	public int batchInsert(List<CarModelEntity> params) {
		return commonDao.mapper(CarModelEntity.class).source(MASTER).sql("batchInsert").session()
				.insert(params);
	}

	public int updateBySelective(CarModelEntity param) {
		return commonDao.mapper(CarModelEntity.class).source(MASTER).sql("updateBySelective").session()
				.update(param);
	}

	public List<CarModelEntity> selectGroupBySeries() {
		return commonDao.mapper(CarModelEntity.class).source(MASTER).sql("selectGroupByCarSeries").session()
				.selectList();
	}

	public Integer countDistinctModelId() {
		Integer count = commonDao.mapper(CarModelEntity.class).source(MASTER).sql("countDistinctModelId").session()
				.selectOne();
		return count == null ? 0 : count;
	}

	/**
	 * 分页查询车款信息，已根据车款group by 去重，当前仅返回车款ID。
	 *
	 * @param pageNumber 第几页，从1开始，为空时默认查询第一页
	 * @param pageSize   分页大小，为空时默认500条
	 * @return
	 */
	public List<CarModelEntity> selectGroupByModelId(Integer pageNumber, Integer pageSize, Integer modelId) {
		int                  start    = (pageNumber == null || pageNumber <= 0) ? DEFAULT_PAGE_NUM : pageNumber;
		int                  offset   = (pageSize == null || pageSize < 0) ? DEFAULT_PAGE_SIZE : pageSize;
		Map<String, Integer> paramMap = new HashMap<>();
		paramMap.put("start", (start - 1) * offset);
		paramMap.put("offset", offset);
		paramMap.put("modelId", modelId);
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectGroupByModelId").session()
				.selectList(paramMap);
	}

	public List<CarModelEntity> selectModelsByNames(List<String> modelNames) {
		List<CarModelEntity> carModelEntities = commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectModelsByNames").session()
				.selectList(modelNames);
		if (CollectionUtils.isEmpty(carModelEntities)) {
			return Collections.emptyList();
		}
		return carModelEntities;
	}

	public List<CarModelEntity> selectAllCarModels(List<Integer> ids) {
		return commonDao.mapper(CarModelEntity.class).source(MASTER).sql("selectAllCarModels")
				.session()
				.selectList(ids);
	}

	public List<CarModelEntity> selectListBySeries(Integer seriesId) {
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectListBySeries")
				.session()
				.selectList(seriesId);
	}

	public List<CarModelEntity> selectModelYearBySeries(Integer brandId, String series) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("brandId", brandId);
		param.put("series", series);
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectModelYearBySeries")
		                .session()
		                .selectList(param);
	}

	public List<CarModelEntity> selectBrandByModelName(String modelCondition) {
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectBrandByModelName")
		                .session()
		                .selectList(modelCondition);
	}

	public List<CarModelEntity> selectModelYearByModelName(String modelCondition, Integer brandId) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("modelCondition", modelCondition);
		param.put("brandId", brandId);
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectModelYearByModelName")
		                .session()
		                .selectList(param);
	}

	public List<CarModelEntity> selectModelBySearch(String modelCondition, Integer brandId, String modelYear) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("modelCondition", modelCondition);
		param.put("brandId", brandId);
		param.put("modelYear", modelYear);
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectModelBySearch")
		                .session()
		                .selectList(param);
	}

	public List<CarModelEntity> selectByModelName(String modelCondition, List<Integer> brandIds){
		Map<String, Object> map = Maps.newHashMap();
		map.put("modelCondition", modelCondition);
		map.put("list", brandIds);
		return commonDao.mapper(CarModelEntity.class).source(SLAVE).sql("selectByModelName")
		                .session()
		                .selectList(map);
	}
}

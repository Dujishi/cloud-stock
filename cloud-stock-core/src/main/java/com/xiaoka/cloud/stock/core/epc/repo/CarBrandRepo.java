package com.xiaoka.cloud.stock.core.epc.repo;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarBrandEntity;
import com.xiaoka.freework.cache.annotation.ServiceCache;
import com.xiaoka.freework.container.dao.CommonDao;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.MASTER;
import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * CarBrandRepo
 *
 * @author suqin
 */
@Repository
public class CarBrandRepo {
	@Resource
	private CommonDao commonDao;

	public List<CarBrandEntity> select(CarBrandEntity param) {
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("select").session()
		                .selectList(param);
	}

	public List<CarBrandEntity> selectByNames(List<String> param) {
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectByNames").session()
		                .selectList(param);
	}

	public int delete(CarBrandEntity param) {
		return commonDao.mapper(CarBrandEntity.class).source(MASTER).sql("delete").session()
		                .update(param);
	}

	public void insert(CarBrandEntity param) {
		commonDao.mapper(CarBrandEntity.class).source(MASTER).sql("insert").session()
		         .insert(param);
	}

	public int updateBySelective(CarBrandEntity param) {
		return commonDao.mapper(CarBrandEntity.class).source(MASTER).sql("updateBySelective").session()
		                .update(param);
	}

	public int batchInsert(List<CarBrandEntity> carBrandEntityList) {
		return commonDao.mapper(CarBrandEntity.class).source(MASTER).sql("batchInsert").session()
		         .insert(carBrandEntityList);
	}

	@ServiceCache(expire = 60 * 20)
	public List<CarBrandEntity> selectAllBrands() {
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectAllBrands").session()
				.selectList();
	}

	@ServiceCache(expire = 60 * 20)
	public List<CarBrandEntity> selectAllBrandsBySort() {
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectAllBrandsBySort").session()
		                .selectList();
	}

	public List<CarBrandEntity> selectBrandFirstLetter() {
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectBrandFirstLetter").session()
		                .selectList();
	}

	public List<CarBrandEntity> selectBrandFirstLetterByBrandIds(List<Integer> brandIds){
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectBrandFirstLetterByBrandIds").session()
		                .selectList(brandIds);
	}

	public List<CarBrandEntity> selectBrandByFirstLetter(String firstLetter) {
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectBrandByFirstLetter").session()
		                .selectList(firstLetter);
	}

	public List<CarBrandEntity> selectBrandByIdsAndFirstLetter(String firstLetter, List<Integer> brandIds) {
		Map<String, Object> param = Maps.newHashMap();
		param.put("firstLetter", firstLetter);
		param.put("brandIds", brandIds);
		return commonDao.mapper(CarBrandEntity.class).source(SLAVE).sql("selectBrandByIdsAndFirstLetter").session()
		                .selectList(param);
	}
}

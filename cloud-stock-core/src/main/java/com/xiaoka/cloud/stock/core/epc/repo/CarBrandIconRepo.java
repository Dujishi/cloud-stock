package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarBrandIconEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.xiaoka.freework.data.datasource.RoutingDataSourceDecision.Source.SLAVE;

/**
 * Do something
 *
 * @author gancao 2017/11/21 3:49
 * @see [相关类/方法]
 * @since [版本号]
 */
@Repository
public class CarBrandIconRepo {

	@Resource
	private CommonDao commonDao;

	public String selectIconByName(String name) {
		//先全匹配
		String icon = commonDao.mapper(CarBrandIconEntity.class).source(SLAVE).sql("getBrandIconByName").session().selectOne(name);
		if (StringUtils.isBlank(icon)) {//模糊匹配
			List<CarBrandIconEntity> entityList = commonDao.mapper(CarBrandIconEntity.class).source(SLAVE).sql("getBrandIconByLikeName").session()
			                                               .selectList(name);
			if (CollectionUtils.isNotEmpty(entityList)) {
				icon = entityList.get(0).getIcon();
			}
		}
		return icon;
	}

	public Map<String, String> selectIconByNames(List<String> names) {
		Map<String, String> iconMapResult = new HashMap<>();
		//先全匹配
		List<CarBrandIconEntity> carBrandIconEntityList = commonDao.mapper(CarBrandIconEntity.class).source(SLAVE).sql("getBrandIconByNames").session().selectList(names);
		if (carBrandIconEntityList != null) {
			iconMapResult.putAll(carBrandIconEntityList.stream().collect(Collectors.toMap(CarBrandIconEntity::getName, CarBrandIconEntity::getIcon)));
		}
		List<String> unmatchedNames = names.stream().filter(name -> !iconMapResult.containsKey(name)).collect(Collectors.toList());
		if (CollectionUtils.isNotEmpty(unmatchedNames)) {
			for (String unmatchedName : unmatchedNames) {
				List<CarBrandIconEntity> entityList = commonDao.mapper(CarBrandIconEntity.class).source(SLAVE).sql("getBrandIconByLikeName").session()
				                                               .selectList(unmatchedName);
				if (CollectionUtils.isNotEmpty(entityList)) {
					iconMapResult.put(unmatchedName, entityList.get(0).getIcon());
				}
			}
		}

		return iconMapResult;
	}
}

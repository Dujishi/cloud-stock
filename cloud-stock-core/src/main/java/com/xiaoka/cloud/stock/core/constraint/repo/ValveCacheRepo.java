package com.xiaoka.cloud.stock.core.constraint.repo;

import com.xiaoka.cloud.stock.core.constraint.repo.entity.ValveCacheEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.data.datasource.RoutingDataSourceDecision;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * @author zhouze
 * @date 2017/12/5
 * @see [相关类/方法]
 * @since [版本号]
 */
@Repository
public class ValveCacheRepo {

	@Resource
	private CommonDao commonDao;

	public ValveCacheEntity selectByKey(String key){
		return commonDao.mapper(ValveCacheEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("selectByKey")
				.session()
				.selectOne(key);
	}

	public Integer insertValveCacheEntity(ValveCacheEntity valveCacheEntity) {
		choppingString(valveCacheEntity);
		return commonDao.mapper(ValveCacheEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("insertValveCacheEntity")
				.session()
				.insert(valveCacheEntity);
	}

	public Integer updateValveCacheEntity(ValveCacheEntity valveCacheEntity) {
		return commonDao.mapper(ValveCacheEntity.class)
				.source(RoutingDataSourceDecision.Source.MASTER)
				.sql("updateValveCacheEntity")
				.session()
				.insert(valveCacheEntity);
	}

	/**
	 * 截断字符串
	 * 异常场景需要截断满足当前方法执行
	 *
	 * @param valveCacheEntity
	 */
	public void choppingString(ValveCacheEntity valveCacheEntity) {
		if (null != valveCacheEntity
				&& StringUtils.isNotBlank(valveCacheEntity.getParamJson())
				&& valveCacheEntity.getParamJson().length() > 500) {
			valveCacheEntity.setParamJson(valveCacheEntity.getParamJson().substring(0, 499));
		}
		if (null != valveCacheEntity
				&& StringUtils.isNotBlank(valveCacheEntity.getMethodSign())
				&& valveCacheEntity.getMethodSign().length() > 500) {
			valveCacheEntity.setMethodSign(valveCacheEntity.getMethodSign().substring(0, 499));
		}
	}

}

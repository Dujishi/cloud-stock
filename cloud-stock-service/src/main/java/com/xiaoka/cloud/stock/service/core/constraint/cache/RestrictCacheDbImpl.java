package com.xiaoka.cloud.stock.service.core.constraint.cache;

import com.xiaoka.cloud.stock.core.constraint.repo.ValveCacheRepo;
import com.xiaoka.cloud.stock.core.constraint.repo.entity.ValveCacheEntity;
import com.xiaoka.cloud.stock.service.core.constraint.RestrictCacheService;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 在使用的时候，先做足够的性能测试（该实现未做全面测试）
 *
 * @author zhouze
 * @date 2017/12/5
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("restrictCacheDbImpl")
public class RestrictCacheDbImpl implements RestrictCacheService {

	@Resource
	ValveCacheRepo valveCacheRepo;

	@Override
	public ValveCachePo queryRestrictValve(String key) {
		if (StringUtils.isBlank(key)) {
			return null;
		}
		ValveCacheEntity valveCacheEntity = valveCacheRepo.selectByKey(key);
		return BeanUtils.transform(ValveCachePo.class, valveCacheEntity);
	}

	@Override
	public void writeRestrictValve(String key, ValveCachePo newCache) {
		ValveCacheEntity hasExistedValve  = valveCacheRepo.selectByKey(key);
		ValveCacheEntity valveCacheEntity = BeanUtils.transform(ValveCacheEntity.class, newCache);
		valveCacheEntity.setUpdateTime(null);
		if (null == hasExistedValve) {
			valveCacheRepo.insertValveCacheEntity(valveCacheEntity);
		} else {
			valveCacheRepo.updateValveCacheEntity(valveCacheEntity);
		}
	}


}

package com.xiaoka.cloud.stock.service.core.constraint;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.service.core.constraint.cache.ValveCachePo;
import org.apache.commons.lang3.StringUtils;

/**
 * @author zhouze
 * @date 2017/12/2
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface RestrictCacheService {

	int DEFAULT_MOD = 16;

	/**
	 * 根据key查询限制方法的阀门信息
	 *
	 * @param key
	 * @return
	 */
	ValveCachePo queryRestrictValve(String key);


	/**
	 * key与新的阀门信息值写入缓存
	 *
	 * @param key
	 * @param newCache
	 */
	void writeRestrictValve(String key, ValveCachePo newCache);

	/**
	 * 根据key取模
	 *
	 * @param key
	 * @param mod
	 * @return
	 */
	default int moduloMod(String key, int mod) {
		Preconditions.checkArgument(StringUtils.isNotBlank(key), "illegal 'key' param");
		Preconditions.checkArgument(mod > 0, "mod can not <= 0");

		return Math.abs(key.hashCode() % mod);
	}

	/**
	 * 根据key取模,默认%16
	 *
	 * @param key
	 * @return
	 */
	default int module(String key) {
		return moduloMod(key, DEFAULT_MOD);
	}

}

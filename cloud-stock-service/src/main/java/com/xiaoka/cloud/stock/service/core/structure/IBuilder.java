package com.xiaoka.cloud.stock.service.core.structure;

/**
 * @author zhouze
 * @date 2017/12/26
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface IBuilder<T> {

	/**
	 * 对象builder
	 *
	 * @return
	 */
	T build();
}

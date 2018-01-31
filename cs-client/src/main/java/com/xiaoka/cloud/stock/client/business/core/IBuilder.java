package com.xiaoka.cloud.stock.client.business.core;

/**
 * @author zhouze
 * @date 2018/1/17
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

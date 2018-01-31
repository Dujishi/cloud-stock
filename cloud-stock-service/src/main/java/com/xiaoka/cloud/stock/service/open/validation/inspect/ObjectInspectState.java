package com.xiaoka.cloud.stock.service.open.validation.inspect;

import com.xiaoka.cloud.stock.service.open.validation.constraint.LoggerCall;

/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
public abstract class ObjectInspectState {

	/**
	 * 对参数进行参数检查校验
	 *
	 * @param object 参数对象
	 */
	abstract void checkObjectParameter(Object object);

	/**
	 * 日志输出
	 *
	 * @param fieldName
	 * @param flag
	 * @return
	 */
	LoggerCall doLog(String fieldName, Integer flag) {
		return type -> type.doLog(fieldName, flag);
	}

	/**
	 * 日志输出
	 *
	 * @param fieldName
	 * @return
	 */
	LoggerCall doLog(String fieldName) {
		return doLog(fieldName, null);
	}
}

package com.xiaoka.cloud.stock.service.open.validation.constraint;

import com.xiaoka.cloud.stock.service.open.validation.constant.LoggerTypeEnum;

/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface LoggerCall {

	/**
	 * 输出日志
	 *
	 * @param type
	 * @return
	 */
	String log(LoggerTypeEnum type);

}

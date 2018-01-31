package com.xiaoka.cloud.stock.client.business.core;

/**
 * Html 解析
 *
 * @author zhouze
 * @date 2018/1/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface HtmlParser<C> {


	/**
	 * 解析内容
	 *
	 * @param content 待解析的内容
	 * @return
	 */
	@SuppressWarnings("unchecked")
	<T> T parse(C content);

}

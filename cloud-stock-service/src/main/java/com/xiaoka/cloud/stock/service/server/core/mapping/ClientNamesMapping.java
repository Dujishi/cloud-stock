package com.xiaoka.cloud.stock.service.server.core.mapping;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 商家和客户端名称映射数据
 *
 * @author zhouze
 * @date 2018/1/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ClientNamesMapping {

	private static Map<Integer, String> data = Maps.newHashMap();

	public static Map<Integer, String> mapData() {
		data.put(1, "test_client");
		return data;
	}
}

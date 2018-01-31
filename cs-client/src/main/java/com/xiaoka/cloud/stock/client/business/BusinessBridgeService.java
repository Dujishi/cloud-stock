package com.xiaoka.cloud.stock.client.business;

import java.util.List;

import com.xiaoka.cloud.stock.client.business.dto.StorageDto;

/**
 * 业务桥接服务
 *
 * @author zhouze
 * @date 2018/1/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface BusinessBridgeService {


	/**
	 * 根据零件码获取库存数据
	 *
	 * @param codes
	 */
	List<StorageDto> getStorageByCodes(List<String> codes);


	/**
	 * //todo
	 * 创建配货单
	 *
	 * @param t
	 * @param <T>
	 */
	<T> void createGoodsOrder(T t);

}

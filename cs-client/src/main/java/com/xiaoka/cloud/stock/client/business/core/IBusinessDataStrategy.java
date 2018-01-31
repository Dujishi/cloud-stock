package com.xiaoka.cloud.stock.client.business.core;

import java.util.List;

import com.xiaoka.cloud.stock.client.business.dto.StorageDto;

/**
 * @author zhouze
 * @date 2018/1/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface IBusinessDataStrategy {

	/**
	 * 查询库存数据
	 *
	 * @param codes
	 * @return
	 */
	List<StorageDto> selectStorageByCodes(List<String> codes);



}

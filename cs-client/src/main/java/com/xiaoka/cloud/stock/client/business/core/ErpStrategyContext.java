package com.xiaoka.cloud.stock.client.business.core;

import java.util.List;

import com.xiaoka.cloud.stock.client.business.dto.StorageDto;
import org.springframework.stereotype.Component;

/**
 * @author zhouze
 * @date 2018/1/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ErpStrategyContext {

	private IBusinessDataStrategy businessDataStrategy;

	public ErpStrategyContext(IBusinessDataStrategy businessStrategy) {
		this.businessDataStrategy = businessStrategy;
	}

	/**
	 * 根据零件码查询库存数据
	 *
	 * @param codes
	 * @return
	 */
	public List<StorageDto> selectStorageByCodes(List<String> codes) {
		return businessDataStrategy.selectStorageByCodes(codes);
	}

}


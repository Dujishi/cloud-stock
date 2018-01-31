package com.xiaoka.cloud.stock.service.server.service;

import com.xiaoka.cloud.stock.service.server.core.parser.dto.RongyiStockDto;

import java.util.List;

/**
 * @author zhouze
 * @date 2018/1/10
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface RongYiErpDataService {

	/**
	 * 根据零件码列表查询库存数据
	 *
	 * @param codes
	 * @return
	 */
	List<RongyiStockDto> selectStockByConditions(List<String> codes);
}

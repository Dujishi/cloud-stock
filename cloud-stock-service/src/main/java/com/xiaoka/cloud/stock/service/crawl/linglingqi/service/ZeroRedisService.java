package com.xiaoka.cloud.stock.service.crawl.linglingqi.service;

import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;

import java.util.List;
import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2017/12/25 11:08
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ZeroRedisService {

	List<String> getSubList();

	/**
	 *
	 * @param type 1是有效帐号 2异常检测帐号
	 * @return
	 */
	Map<Integer, AccountMsgDto> getAccountMap(Integer type);

	void putAccount(Integer supplierId, AccountMsgDto accountMsgDto,Integer type);

	void removeAccount(Integer supplierId, Integer type);

	AccountMsgDto getAccountBySupplierId(Integer supplierId, Integer type);

}

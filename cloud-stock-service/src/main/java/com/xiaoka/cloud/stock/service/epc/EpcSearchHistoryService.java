package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSearchLogEntity;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

/**
 * 搜索历史服务
 *
 * @author gancao 2017/11/22 16:23
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface EpcSearchHistoryService {

	/**
	 * 搜索历史
	 * @param searchType
	 * @return
	 */
	List<CloudSearchLogEntity> searchHistory(Integer searchType, CloudSupplierUserDto userDto);

	/**
	 * 插入搜索内容
	 * @param searchType
	 * @param content
	 */
	void insertSearchHistory(Integer searchType, String content, CloudSupplierUserDto userDto);
}

package com.xiaoka.cloud.stock.service.open;

import com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam;

/**
 * @author zhouze
 * @date 2017/11/7
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface StockOpenApiService {

	/**
	 * 初始化数据
	 *
	 * @param supplierSyncParam 接口数据参数
	 * @param appId             提供数据方的APPID，用于鉴权和寻找具体供应商
	 */
	void initData(SupplierSyncParam supplierSyncParam, String appId);

	/**
	 * 增量处理数据
	 *
	 * @param supplierSyncParam
	 * @param appId
	 */
	void bulkData(SupplierSyncParam supplierSyncParam, String appId);
}

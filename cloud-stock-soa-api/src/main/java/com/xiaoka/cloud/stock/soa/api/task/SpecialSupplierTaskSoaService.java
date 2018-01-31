package com.xiaoka.cloud.stock.soa.api.task;

/**
 * @author zhouze
 * @date 2018/1/2
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface SpecialSupplierTaskSoaService {

	/**
	 * 写入供应商存在库存的数据
	 *
	 * @param shopId
	 */
	void doSaveStockPartAdapterCarModels(Integer shopId);
}

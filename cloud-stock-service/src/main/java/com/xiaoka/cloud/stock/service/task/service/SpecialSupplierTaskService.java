package com.xiaoka.cloud.stock.service.task.service;

/**
 * @author zhouze
 * @date 2018/1/2
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface SpecialSupplierTaskService {


	/**
	 * 查询供应商的存在库存的数据的适配车型，
	 * 并保存在 part_model 表中，此时partModel不分表
	 *
	 * @param shopId
	 */
	void runSearchExistsStockPartAdapterCarModels(Integer shopId);
}

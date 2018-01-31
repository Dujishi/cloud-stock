package com.xiaoka.cloud.stock.soa.impl.task;

import com.xiaoka.cloud.stock.service.task.service.SpecialSupplierTaskService;
import com.xiaoka.cloud.stock.soa.api.task.SpecialSupplierTaskSoaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author zhouze
 * @date 2018/1/2
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("specialSupplierTaskSoaService")
public class SpecialSupplierTaskSoaServiceImpl implements SpecialSupplierTaskSoaService {

	@Resource
	SpecialSupplierTaskService specialSupplierTaskService;

	@Override
	public void doSaveStockPartAdapterCarModels(Integer shopId) {
		specialSupplierTaskService.runSearchExistsStockPartAdapterCarModels(shopId);
	}
}

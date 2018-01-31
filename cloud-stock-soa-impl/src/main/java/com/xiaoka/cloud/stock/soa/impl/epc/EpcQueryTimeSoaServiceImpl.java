package com.xiaoka.cloud.stock.soa.impl.epc;

import com.xiaoka.cloud.stock.service.epc.EpcQueryService;
import com.xiaoka.cloud.stock.soa.impl.test.EpcQueryTimeSoaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Do something
 *
 * @author gancao 2017/12/2 10:22
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("epcQueryTimeSoaService")
public class EpcQueryTimeSoaServiceImpl implements EpcQueryTimeSoaService {

	@Resource
	private EpcQueryService epcQueryService;

	@Override
	public String getCarAssemblyDtoList(Integer carModelId, Integer categoryId, String categoryName, Integer type) {
		long start = System.currentTimeMillis();
		return null;
	}
}

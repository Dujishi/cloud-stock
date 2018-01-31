package com.xiaoka.cloud.stock.soa.impl.epc;

import com.xiaoka.cloud.stock.soa.api.epc.EpcAggregateSoaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2018/1/19 15:06
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("epcAggregateSoaService")
public class EpcAggregateSoaServiceImpl implements EpcAggregateSoaService {

	@Resource
	private EpcAggregateSoaService epcAggregateSoaService;

	@Override
	public long aggregateSuperEpcByToday() {
		return epcAggregateSoaService.aggregateSuperEpcByToday();
	}

	@Override
	public Map<String, Long> aggregateDetailSuperEpcByToday() {
		return epcAggregateSoaService.aggregateDetailSuperEpcByToday();
	}

	@Override
	public Map<Integer, Long> aggregateZeroEpcByToday() {
		return epcAggregateSoaService.aggregateZeroEpcByToday();
	}
}

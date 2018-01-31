package com.xiaoka.cloud.stock.soa.impl.epc;

import com.xiaoka.cloud.stock.service.epc.EpcRedisService;
import com.xiaoka.cloud.stock.soa.api.epc.EpcQueryRedisSoaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Do something
 *
 * @author gancao 2017/12/2 9:51
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("epcQueryRedisSoaService")
public class EpcQueryRedisSoaServiceImpl implements EpcQueryRedisSoaService {

	@Resource
	private EpcRedisService epcRedisService;

	@Override
	public String openRepoFilter() {
		return epcRedisService.openRepoFilter();
	}

	@Override
	public String openSuperEpcFilter() {
		return epcRedisService.openSuperEpcFilter();
	}

	@Override
	public void closeFilter() {
		epcRedisService.closeFilter();
	}
}

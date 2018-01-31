package com.xiaoka.cloud.stock.soa.impl.epc;

import com.xiaoka.cloud.stock.service.crawl.superepc.StandardInfoCrawlService;
import com.xiaoka.cloud.stock.soa.api.epc.SuperEpcSoaService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by gejiankui on 2017/11/18.
 */
@Service("superEpcSoaService")

public class SuperEpcSoaServiceImpl implements SuperEpcSoaService{
	@Resource
	private StandardInfoCrawlService standardInfoCrawlService;

	@Override
	public void crawlStandardPartAndAssemblyInfo() {
		standardInfoCrawlService.crawlStandardPartAndAssemblyInfo();
	}
}

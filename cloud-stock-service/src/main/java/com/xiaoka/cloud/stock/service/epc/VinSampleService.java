package com.xiaoka.cloud.stock.service.epc;

import java.util.List;

public interface VinSampleService {

	void createVinSampleForAllCarBrand();

	void createVinSample(List<Integer> carBrandIdList);

	//	@Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
	void checkVinSample();
}

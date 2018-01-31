package com.xiaoka.cloud.stock.soa.impl.test;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.cloud.stock.soa.api.test.ErpNatureTestSoaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouze
 * @date 2017/11/27
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("erpNatureTestSoaService")
public class ErpNatureTestSoaServiceImpl implements ErpNatureTestSoaService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ErpNatureTestSoaServiceImpl.class);

	@Resource
	CloudPartRepo cloudPartRepo;

	@Override
	public void testInsertCount() {

		// 单线程测试 1 000 000 条数据插入

		long vt = System.currentTimeMillis();

		doPlanA();

		long ct1 = System.currentTimeMillis();

		doPlanB();

		long ct2 = System.currentTimeMillis();


		LOGGER.info("===== Plan[A]耗时-> " + (ct1 - vt) + "ms");
		LOGGER.info("===== Plan[B]耗时-> " + (ct2 - ct1) + "ms");

	}

	@Override
	public void doPlanAThreads(Integer init) {
		long vt = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(50);

		if (init == null) {
			init = 22000000;
		}

		for (int y = 1; y <= 2000; y++) {
			int     finalY    = y;
			Integer finalInit = init;
			executorService.execute(() -> {
				doInsertBatchList(finalY, 500, finalInit);
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOGGER.info("===== 多线程下Plan[A] 中断异常 ");
		}

		long ct1 = System.currentTimeMillis();
		LOGGER.info("===== 多线程下Plan[A]耗时-> " + (ct1 - vt) + "ms");
	}

	@Override
	public void doPlanBThreads(Integer init) {

		long vt = System.currentTimeMillis();

		ExecutorService executorService = Executors.newFixedThreadPool(50);
		if (init == null) {
			init = 12000000;
		}
		for (int y = 1; y <= 10000; y++) {
			int     finalY    = y;
			Integer finalInit = init;
			executorService.execute(() -> {
				doInsertBatchList(finalY, 100, finalInit);
			});
		}


		executorService.shutdown();
		try {
			executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			LOGGER.info("===== 多线程下Plan[B] 中断异常 ");
		}


		long ct1 = System.currentTimeMillis();
		LOGGER.info("===== 多线程下Plan[B]耗时-> " + (ct1 - vt) + "ms");
	}

	@Override
	public Integer testStockSlave() {
		List<String>          codes             = Lists.newArrayList("s1");
		List<Integer>         shopIds           = Lists.newArrayList(1);
		return cloudPartRepo.queryStockPartCountByShopIdsAndOeNoes(codes, shopIds);
	}


	private void doPlanB() {
		for (int y = 1; y <= 10000; y++) {
			doInsertBatchList(y, 100, 12000000);
		}
	}

	private void doPlanA() {
		for (int y = 1; y <= 2000; y++) {
			doInsertBatchList(y, 500, 22000000);
		}
	}

	private void doInsertBatchList(int y, int max, int init) {
		List<CloudPartEntity> list = Lists.newArrayList();
		for (int i = 1; i <= max; i++) {
			CloudPartEntity c = new CloudPartEntity();
			c.setcPartId(String.valueOf(y * i + init));
			c.setcDepotId(String.valueOf(i));
			c.setcSupplierId("test");
			c.setShopId(1);
			c.setOeNo("s1");
			c.setPartName("partName");
			c.setPartBrand("brand1");
			c.setOriginPlace("原厂");
			c.setManufacturer("sadas");
			c.setcOperateMode(1);
			c.setcCreateTime("2017-01-01");
			c.setcUpdateTime("2017-01-01");
			list.add(c);
		}
		cloudPartRepo.insertCloudPartEntities(list);
	}


}
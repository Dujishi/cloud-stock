package com.xiaoka.cloud.stock.soa.impl.crawl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.ZeroDataCrawlTaskService;
import com.xiaoka.cloud.stock.soa.api.crawl.ZeroDataCrawlerSoaService;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Do something
 *
 * @author gancao 2017/12/21 14:09
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("zeroDataCrawlerSoaService")
public class ZeroDataCrawlerSoaServiceImpl implements ZeroDataCrawlerSoaService {
	private static final Logger logger = LoggerFactory.getLogger(ZeroDataCrawlerSoaServiceImpl.class);
	private Map<String, Boolean> accountStatusMap = Maps.newConcurrentMap();
	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	private ZeroDataCrawlTaskService zeroDataCrawlTaskService;

	@Override
	public void crawlAllData(List<Integer> ids) {
		Preconditions.checkArgument(CollectionUtils.isNotEmpty(ids), "帐号id不能为空");
		List<ZeroAccountEntity> zeroAccountEntityList = Lists.newArrayList();
		ids.forEach(id -> {
			ZeroAccountEntity accountEntity = zeroChooseService.getZeroAccountEntityById(id);
			if (Objects.nonNull(accountEntity) && StringUtils.isNotBlank(accountEntity.getIp()) && Objects.nonNull(accountEntity.getPort())){
				//帐号存在，且配置了代理IP信息
				zeroAccountEntityList.add(accountEntity);
			}
		});
		if (CollectionUtils.isNotEmpty(zeroAccountEntityList)){
			logger.info("待爬取帐号信息:{}", Jackson.mobile().writeValueAsString(zeroAccountEntityList));
			ExecutorService executorService = Executors.newFixedThreadPool(zeroAccountEntityList.size());
			zeroAccountEntityList.forEach(entity -> {
				if (Objects.isNull(accountStatusMap.get(entity.getPhone())) || !accountStatusMap.get(entity.getPhone())){
					accountStatusMap.put(entity.getPhone(), true);
					executorService.execute(() -> zeroDataCrawlTaskService.crawlTask(entity));
				}
			});
			executorService.shutdown();
			try {
				executorService.awaitTermination(12, TimeUnit.HOURS);//12小时后结束
				zeroAccountEntityList.forEach(entity -> accountStatusMap.put(entity.getPhone(), false));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}

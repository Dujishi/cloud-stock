package com.xiaoka.cloud.stock.service.crawl.linglingqi.task;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;

/**
 * Do something
 *
 * @author gancao 2018/1/2 14:19
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ZeroDataCrawlTaskService {

	void crawlTask();

	void crawlTask(ZeroAccountEntity accountEntity);

	void complete(ZeroAccountEntity accountEntity);
}

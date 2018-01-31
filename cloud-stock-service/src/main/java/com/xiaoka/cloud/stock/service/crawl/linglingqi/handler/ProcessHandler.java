package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import us.codecraft.webmagic.Page;

/**
 * Do something
 *
 * @author gancao 2017/12/13 11:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ProcessHandler {
	void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity);

}

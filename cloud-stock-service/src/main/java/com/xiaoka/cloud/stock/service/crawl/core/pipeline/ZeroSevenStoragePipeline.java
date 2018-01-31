package com.xiaoka.cloud.stock.service.crawl.core.pipeline;

import org.springframework.stereotype.Service;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * @author zhouze
 * @date 2017/12/11
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroSevenStoragePipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {

		String a = "";
	}
}

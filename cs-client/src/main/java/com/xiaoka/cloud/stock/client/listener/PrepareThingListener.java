package com.xiaoka.cloud.stock.client.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author zhouze
 * @date 2018/1/11
 * @see [相关类/方法]
 * @since [版本号]
 */
@Component
public class PrepareThingListener implements ApplicationListener<ContextRefreshedEvent> {

	private static volatile int i = 1;

	private static final Logger LOGGER = LoggerFactory.getLogger(PrepareThingListener.class);

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		synchronized (PrepareThingListener.class) {
			if (i == 1) {
				doSomeThing(event);
				i++;
			}
		}
	}

	private void doSomeThing(ContextRefreshedEvent event) {
		// 处理逻辑

	}
}

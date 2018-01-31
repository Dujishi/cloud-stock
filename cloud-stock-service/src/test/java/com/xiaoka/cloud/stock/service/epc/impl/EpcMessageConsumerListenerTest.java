package com.xiaoka.cloud.stock.service.epc.impl;

import com.xiaoka.cloud.stock.service.mqmessage.consumer.EpcMessageConsumerListener;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcMessageEvent;
import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * Do something
 *
 * @author gancao 2017/11/25 16:26
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml"
})
public class EpcMessageConsumerListenerTest extends ContainerTest {

	@Resource
	EpcMessageConsumerListener epcMessageConsumerListener;

	@Test
	public void test() {
		EpcMessageEvent event = new EpcMessageEvent();
		event.setMessageType(6);

		EpcNotifyDto epcNotifyDto = new EpcNotifyDto();
		epcNotifyDto.setModelId(562);
		epcNotifyDto.setPartCode("11111714541");
		event.setEpcNotifyDto(epcNotifyDto);

		epcMessageConsumerListener.onApplicationEvent(event);
	}
}

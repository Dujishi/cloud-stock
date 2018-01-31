package com.xiaoka.cloud.stock.service.mqmessage.consumer.superepc;

import com.xiaoka.cloud.stock.service.mqmessage.dto.EpcNotifyDto;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;
import java.util.Objects;

/**
 * 定义抽象的处理器
 *
 * @author gancao 2017/11/23 14:46
 * @see [相关类/方法]
 * @since [版本号]
 */
public abstract class AbstractMessageHandlerService {

	//分布式集群锁等待时间
	protected static final Integer WAIT_TIME = 3;
	//多级同纬度的链路
	private List<AbstractMessageHandlerService> nextAbstractHandlerList;

	public void setNextAbstractHandlerList(List<AbstractMessageHandlerService> nextAbstractHandlerList) {
		this.nextAbstractHandlerList = nextAbstractHandlerList;
	}

	/**
	 * 由于EPC链路任务较为复杂，下级链路的任务可能是个批量操作所以采用获取下级链路任务源的方式作二次批处理
	 *
	 * @param epcNotifyDto
	 */
	public void startMessageHandler(EpcNotifyDto epcNotifyDto) {
		List<EpcNotifyDto> epcNotifyDtoList = null;
		if (Objects.nonNull(epcNotifyDto)) {
			epcNotifyDtoList = messageHandler(epcNotifyDto);
		}
		if (CollectionUtils.isNotEmpty(nextAbstractHandlerList) && CollectionUtils.isNotEmpty(epcNotifyDtoList)) {
			epcNotifyDtoList.forEach(dto -> nextAbstractHandlerList.forEach(nextAbstractHandler -> nextAbstractHandler.startMessageHandler(dto)));
		}
	}

	/**
	 * 当前处理器处理消息，并返回下一链路器所需的操作源
	 *
	 * @param epcNotifyDto
	 */
	abstract protected List<EpcNotifyDto> messageHandler(EpcNotifyDto epcNotifyDto);

}

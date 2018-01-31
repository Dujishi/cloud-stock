package com.xiaoka.cloud.stock.client.business.impl;

import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.xiaoka.cloud.stock.client.business.PermissionBridgeService;
import com.xiaoka.cloud.stock.client.business.dto.StrategyDto;
import com.xiaoka.cloud.stock.client.cap.DumpPacketService;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @author zhouze
 * @date 2018/1/15
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PermissionBridgeServiceImpl implements PermissionBridgeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionBridgeServiceImpl.class);

	@Resource
	private DumpPacketService dumpPacketService;

	private static volatile int    idx      = 0;
	private final           Object SYNC_OBJ = new Object();

	private static final String PROPERTY_NAME = "os.name";
	private static final String OS_NAME       = "Windows";

	/**
	 * 非线程安全
	 */
	private StrategyDto strategy;

	@Override
	public boolean registerStrategy(StrategyDto strategyDto) {
		if (null == strategyDto) {
			return false;
		}
		synchronized (SYNC_OBJ) {
			strategy = strategyDto;
			if (idx > 1) {
				LOGGER.info("检测到已经在运行...");
				return true;
			}
			idx++;
		}
		if (System.getProperty(PROPERTY_NAME).contains(OS_NAME)) {
			Map<String, String> maps   = strategyDto.getAddInfo();
			String              filter = null;
			if (null != maps && maps.size() > 0) {
				//filter = "host cs.ddyc.com";
				filter = maps.get("filter");
			}
			try {
				dumpPacketService.dumpPackets(filter);
			} catch (PcapNativeException e) {
				LOGGER.error("dump出现错误,filter:{}", filter, e);
			}
		}
		return true;
	}

	@Override
	public boolean reRegister() {
		synchronized (SYNC_OBJ) {
			idx = 0;
		}
		return true;
	}

	@Override
	public StrategyDto getStrategy() {
		synchronized (SYNC_OBJ) {
			return strategy;
		}
	}


}

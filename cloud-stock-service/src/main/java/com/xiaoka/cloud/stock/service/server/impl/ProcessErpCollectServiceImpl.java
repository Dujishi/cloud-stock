package com.xiaoka.cloud.stock.service.server.impl;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.service.server.dto.ErpCollectDataDto;
import com.xiaoka.cloud.stock.service.server.dto.ErpUserAuthDto;
import com.xiaoka.cloud.stock.service.server.dto.TransferDto;
import com.xiaoka.cloud.stock.service.server.service.ProcessErpCollectService;
import com.xiaoka.freework.common.net.tunnel.TunnelData;
import com.xiaoka.freework.common.net.tunnel.TunnelServer;
import com.xiaoka.freework.common.net.tunnel.vo.TunnelDataImpl;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * 向客户端发送请求，并采集数据
 *
 * @author zhouze
 * @date 2017/12/25
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ProcessErpCollectServiceImpl implements ProcessErpCollectService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessErpCollectServiceImpl.class);

	@Resource
	private TunnelServer tunnelServer;

	/**
	 * 通用方法
	 */
	private static final String UNIVERSAL_LISTENER = "UNIVERSAL_METHOD";

	/**
	 * 获取登录的认证信息
	 */
	private static final String AUTH_LISTENER = "ERP_USER_AUTH";

	@Override
	public ErpCollectDataDto doneCollectDataFromErp(String channel, TransferDto transferDto) {
		if (StringUtils.isBlank(channel) || null == transferDto) {
			return null;
		}
		LOGGER.info("listener -> {}", UNIVERSAL_LISTENER);
		try {
			TunnelDataImpl data = TunnelDataImpl.builder().listener(UNIVERSAL_LISTENER)
					.data(JSON.toJSONString(transferDto))
					.build();
			Future<TunnelData> response = tunnelServer.caller(channel).call(data);
			TunnelData         respData = response.get();
			if (respData.getStatus().isRespSuccess()) {
				LOGGER.info("系列 {}，客户端返回：{}", respData.getSequence(), respData.getDataString());
				return new ErpCollectDataDto.InnerBuilder()
						.setChannel(channel)
						.setResponse(respData.getDataString())
						.build();
			} else {
				LOGGER.info("状态 {}，返回：{}", respData.getStatus(), respData.getDataString());
			}
		} catch (Exception e) {
			LOGGER.info("采集ERP数据与客户端连接出现异常", e);
		}
		return null;
	}

	@Override
	public ErpUserAuthDto getUserSign(String channel) {
		if (StringUtils.isBlank(channel)) {
			return null;
		}
		LOGGER.info("listener -> {}", AUTH_LISTENER);

		try {
			TunnelDataImpl data = TunnelDataImpl.builder().listener(AUTH_LISTENER)
					.data("1")
					.build();
			Future<TunnelData> response = tunnelServer.caller(channel).call(data);
			TunnelData         respData = response.get();
			if (respData.getStatus().isRespSuccess()) {
				LOGGER.info("系列 {}，客户端返回：{}", respData.getSequence(), respData.getDataString());
				return Jackson.base().readValue(respData.getDataString(), ErpUserAuthDto.class);
			} else {
				LOGGER.info("状态 {}，返回：{}", respData.getStatus(), respData.getDataString());
			}
		} catch (Exception e) {
			LOGGER.info("采集ERP数据与客户端连接出现异常", e);
		}
		return null;
	}

}

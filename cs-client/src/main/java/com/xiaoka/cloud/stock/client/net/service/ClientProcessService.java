package com.xiaoka.cloud.stock.client.net.service;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.client.cap.ReadPacketService;
import com.xiaoka.cloud.stock.client.cap.dto.ErpUserAuthDto;
import com.xiaoka.cloud.stock.client.net.contants.RequestTypeEnum;
import com.xiaoka.cloud.stock.client.net.dto.TransferDto;
import com.xiaoka.cloud.stock.client.net.utils.HttpUtil;
import com.xiaoka.freework.common.net.tunnel.TunnelData;
import com.xiaoka.freework.common.net.tunnel.listener.TunnelListener;
import com.xiaoka.freework.utils.json.Jackson;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhouze
 * @date 2018/1/11
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ClientProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientProcessService.class);

	@Resource
	ReadPacketService readPacketService;

	@TunnelListener("UNIVERSAL_METHOD")
	public TunnelData handleServerSite(TunnelData data) {
		LOGGER.info("收到服务端请求 -> " + data.getSequence() + "：" + data.getDataString());

		String request = data.getDataString();
		if (StringUtils.isBlank(request)) {
			return data.buildError("request is null");
		}

		TransferDto transferDto = JSON.parseObject(request, TransferDto.class);
		if (null == transferDto) {
			return data.buildError("transferDto is null");
		}

		String response = "";
		try {
			if (Objects.equals(RequestTypeEnum.GET.toString(), transferDto.getType())) {
				response = HttpUtil.doGet(transferDto.getUrl(), transferDto.getHeaders());
			} else if (Objects.equals(RequestTypeEnum.POST.toString(), transferDto.getType())) {
				response = HttpUtil.doPost(transferDto.getUrl(), transferDto.getRequestBody(), transferDto.getHeaders());
			}
			return data.buildSuccess(response);
		} catch (Exception e) {
			LOGGER.info("请求出现错误", e);
			return data.buildError("请求异常:" + e.getMessage());
		}
	}

	@TunnelListener("ERP_USER_AUTH")
	public TunnelData handleAuthSite(TunnelData data) {
		LOGGER.info("收到服务端请求 -> " + data.getSequence() + "：" + data.getDataString());

		try {
			//获取cookie和sId
			ErpUserAuthDto erpUserAuthDto = readPacketService.readPacket();
			String         response       = Jackson.base().writeValueAsString(erpUserAuthDto);
			return data.buildSuccess(response);
		} catch (Exception e) {
			LOGGER.info("请求出现错误", e);
			return data.buildError("请求异常:" + e.getMessage());
		}
	}
}

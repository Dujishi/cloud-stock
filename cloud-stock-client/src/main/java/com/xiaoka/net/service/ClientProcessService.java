package com.xiaoka.net.service;

import com.alibaba.fastjson.JSON;
import com.xiaoka.freework.common.net.tunnel.TunnelData;
import com.xiaoka.freework.common.net.tunnel.listener.TunnelListener;
import com.xiaoka.constants.RequestTypeEnum;
import com.xiaoka.net.dto.TransferDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.xiaoka.utils.HttpUtil;

import java.util.Objects;

/**
 * @author zhouze
 * @date 2017/12/26
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ClientProcessService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClientProcessService.class);

	@TunnelListener("UNIVERSAL_METHOD")
	public TunnelData handleServerSite(TunnelData data) {
		LOGGER.info("收到服务端数据 -> " + data.getSequence() + "：" + data.getDataString());

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
			LOGGER.info("请求错误", e);
			return data.buildError("请求异常:" + e.getMessage());
		}
	}
}

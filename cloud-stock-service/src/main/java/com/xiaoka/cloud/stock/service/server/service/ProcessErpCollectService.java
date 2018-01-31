package com.xiaoka.cloud.stock.service.server.service;

import com.xiaoka.cloud.stock.service.server.dto.ErpCollectDataDto;
import com.xiaoka.cloud.stock.service.server.dto.ErpUserAuthDto;
import com.xiaoka.cloud.stock.service.server.dto.TransferDto;

/**
 * 与客户端通信发起请求
 *
 * @author zhouze
 * @date 2017/12/25
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ProcessErpCollectService {

	/**
	 * 向客户端发送请求，并采集数据
	 *
	 * @param channel     通道标识，用以确定哪个供应商
	 * @param transferDto 请求对象
	 * @return
	 */
	ErpCollectDataDto doneCollectDataFromErp(String channel, TransferDto transferDto);

	/**
	 * todo: 临时获取erp用户的登录认证信息
	 *
	 * @param channel
	 * @return
	 */
	ErpUserAuthDto getUserSign(String channel);

}

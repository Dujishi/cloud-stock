package com.xiaoka.cloud.stock.service.crawl.core.log;

import com.xiaoka.cloud.stock.service.crawl.core.log.dto.EpcLogDto;
import com.xiaoka.freework.help.log.DistributedLogger;
import com.xiaoka.freework.utils.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2018/1/4 13:29
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class EpcLogUtil {

	private static final String LOG_INDEX_CRAWL = "crawl";
	private static final String LOG_TYPE_REQUEST_URL = "requestUrl";//爬取的地址
	private static final String LOG_TYPE_IP = "ip";//爬取ip
	private static final String LOG_TYPE_REAL_IP = "realIp";//真实ip
	private static final String LOG_TYPE_SOURCE = "source";//爬取来源
	private static final String LOG_TYPE_EXEC_TIME= "execTime";//请求执行时间
	private static final String LOG_TYPE_REQUEST_TYPE = "requestType";//请求类型 007或者正时
	private static final String LOG_TYPE_STATUS = "status";//请求状态
	private static final String LOG_TYPE_MESSAGE = "message";//内容
	private static final String LOG_TYPE_ARGS= "args";//请求参数
	private static final String LOG_TYPE_USER_ID = "userId";//用户id
	private static final String LOG_TYPE_SUPPLIER_ID = "supplierId";//供应商id


	public static void logTrace(EpcLogDto epcLogDto){
		Map<String, Object> fields = new HashMap<>();
		fields.put(LOG_TYPE_REQUEST_URL, epcLogDto.getUrl());
		if (StringUtils.isNotBlank(epcLogDto.getIp())){
			fields.put(LOG_TYPE_IP, epcLogDto.getIp());
		}
		if (StringUtils.isNotBlank(epcLogDto.getRealIp())){
			fields.put(LOG_TYPE_REAL_IP, epcLogDto.getRealIp());
		}
		if (Objects.nonNull(epcLogDto.getSource())){
			fields.put(LOG_TYPE_SOURCE, epcLogDto.getSource());
		}
		if (StringUtils.isNotBlank(epcLogDto.getMessage())){
			fields.put(LOG_TYPE_MESSAGE, epcLogDto.getMessage());
		}
		if (StringUtils.isNotBlank(epcLogDto.getStatus())){
			fields.put(LOG_TYPE_STATUS, epcLogDto.getStatus());
		}
		if (StringUtils.isNotBlank(epcLogDto.getArgs())){
			fields.put(LOG_TYPE_ARGS, epcLogDto.getArgs());
		}
		if (Objects.nonNull(epcLogDto.getUserId())){
			fields.put(LOG_TYPE_USER_ID, epcLogDto.getUserId());
		}
		if (Objects.nonNull(epcLogDto.getSupplierId())){
			fields.put(LOG_TYPE_SUPPLIER_ID, epcLogDto.getSupplierId());
		}
		fields.put(LOG_TYPE_EXEC_TIME, epcLogDto.getExecTime());
		fields.put(LOG_TYPE_REQUEST_TYPE, epcLogDto.getType());
		Utils.get(DistributedLogger.class).log(LOG_INDEX_CRAWL, fields);
	}
}

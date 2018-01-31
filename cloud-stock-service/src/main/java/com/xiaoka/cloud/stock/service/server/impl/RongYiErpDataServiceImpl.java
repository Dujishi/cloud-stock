package com.xiaoka.cloud.stock.service.server.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.service.server.constants.RequestTypeEnum;
import com.xiaoka.cloud.stock.service.server.core.mapping.ClientNamesMapping;
import com.xiaoka.cloud.stock.service.server.core.parser.HtmlParser;
import com.xiaoka.cloud.stock.service.server.core.parser.dto.RongyiStockDto;
import com.xiaoka.cloud.stock.service.server.dto.ErpCollectDataDto;
import com.xiaoka.cloud.stock.service.server.dto.ErpUserAuthDto;
import com.xiaoka.cloud.stock.service.server.dto.TransferDto;
import com.xiaoka.cloud.stock.service.server.service.ProcessErpCollectService;
import com.xiaoka.cloud.stock.service.server.service.RongYiErpDataService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhouze
 * @date 2018/1/10
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class RongYiErpDataServiceImpl implements RongYiErpDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RongYiErpDataServiceImpl.class);

	@Resource
	ProcessErpCollectService processErpCollectService;
	@Resource
	HtmlParser               RongyiErpParser;

	private static final String STOCK_URL = "http://60.191.43.19:8090/HzryERP/nStkMnt01B.jsp?KsPid=%s&KsCustom=&KsAppVerson=2016/&usergrp=06&wh_loc=&bin=&row=&wt=&stk=&vnd_id=&item1_code=&ref_type=&ref_date1=&ref_date2=&item_brand=&spec=&item2_code=&item_code=&src_id=%s&item_desc=&xtype=&prod=&KsTime=2018010134443&KsSetSrcMode=Replace";

	@Override
	public List<RongyiStockDto> selectStockByConditions(List<String> codes) {
		if (CollectionUtils.isEmpty(codes)) {
			return Collections.emptyList();
		}
		long t1 = System.currentTimeMillis();
		LOGGER.info("准备获取校验信息->{}", t1);
		ErpUserAuthDto erpUserAuthDto = processErpCollectService.getUserSign(ClientNamesMapping.mapData().get(1));
		long           t2             = System.currentTimeMillis();
		LOGGER.info("获取校验信息完成->{}ms，结果为:{}", t2 - t1, JSON.toJSONString(erpUserAuthDto));
		if (null == erpUserAuthDto) {
			return Collections.emptyList();
		}
		String               ksPid   = erpUserAuthDto.getSpecialId();
		List<RongyiStockDto> results = Lists.newArrayList();
		codes.forEach(code -> {
			String      oeNo        = code.trim();
			String      url         = String.format(STOCK_URL, ksPid, oeNo);
			String      channel     = ClientNamesMapping.mapData().get(1);
			TransferDto transferDto = new TransferDto(channel, url, RequestTypeEnum.GET.toString());
			String      cookies     = erpUserAuthDto.getCookies();
			if (StringUtils.isNotBlank(cookies)) {
				Map<String, String> headers = Maps.newHashMap();
				headers.put("Cookie", cookies);
				transferDto.setHeaders(headers);
			}
			ErpCollectDataDto erpCollectData = processErpCollectService.doneCollectDataFromErp(channel, transferDto);
			if (erpCollectData == null) {
				return;
			}
			String content = erpCollectData.getResponse();
			if (StringUtils.isBlank(content)) {
				return;
			}
			List<RongyiStockDto> netResults = (List<RongyiStockDto>) RongyiErpParser.parse(content);
			netResults.forEach(p -> {
				if (null != p && StringUtils.isNotBlank(p.getcOeNo())) {
					if (Objects.equals(p.getcOeNo().trim(), oeNo)) {
						results.add(p);
					}
				}
			});
		});
		LOGGER.info("根据参数:{}获取到的数据结果为:{}", JSON.toJSONString(codes), JSON.toJSONString(results));
		return results;
	}
}

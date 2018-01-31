package com.xiaoka.cloud.stock.client.business.core.rongyi.strategy;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.client.business.core.IBusinessDataStrategy;
import com.xiaoka.cloud.stock.client.business.core.rongyi.dto.RongyiStockDto;
import com.xiaoka.cloud.stock.client.business.core.rongyi.parser.RongyiStockParser;
import com.xiaoka.cloud.stock.client.business.dto.StorageDto;
import com.xiaoka.cloud.stock.client.cap.ReadPacketService;
import com.xiaoka.cloud.stock.client.cap.dto.ErpUserAuthDto;
import com.xiaoka.cloud.stock.client.net.utils.HttpUtil;
import com.xiaoka.cloud.stock.client.utils.DateFormatUtil;
import org.apache.commons.lang3.StringUtils;
import org.pcap4j.core.PcapNativeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author zhouze
 * @date 2018/1/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RongyiDataStrategy implements IBusinessDataStrategy {

	private static final Logger logger = LoggerFactory.getLogger(RongyiDataStrategy.class);

	private static final String STOCK_URL = "http://60.191.43.19:8090/HzryERP/sales01.jsp?KsPid=%s&KsCustom=&KsAppVerson=2016/&item_code=&item1_code=&item2_code=&item_desc=&src_id=%s" +
			"&src_desc=&item_brand=&type=&spec=&prod=&sort=&wh_loc=" +
			"&gen_code=&rpv_code=&wscode=6&wsflag=1&clnt_id=&cat_id=" +
			"&usergrp=" +
			"&sal_qbeg=%s" +
			"&sal_qend=%s" +
			"&am_flag=&trx_type=SW&whgrp=0&KsTime=2018018103956&KsSetSrcMode=Replace";


	@Override
	public List<StorageDto> selectStorageByCodes(List<String> codes) {
		if (CollectionUtils.isEmpty(codes)) {
			logger.info("codes：{}为空", codes);
			return Collections.emptyList();
		}

		ReadPacketService readPacketService = new ReadPacketService();
		ErpUserAuthDto    erpUserAuthDto    = null;
		try {
			erpUserAuthDto = readPacketService.readPacket();
		} catch (PcapNativeException e) {
			logger.error("获取当前验证信息出现错误", e);
		}

		if (null == erpUserAuthDto) {
			logger.info("未获取到验证信息，请检查是否有开启截包功能");
			return Collections.emptyList();
		}

		List<StorageDto> results             = Lists.newArrayList();
		ErpUserAuthDto   finalErpUserAuthDto = erpUserAuthDto;
		String           ksPid               = finalErpUserAuthDto.getSpecialId();
		if (StringUtils.isBlank(ksPid)) {
			logger.info("未获取到验证信息-KsPid，请检查是否有开启截包功能");
			return Collections.emptyList();
		}
		codes.forEach(code -> {
			if (StringUtils.isEmpty(code)){
				return;
			}
			String              date    = DateFormatUtil.date2String(new Date(), DateFormatUtil.TIME_FORMAR_RY);
			String              oeNo    = code.trim();
			String              url     = String.format(STOCK_URL, ksPid, oeNo, date, date).replace("KsAppVerson=2016/","KsAppVerson=2016%2F").replace(" ","%20");
			String              cookies = finalErpUserAuthDto.getCookies();
			Map<String, String> headers = Maps.newHashMap();
			if (StringUtils.isNotBlank(cookies)) {
				headers.put("Cookie", cookies);
			}
			String content = "";
			try {
				content = HttpUtil.doGet(url, headers);
			} catch (Exception e) {
				logger.info("Http请求发生意外", e);
			}
			List<RongyiStockDto> stockDtos = new RongyiStockParser().parse(content);
			if (CollectionUtils.isEmpty(stockDtos)) {
				logger.info("未查询到任何库存数据");
				return;
			}
			stockDtos.forEach(p -> {
				StorageDto storage = buildStorageDto(code, p);
				results.add(storage);
			});
		});
		return results;
	}

	private StorageDto buildStorageDto(String code, RongyiStockDto p) {
		StorageDto storage = new StorageDto();
		storage.setcPartId(p.getcPartId());
		storage.setCode1(p.getCode1());
		storage.setPartCode(code);
		storage.setName(p.getName());
		storage.setCarModel(p.getCarModel());
		storage.setOriginPlace(p.getOriginPlace());
		storage.setStandard(p.getStandard());
		storage.setUnit(p.getUnit());
		storage.setBrand(p.getBrand());
		storage.setDepot(p.getDepot());
		storage.setBalanceCount(p.getBalanceCount());
		storage.setCostPrice(p.getCostPrice());
		storage.setTradePrice(p.getTradePrice());
		storage.setInsurerPrice(p.getInsurancePrice());
		storage.setRepairPrice(p.getRepairFactoryPrice());
		storage.setMaintainPrice(p.getRepairStationPrice());
		if (StringUtils.isNotBlank(p.getBalanceCount())) {
			try {
				BigDecimal val = new BigDecimal(p.getBalanceCount());
				if (val.compareTo(BigDecimal.ZERO) > 0) {
					storage.setFlag(1);
				} else {
					storage.setFlag(2);
				}
			} catch (NumberFormatException e) {
				logger.error("格式转化错误", e);
				storage.setFlag(3);
			}
		}
		return storage;
	}
}

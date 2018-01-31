package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroPartPriceDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.PartPriceResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataCollectService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.util.ZeroUrlUtil;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;
import java.util.List;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_BRAND;

/**
 * 零件价格处理
 *
 * @author gancao 2017/12/18 14:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PartPriceProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(PartPriceProcessHandler.class);

	@Resource
	private ZeroDataCollectService zeroDataCollectService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		String url = page.getUrl().get();
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)) {
			logger.info("请求url:{}未获取到数据,登录失效", url);
			return;
		}
		String uri = url.substring(url.indexOf("?") + 1);
		ZeroResp<List<PartPriceResp>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<PartPriceResp>>>() {
		});
		List<PartPriceResp> partPriceRespList = zeroResp.getData();
		if (CollectionUtils.isNotEmpty(partPriceRespList)) {
			this.savePartPrice(partPriceRespList, uri);
		}else {
			logger.info("未获取到数据:{}", zeroResp.getMsg());
		}
	}

	private void savePartPrice(List<PartPriceResp> partPriceRespList, String uri) {
		String brand = ZeroUrlUtil.getValue(uri, PARAM_BRAND);
		List<ZeroPartPriceDto> partPriceDtoList = Lists.newArrayList();
		//拼装零件价格信息
		partPriceRespList.forEach(resp ->
				resp.getData().forEach(p -> {
					ZeroPartPriceDto zeroPartPriceDto = BeanUtils.transform(ZeroPartPriceDto.class, p);
					zeroPartPriceDto.setBrand(brand);
					partPriceDtoList.add(zeroPartPriceDto);
				})
		);
		zeroDataCollectService.savePartPrices(partPriceDtoList);
	}

}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroAdapterCarModelDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.PartCarResp;
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
import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_PART;

/**
 * 零件适用车型处理
 *
 * @author gancao 2017/12/18 14:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PartCarProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(PartCarProcessHandler.class);

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
		ZeroResp<List<List<PartCarResp>>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<List<PartCarResp>>>>() {
		});
		List<List<PartCarResp>> partCarRespList = zeroResp.getData();
		if (CollectionUtils.isNotEmpty(partCarRespList)) {
			this.savePartCar(partCarRespList, uri);
		}else {
			logger.info("未获取到数据:{}", zeroResp.getMsg());
		}
	}

	private void savePartCar(List<List<PartCarResp>> partCarRespList, String uri) {
		String brand = ZeroUrlUtil.getValue(uri, PARAM_BRAND);
		String part = ZeroUrlUtil.getValue(uri, PARAM_PART);
		List<ZeroAdapterCarModelDto> carModelDtoList = Lists.newArrayList();
		//拼装适用车型数据
		partCarRespList.forEach(resp ->
				resp.forEach(p -> {
					ZeroAdapterCarModelDto zeroAdapterCarModelDto = BeanUtils.transform(ZeroAdapterCarModelDto.class, p);
					zeroAdapterCarModelDto.setCarModel(p.getCarsModel());
					zeroAdapterCarModelDto.setPid(part);
					zeroAdapterCarModelDto.setBrand(brand);
					carModelDtoList.add(zeroAdapterCarModelDto);
				})
		);
		zeroDataCollectService.saveAdapterCarModels(carModelDtoList);
	}
}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroCarModelDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarModelChooseResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataCollectService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroRedisService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.util.ZeroUrlUtil;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_BRAND;
import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.REQUEST_URL;

/**
 * 车型选择过程处理
 *
 * @author gancao 2017/12/13 17:59
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelChooseProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CarModelChooseProcessHandler.class);
	@Resource
	private ZeroDataCollectService zeroDataCollectService;
	@Resource
	private ZeroRedisService zeroRedisService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		String url = page.getUrl().get();
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)){
			logger.info("请求url:{}未获取到数据,登录失效", url);
			return;
		}
		ZeroResp<List<CarModelChooseResp>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<CarModelChooseResp>>>() {
		});
		//得到选择的内容
		List<CarModelChooseResp> carModelChooseRespList = zeroResp.getData();
		if (CollectionUtils.isEmpty(carModelChooseRespList)) {
			logger.info("未获取到数据:{}", zeroResp.getMsg());
			return;
		}
		CarModelChooseResp checkCarModelChooseResp = null;
		if ("选择车型".equals(zeroResp.getTitle())) {
			checkCarModelChooseResp = carModelChooseRespList.stream().filter(p -> StringUtils.deleteWhitespace(chooseEntity.getCarModel()).
					equals(StringUtils.deleteWhitespace(p.getName()))).findFirst().orElse(null);
		} else if ("选择年份".equals(zeroResp.getTitle())) {
			checkCarModelChooseResp = carModelChooseRespList.stream().filter(p -> StringUtils.deleteWhitespace(chooseEntity.getYear()).
					equals(StringUtils.deleteWhitespace(p.getName()))).findFirst().orElse(null);
		} else if ("选择发动机".equals(zeroResp.getTitle())) {
			checkCarModelChooseResp = carModelChooseRespList.stream().filter(p -> StringUtils.deleteWhitespace(chooseEntity.getEngine()).
					equals(StringUtils.deleteWhitespace(p.getName()))).findFirst().orElse(null);
		} else if ("选择变速箱".equals(zeroResp.getTitle())) {
			checkCarModelChooseResp = carModelChooseRespList.stream().filter(p -> StringUtils.deleteWhitespace(chooseEntity.getGearBox()).
					equals(StringUtils.deleteWhitespace(p.getName()))).findFirst().orElse(null);
		} else if ("选择市场".equals(zeroResp.getTitle())) {
			checkCarModelChooseResp = carModelChooseRespList.stream().filter(p -> StringUtils.deleteWhitespace(chooseEntity.getMarket()).
					equals(StringUtils.deleteWhitespace(p.getName()))).findFirst().orElse(null);
		}
		logger.info("选中的车型项为:{}", Jackson.mobile().writeValueAsString(checkCarModelChooseResp));
		/*if ("选择车型".equals(zeroResp.getTitle())){
			String uri = url.substring(url.indexOf("?") + 1);
			List<String> value = zeroRedisService.getSubList();
			if (CollectionUtils.isNotEmpty(value) && value.size() == 2){
				carModelChooseRespList = carModelChooseRespList.subList(Integer.valueOf(value.get(0)), Integer.valueOf(value.get(1)));
			}
			this.saveModel(carModelChooseRespList, uri);
			logger.info("截取的车型数据为:{}", Jackson.mobile().writeValueAsString(carModelChooseRespList));
		}*/
		if (Objects.nonNull(checkCarModelChooseResp)){
			this.requestChain(Collections.singletonList(checkCarModelChooseResp), page);
		}
	}

	private void saveModel(List<CarModelChooseResp> carModelChooseRespList, String uri) {
		String brand = ZeroUrlUtil.getValue(uri, PARAM_BRAND);
		List<ZeroCarModelDto> carModelDtoList = Lists.newArrayList();
		carModelChooseRespList.forEach(dto -> {
			ZeroCarModelDto zeroCarModelDto = new ZeroCarModelDto();
			zeroCarModelDto.setBrand(brand);
			zeroCarModelDto.setCarModel(dto.getName());
			carModelDtoList.add(zeroCarModelDto);
		});
		//车型本地化
		zeroDataCollectService.saveCarModels(carModelDtoList);
	}

	private void requestChain(List<CarModelChooseResp> carModelChooseRespList, Page page) {
		List<String> urlList = Lists.newArrayList();
		carModelChooseRespList.forEach(resp -> {
			if ("1".equals(resp.getHasNext())) {
				urlList.add(REQUEST_URL + resp.getUri());
			} else {
				if (StringUtils.isNotBlank(resp.getKeys())) {
					urlList.add(REQUEST_URL + "/ppycars/carsinfos?"+ resp.getKeys());
				}
			}
		});
		page.addTargetRequests(urlList);
	}

}

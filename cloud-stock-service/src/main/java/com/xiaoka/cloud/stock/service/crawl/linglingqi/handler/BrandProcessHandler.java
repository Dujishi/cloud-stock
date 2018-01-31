package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroBrandDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarBrandResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataCollectService;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/12/13 11:52
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class BrandProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(BrandProcessHandler.class);

	@Resource
	private ZeroDataCollectService zeroDataCollectService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)){
			logger.info("请求url:{}未获取到数据,登录失效", page.getUrl().get());
			return;
		}
		logger.info("品牌获取的数据为:{}", result);
		ZeroResp<List<CarBrandResp>> dto = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<CarBrandResp>>>() {
		});
		//得到品牌列表
		List<CarBrandResp> respList = dto.getData();
		if (CollectionUtils.isNotEmpty(respList)) {
			List<ZeroBrandDto> zeroBrandDtoList = Lists.newArrayList();
			/*respList.forEach(resp -> {
				ZeroBrandDto zeroBrandDto = new ZeroBrandDto();
				zeroBrandDto.setBrand(resp.getBrand());
				zeroBrandDto.setName(resp.getName());
				zeroBrandDto.setUri(resp.getImg());
				zeroBrandDtoList.add(zeroBrandDto);
			});
			zeroDataCollectService.saveBrands(zeroBrandDtoList);*/
			//车型品牌
			//page.addTargetRequest("https://www.007vin.com/cars/show?brand=maserati");
		}else {
			logger.info("未获取到数据:{}", dto.getMsg());
		}
	}
}

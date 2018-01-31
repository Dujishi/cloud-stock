package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import us.codecraft.webmagic.Page;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.REQUEST_URL;

/**
 * 车型选择处理
 *
 * @author gancao 2017/12/18 13:29
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelCheckProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CarModelCheckProcessHandler.class);
	private static final List<String> CAR_CHOOSE_CONTENT = ImmutableList.of("车型","品牌","年份","发动机","变速箱","市场");

	@Resource
	private ZeroChooseService zeroChooseService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		logger.info("车型定款选择，帐号:{}", page);
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)){
			logger.info("请求url:{}未获取到数据,登录失效", page.getUrl().get());
			return;
		}
		ZeroResp<Object> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<Object>>() {
		});
		if (Objects.nonNull(zeroResp)) {
			/*ZeroCarModelChooseEntity entity = this.transformZeroCarModelChooseEntity(zeroResp);
			if (Objects.nonNull(entity)){
				//插入车型数据
				this.saveModel(entity);
			}*/
			if (StringUtils.isNotBlank(zeroResp.getUri())) {
				//下一级获取主组信息
				page.addTargetRequest(REQUEST_URL + "/ppycars/group?" + zeroResp.getUri());
			}
		}
	}
	
	private ZeroCarModelChooseEntity transformZeroCarModelChooseEntity(ZeroResp<Object> zeroResp) {
		//得到选择的内容
		List<String> content = zeroResp.getMains();
		if (CollectionUtils.isEmpty(content)) {
			return null;
		}
		Map<String, String> map = Maps.newHashMap();
		content.forEach(p -> {
			String[] values = p.split(":");
			if (values.length > 1) {
				map.put(StringUtils.deleteWhitespace(values[0]), StringUtils.deleteWhitespace(values[1]));
				if (!CAR_CHOOSE_CONTENT.contains(StringUtils.deleteWhitespace(values[0]))){
					logger.error("{}车型选择项未设置", StringUtils.deleteWhitespace(values[0]));
				}
			}
		});
		if (!map.isEmpty()) {
			ZeroCarModelChooseEntity entity = new ZeroCarModelChooseEntity();
			entity.setCarModel(map.get("车型"));
			entity.setBrand(map.get("品牌"));
			entity.setYear(map.get("年份"));
			entity.setEngine(map.get("发动机"));
			entity.setGearBox(map.get("变速箱"));
			entity.setMarket(map.get("市场"));
			entity.setAuth(zeroResp.getAuth());
			entity.setUri(zeroResp.getUri());
			return entity;
		}
		return null;
	}

	private void saveModel(ZeroCarModelChooseEntity entity) {
		//车型数据爬取用于后面爬取及分析后面相关数据
		zeroChooseService.insert(entity);
	}

}

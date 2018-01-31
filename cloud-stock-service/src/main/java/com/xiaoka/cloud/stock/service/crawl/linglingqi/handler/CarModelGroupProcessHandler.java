package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarGroupChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarModelGroupChooseResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
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
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_AUTH;
import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.REQUEST_URL;

/**
 * 车型主组处理
 *
 * @author gancao 2017/12/18 14:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelGroupProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CarModelGroupProcessHandler.class);

	@Resource
	private ZeroChooseService zeroChooseService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		String url = page.getUrl().get();
		String uri = url.substring(url.indexOf("?") + 1);
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)){
			logger.info("请求url:{}未获取到数据,登录失效", url);
			return;
		}
		ZeroResp<List<CarModelGroupChooseResp>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<CarModelGroupChooseResp>>>() {
		});
		List<CarModelGroupChooseResp> carModelGroupRespList = zeroResp.getData();
		if (CollectionUtils.isEmpty(carModelGroupRespList)){
			System.out.println("未获取到数据:"+zeroResp.getMsg());
			logger.info("未获取到数据:{}", zeroResp.getMsg());
			if (Objects.equals(2, zeroResp.getCode()) && StringUtils.isNotBlank(zeroResp.getMsg()) && zeroResp.getMsg().contains("抱歉")){
				//车型没有主组数据
				chooseEntity.setSuccessStatus(1);
				zeroChooseService.updateZeroCarModelChooseEntity(chooseEntity);
			}
			return;
		}
		//获取车型对应的auth并查询该auth对应的车型选择项
		ZeroCarModelChooseEntity entity = zeroChooseService.getZeroCarModelChooseEntityByAuth(ZeroUrlUtil.getValue(uri, PARAM_AUTH));
		if (Objects.nonNull(entity)) {
			List<ZeroCarGroupChooseEntity> groupChooseEntityList = Lists.newArrayList();
			carModelGroupRespList.forEach(resp -> {
				ZeroCarGroupChooseEntity zeroCarGroupChooseEntity = BeanUtils.transform(ZeroCarGroupChooseEntity.class, resp);
				zeroCarGroupChooseEntity.setcId(entity.getId());
				zeroCarGroupChooseEntity.setUri("auth=" + resp.getUriParam().getAuth() + "&code=" + resp.getUriParam().getCode()
						+ "&carstype=" + resp.getUriParam().getCarstype());
				groupChooseEntityList.add(zeroCarGroupChooseEntity);
			});
			//批量插入车型的主组
			zeroChooseService.batchInsertZeroCarGroup(groupChooseEntityList);
			this.requestChain(carModelGroupRespList, page);
		}
	}

	private void requestChain(List<CarModelGroupChooseResp> carModelGroupRespList, Page page) {
		List<String> urlList = Lists.newArrayList();
		carModelGroupRespList.forEach(resp -> {
			if (Objects.nonNull(resp.getUriParam())) {
				urlList.add(REQUEST_URL + "/ppycars/subgroup?" + "auth=" + resp.getUriParam().getAuth() + "&code=" + resp.getUriParam().getCode()
						+ "&carstype=" + resp.getUriParam().getCarstype());
			}
		});
		page.addTargetRequests(urlList);
	}

}

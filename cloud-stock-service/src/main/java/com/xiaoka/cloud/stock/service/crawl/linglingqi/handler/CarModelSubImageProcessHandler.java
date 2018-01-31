package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarSubGroupChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroSubGroupImgDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarModelSubGroupImageResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
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
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_AUTH;

/**
 * 零件组图片处理
 *
 * @author gancao 2017/12/20 14:50
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelSubImageProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CarModelSubImageProcessHandler.class);

	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	private ZeroDataCollectService zeroDataCollectService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		String url = page.getUrl().get();
		String uri = url.substring(url.indexOf("?") + 1);
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)) {
			logger.info("请求url:{}未获取到数据,登录失效", url);
			return;
		}
		ZeroResp<CarModelSubGroupImageResp> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<CarModelSubGroupImageResp>>() {
		});
		CarModelSubGroupImageResp imageResp = zeroResp.getData();
		if (Objects.nonNull(imageResp)) {
			saveImageMsg(imageResp, uri);
		}else {
			logger.info("未获取到数据:{}", zeroResp.getMsg());
		}
	}

	private void saveImageMsg(CarModelSubGroupImageResp imageResp, String uri) {
		if(CollectionUtils.isEmpty(imageResp.getMapData())){
			logger.info("uri:{}未获取到图片打点信息", uri);
			return;
		}
		//先通过uri获取auth然后通过auth获取零件组
		ZeroCarSubGroupChooseEntity entity = zeroChooseService.getZeroCarSubGroupChooseEntityByAuth(ZeroUrlUtil.getValue(uri, PARAM_AUTH));
		if (Objects.nonNull(entity)) {
			List<ZeroSubGroupImgDto> imgDtoList = Lists.newArrayList();
			BigDecimal width = new BigDecimal(imageResp.getW());
			BigDecimal height = new BigDecimal(imageResp.getH());
			imageResp.getMapData().forEach(resp -> {
				if (CollectionUtils.isNotEmpty(resp) && resp.size() == 5) {
					ZeroSubGroupImgDto imgDto = new ZeroSubGroupImgDto();
					imgDto.setGroupId(entity.getInnerGroupId());
					imgDto.setSubGroupId(entity.getInnerSubGroupId());
					imgDto.setGroupName(entity.getGroupName());
					imgDto.setSubgroup(entity.getSubGroup());
					imgDto.setSubGroupName(entity.getSubGroupName());
					imgDto.setImgUrl(imageResp.getImgUrl());
					imgDto.setHeight(height);
					imgDto.setWidth(width);
					imgDto.setX1(new BigDecimal(resp.get(0)));
					imgDto.setY1(new BigDecimal(resp.get(1)));
					imgDto.setX2(new BigDecimal(resp.get(2)));
					imgDto.setY2(new BigDecimal(resp.get(3)));
					imgDto.setItId(resp.get(4));
					imgDtoList.add(imgDto);
				}
			});
			//批量保存零件组图片信息
			zeroDataCollectService.saveSubGroupImgs(imgDtoList);
		}
	}

}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarGroupChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarSubGroupChooseEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroGroupDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroSubGroupDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.CarModelSubGroupChooseResp;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_AUTH;
import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.REQUEST_URL;

/**
 * 主组下零件组处理
 *
 * @author gancao 2017/12/18 14:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelSubGroupProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CarModelSubGroupProcessHandler.class);

	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	private ZeroDataCollectService zeroDataCollectService;

	@Override
	public void handler(Page page, String phone, ZeroCarModelChooseEntity chooseEntity) {
		String url = page.getUrl().get();
		String uri = url.substring(url.indexOf("?") + 1);
		String result = page.getJson().get();
		if (StringUtils.isBlank(result)){
			logger.info("请求url:{}未获取到数据,登录失效", url);
			return;
		}
		ZeroResp<List<CarModelSubGroupChooseResp>> zeroResp = Jackson.base().readValue(result,
				new TypeReference<ZeroResp<List<CarModelSubGroupChooseResp>>>() {
				});
		List<CarModelSubGroupChooseResp> carModelSubGroupRespList = zeroResp.getData();
		ZeroCarGroupChooseEntity entity = zeroChooseService.getZeroCarGroupChooseEntityByAuth(ZeroUrlUtil.getValue(uri, PARAM_AUTH));
		if (CollectionUtils.isNotEmpty(carModelSubGroupRespList)) {
			this.saveGroup(carModelSubGroupRespList, uri, entity);
			this.requestChain(carModelSubGroupRespList, page);
		}else {
			if (Objects.equals(1, zeroResp.getCode()) && Objects.nonNull(entity)){
				//车型没有主组下没有零件组
				entity.setSuccessStatus(1);
				zeroChooseService.updateZeroCarGroup(entity);
			}
			logger.info("请求url:{}未获取到数据,失败原因", url, zeroResp.getMsg());
		}
	}

	private void saveGroup(List<CarModelSubGroupChooseResp> carModelSubGroupRespList, String uri, ZeroCarGroupChooseEntity entity) {
		//获取车型主组对应的auth并查询该auth对应的主组信息
		if (Objects.nonNull(entity) && CollectionUtils.isNotEmpty(carModelSubGroupRespList)) {
			//主组信息获取完毕后获取该主组对应的车型数据
			ZeroCarModelChooseEntity carModelEntity = zeroChooseService.getZeroCarModelChooseEntityByCId(entity.getcId());
			if (Objects.nonNull(carModelEntity)) {
				ZeroGroupDto zeroGroupDto = this.getZeroGroupDto(carModelEntity, entity, carModelSubGroupRespList);
				//保存车型主组及子组信息
				zeroDataCollectService.saveGroups(Collections.singletonList(zeroGroupDto));
				List<ZeroCarSubGroupChooseEntity> zeroCarSubGroupChooseEntityList = this.getZeroCarSubGroupChooseEntityList(zeroGroupDto, entity);
				//抓取数据保存用于后面分析解析
				zeroChooseService.batchInsertZeroCarSubGroup(zeroCarSubGroupChooseEntityList);
				//变更主组的状态为成功
				entity.setSuccessStatus(1);
				zeroChooseService.updateZeroCarGroup(entity);
			}
		}
	}

	private ZeroGroupDto getZeroGroupDto(ZeroCarModelChooseEntity carModelEntity, ZeroCarGroupChooseEntity entity,
			List<CarModelSubGroupChooseResp> carModelSubGroupRespList) {
		//主组信息
		ZeroGroupDto zeroGroupDto = BeanUtils.transform(ZeroGroupDto.class, carModelEntity);
		zeroGroupDto.setcId(carModelEntity.getId());
		zeroGroupDto.setGroupName(entity.getGroupName());
		zeroGroupDto.setGroupNum(entity.getGroupNum());
		List<ZeroSubGroupDto> zeroSubGroupList = Lists.newArrayList();
		carModelSubGroupRespList.forEach(resp -> {
			//拼装零件组信息
			ZeroSubGroupDto zeroSubGroupDto = BeanUtils.transform(ZeroSubGroupDto.class, carModelEntity);
			zeroSubGroupDto.setcId(carModelEntity.getId());
			zeroSubGroupDto.setGroupName(entity.getGroupName());
			zeroSubGroupDto.setSubGroupName(resp.getSubGroupName());
			zeroSubGroupDto.setSubGroup(resp.getSubGroup());
			zeroSubGroupDto.setSubDesc(resp.getDescription());
			zeroSubGroupDto.setSubGroupUrl(resp.getUrl());
			zeroSubGroupDto.setSubModel(resp.getModel());
			zeroSubGroupDto.setSubMid(resp.getMid());
			zeroSubGroupDto.setAuth(resp.getAuth());
			zeroSubGroupDto.setUri("auth=" + resp.getUriParam().getAuth() + "&code=" + resp.getUriParam().getCode());
			zeroSubGroupList.add(zeroSubGroupDto);
		});
		zeroGroupDto.setZeroSubGroups(zeroSubGroupList);
		return zeroGroupDto;
	}

	private List<ZeroCarSubGroupChooseEntity> getZeroCarSubGroupChooseEntityList(ZeroGroupDto zeroGroupDto, ZeroCarGroupChooseEntity entity) {
		List<ZeroCarSubGroupChooseEntity> zeroCarSubGroupChooseEntityList = Lists.newArrayList();
		zeroGroupDto.getZeroSubGroups().forEach(dto -> {
			ZeroCarSubGroupChooseEntity subGroupChooseEntity = new ZeroCarSubGroupChooseEntity();
			subGroupChooseEntity.setInnerGroupId(dto.getGroupId());
			subGroupChooseEntity.setInnerSubGroupId(dto.getSubGroupId());
			subGroupChooseEntity.setGroupId(entity.getId());
			subGroupChooseEntity.setGroupName(dto.getGroupName());
			subGroupChooseEntity.setSubGroupName(dto.getSubGroupName());
			subGroupChooseEntity.setSubGroup(dto.getSubGroup());
			subGroupChooseEntity.setMid(dto.getSubMid());
			subGroupChooseEntity.setAuth(dto.getAuth());
			subGroupChooseEntity.setUri(dto.getUri());
			zeroCarSubGroupChooseEntityList.add(subGroupChooseEntity);
		});
		return zeroCarSubGroupChooseEntityList;
	}

	private void requestChain(List<CarModelSubGroupChooseResp> carModelSubGroupRespList, Page page) {
		List<String> urlList = Lists.newArrayList();
		carModelSubGroupRespList.forEach(resp -> {
			if (Objects.nonNull(resp.getUriParam())) {
				//urlList.add(REQUEST_URL + "/ppycars/subimgs?" + "auth=" + resp.getUriParam().getAuth() + "&code=" + resp.getUriParam().getCode());
				urlList.add(REQUEST_URL + "/ppycars/parts?" + "auth=" + resp.getUriParam().getAuth() + "&code=" + resp.getUriParam().getCode());
			}
		});
		page.addTargetRequests(urlList);
	}

}

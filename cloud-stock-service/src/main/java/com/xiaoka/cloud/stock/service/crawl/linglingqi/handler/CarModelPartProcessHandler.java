package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarSubGroupChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroSubGroupPartsDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.PartChooseResp;
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
import java.util.List;
import java.util.Objects;

import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.PARAM_AUTH;
import static com.xiaoka.cloud.stock.service.crawl.linglingqi.constant.LingLingQiConstant.REQUEST_URL;

/**
 * 零件组下零件关系处理
 *
 * @author gancao 2017/12/18 14:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelPartProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(CarModelPartProcessHandler.class);

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
		ZeroResp<List<List<PartChooseResp>>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<List<PartChooseResp>>>>() {
		});
		List<List<PartChooseResp>> carModelPartChooseRespList = zeroResp.getData();
		ZeroCarSubGroupChooseEntity entity = zeroChooseService.getZeroCarSubGroupChooseEntityByAuth(ZeroUrlUtil.getValue(uri, PARAM_AUTH));
		//获取该零件的子组
		if (CollectionUtils.isNotEmpty(carModelPartChooseRespList)) {
			this.saveCarModelPart(carModelPartChooseRespList, chooseEntity.getId(), entity);
		} else {
			if (Objects.equals(1, zeroResp.getCode()) && Objects.nonNull(entity)){
				//车型没有主组下没有零件组
				entity.setSuccessStatus(1);
				zeroChooseService.updateZeroCarSubGroup(entity);
			}
			logger.info("未获取到数据:{}", zeroResp.getMsg());
		}

	}

	private void saveCarModelPart(List<List<PartChooseResp>> carModelPartChooseRespList, Integer cId, ZeroCarSubGroupChooseEntity entity) {
		//根据auth获取零件组
		if (Objects.nonNull(entity)) {
			List<ZeroSubGroupPartsDto> partsDtoList = Lists.newArrayList();
			//拼装零件
			carModelPartChooseRespList.forEach(resp ->
					resp.forEach(p -> {
						ZeroSubGroupPartsDto partsDto = new ZeroSubGroupPartsDto();
						partsDto.setcId(cId);
						partsDto.setLabelName(p.getLabel());
						partsDto.setGroupId(entity.getInnerGroupId());
						partsDto.setGroupName(entity.getGroupName());
						partsDto.setSubGroupId(entity.getInnerSubGroupId());
						partsDto.setSubGroup(entity.getSubGroup());
						partsDto.setSubGroupName(entity.getSubGroupName());
						partsDto.setPid(p.getPid());
						partsDto.setRealPid(p.getRealPid());
						partsDto.setNum(p.getNum());
						partsDto.setCount(p.getQuantity());
						partsDtoList.add(partsDto);
					})
			);
			zeroDataCollectService.saveSubGroupParts(partsDtoList);
			//变更零件组的状态为成功
			entity.setSuccessStatus(1);
			zeroChooseService.updateZeroCarSubGroup(entity);
		}
	}

	private void requestChain(List<List<PartChooseResp>> carModelPartChooseRespList, Page page, String code) {
		List<String> urlList = Lists.newArrayList();
		carModelPartChooseRespList.forEach(resp ->
				resp.forEach(p -> {
					urlList.add(REQUEST_URL + "/ppys/partcars?part=" + p.getRealPid() + "&brand=" + code);//适用车型
					urlList.add(REQUEST_URL + "/ppys/partprices?part=" + p.getRealPid() + "&brand=" + code);//渠道价格
					urlList.add(REQUEST_URL + "/ppys/partssearchs?part=" + p.getRealPid() + "&brand=" + code);//配件信息
				})
		);
		page.addTargetRequests(urlList);
	}

}

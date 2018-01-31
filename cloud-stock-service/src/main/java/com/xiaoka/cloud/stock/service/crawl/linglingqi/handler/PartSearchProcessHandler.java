package com.xiaoka.cloud.stock.service.crawl.linglingqi.handler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroCarModelChooseEntity;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.ZeroPartInfoDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.PartChooseResp;
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
 * 零件详情处理
 *
 * @author gancao 2017/12/18 14:16
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PartSearchProcessHandler implements ProcessHandler {

	private static final Logger logger = LoggerFactory.getLogger(PartSearchProcessHandler.class);

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
		ZeroResp<List<PartChooseResp>> zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<List<PartChooseResp>>>() {
		});
		List<PartChooseResp> respList = zeroResp.getData();
		if (CollectionUtils.isNotEmpty(respList)) {
			this.savePartInfo(respList, uri);
		}else {
			logger.info("未获取到数据:{}", zeroResp.getMsg());
		}
	}

	private void savePartInfo(List<PartChooseResp> respList, String uri) {
		String brand = ZeroUrlUtil.getValue(uri, PARAM_BRAND);
		List<ZeroPartInfoDto> partInfoDtoList = Lists.newArrayList();
		respList.forEach(resp -> {
			ZeroPartInfoDto zeroPartInfoDto = new ZeroPartInfoDto();
			zeroPartInfoDto.setPid(resp.getPid());
			zeroPartInfoDto.setRealPid(resp.getPid());
			zeroPartInfoDto.setBrand(brand);
			zeroPartInfoDto.setPidLabel(resp.getLabel());
			zeroPartInfoDto.setPidModel(resp.getModel());
			zeroPartInfoDto.setPidRemark(resp.getRemark());
			partInfoDtoList.add(zeroPartInfoDto);
		});
		zeroDataCollectService.savePartInfos(partInfoDtoList);
	}

}

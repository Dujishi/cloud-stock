package com.xiaoka.cloud.stock.service.task.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.PartModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartModelEntity;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.cloud.stock.service.crawl.superepc.PartAdapterCarModelCrawlService;
import com.xiaoka.cloud.stock.service.task.service.SpecialSupplierTaskService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCPartService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAdapterModelsResp;
import com.xiaoka.freework.help.page.PageList;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author zhouze
 * @date 2018/1/2
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class RongYiSupplierTaskServiceImpl implements SpecialSupplierTaskService {

	private static final Logger LOGGER = LoggerFactory.getLogger(RongYiSupplierTaskServiceImpl.class);

	@Resource
	private PartAdapterCarModelCrawlService partAdapterCarModelCrawlService;
	@Resource
	private CloudPartRepo                   cloudPartRepo;

	private static final String NO_STOCK_FLAG = String.valueOf("1");
	private static final String SPLIT_FLAG    = String.valueOf("/");

	@Override
	public void runSearchExistsStockPartAdapterCarModels(Integer shopId) {
		int                       page      = 1;
		PageList<CloudPartEntity> pageList  = PageList.build(page, 10);
		CloudPartEntity           condition = new CloudPartEntity();
		condition.setShopId(shopId);
		pageList.setCondition(condition);
		do {
			List<CloudPartEntity> cloudPartEntities = cloudPartRepo.queryExistsStockPageList(pageList);
			if (CollectionUtils.isEmpty(cloudPartEntities)) {
				LOGGER.info("查询出的结果为空");
				return;
			}
			List<String> codes = Lists.newArrayList();
			cloudPartEntities.forEach(p -> {
				String searchCode = p.getOeNo();
				//解析searchCode
				if (Objects.equals(NO_STOCK_FLAG, searchCode)) {
					return;
				}
				if (searchCode.indexOf(SPLIT_FLAG) > 0) {
					for (String code : searchCode.split(SPLIT_FLAG)) {
						if (StringUtils.isNotBlank(code)) {
							codes.add(code);
						}
					}
				} else {
					codes.add(searchCode);
				}
			});
			partAdapterCarModelCrawlService.crawlPartAdapterCarModel(codes);
			pageList.setPage(page++);
		} while (true);
	}

}

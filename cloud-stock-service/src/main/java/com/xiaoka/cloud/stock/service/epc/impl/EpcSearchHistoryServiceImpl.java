package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Splitter;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudSearchLogRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSearchLogEntity;
import com.xiaoka.cloud.stock.service.epc.EpcSearchHistoryService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Do something
 *
 * @author gancao 2017/11/22 16:25
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class EpcSearchHistoryServiceImpl implements EpcSearchHistoryService {

	private static final ExecutorService executorService = Executors.newFixedThreadPool(5);
	private static final Splitter SPLITTER = Splitter.on(",").trimResults();

	@Resource
	private CloudSearchLogRepo cloudSearchLogRepo;

	@Override
	public List<CloudSearchLogEntity> searchHistory(Integer searchType, CloudSupplierUserDto userDto) {
		if (Objects.nonNull(userDto)) {
			return cloudSearchLogRepo.selectLateLog(userDto.getUserId(), searchType, null);
		}
		return null;
	}

	@Override
	public void insertSearchHistory(Integer searchType, String content, CloudSupplierUserDto userDto) {
		if (Objects.nonNull(userDto) && StringUtils.isNotBlank(content)) {
			CloudSearchLogEntity entity = new CloudSearchLogEntity();
			entity.setContent(content);
			entity.setSearchType(searchType);
			entity.setUserId(userDto.getUserId());
			entity.setSupplierId(userDto.getSupplierId());
			executorService.execute(() -> this.insertLog(entity));//异步执行日志插入操作
		}
	}

	private void insertLog(CloudSearchLogEntity entity) {
		List<String> contentList;
		if (entity.getSearchType() == 2) {
			contentList = SPLITTER.splitToList(entity.getContent());
		} else {
			contentList = Collections.singletonList(entity.getContent());
		}

		contentList.forEach(content -> {
			entity.setContent(content);
			List<CloudSearchLogEntity> searchLogEntityList = cloudSearchLogRepo
					.selectLateLog(entity.getUserId(), entity.getSearchType(), content);//取最近该内容的记录
			if (CollectionUtils.isNotEmpty(searchLogEntityList)) {//判断是否有重复的搜索内容
				cloudSearchLogRepo.delete(searchLogEntityList.get(0).getId());
			}
			cloudSearchLogRepo.insert(entity);
		});

	}
}

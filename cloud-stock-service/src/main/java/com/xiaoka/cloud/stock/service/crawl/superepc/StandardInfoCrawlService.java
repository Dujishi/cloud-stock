package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaoka.cloud.stock.core.epc.repo.StandardAssemblyRepo;
import com.xiaoka.cloud.stock.core.epc.repo.StandardCategoryRepo;
import com.xiaoka.cloud.stock.core.epc.repo.StandardPartRepo;
import com.xiaoka.cloud.stock.core.epc.repo.StandardSubAssemblyRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardAssemblyEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardSubAssemblyEntity;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCCatalogueService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTimerAssemblyListResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTimerStandardPartsResp;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.json.Jackson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 该服务用于爬取标准配件总成及配件信息数据，并存储到数据库中
 * Created by suqin on 17/11/2017.
 */
@Service
public class StandardInfoCrawlService {

	private Logger logger = LoggerFactory.getLogger(StandardInfoCrawlService.class);

	@Resource
	SuperEPCCatalogueService superEPCCatalogueService;

	@Resource
	StandardCategoryRepo standardCategoryRepo;

	@Resource
	StandardAssemblyRepo standardAssemblyRepo;

	@Resource
	StandardSubAssemblyRepo standardSubAssemblyRepo;

	@Resource
	StandardPartRepo standardPartRepo;

	public void crawlStandardPartAndAssemblyInfo() {
		this.crawlAssemblyInfo();
		this.crawlPartInfo();

	}

	private void crawlAssemblyInfo() {
		logger.info("Prepare craw time assembly list ....");
		List<GetTimerAssemblyListResp> assemblyResps = superEPCCatalogueService.getTimerAccemblyList();

		if (CollectionUtils.isEmpty(assemblyResps)) {
			logger.error("2.2接口出错，未能查询到数据，请检查正时接口！");
			//TODO ? 正时接口返回空时该怎么处理
			return;
		}
		Set<Integer> assemblyIds = Sets.newHashSet();
		Set<Integer> subAssemblyIds = Sets.newHashSet();
		Set<String> categoryNames = Sets.newHashSet();
		assemblyResps.forEach(assembly -> {
			assemblyIds.add(Integer.valueOf(assembly.getTimerAssemblyId()));
			subAssemblyIds.add(Integer.valueOf(assembly.getTimerSubAssemblyId()));
			categoryNames.add(assembly.getTimerType());
		});

		Map<Integer, Integer> alreadyExistAssemblyIdMap = Maps.newHashMap();
		Map<Integer, Integer> alreadyExistSubAssemblyIdMap = Maps.newHashMap();
		Map<String, String> alreadyExistCategoryNameMap = Maps.newHashMap();

		//判断总成是否存在
		List<Integer> alreadyExistAssemblyIds = standardAssemblyRepo.selectIdByIds(new ArrayList<>(assemblyIds));
		if (CollectionUtils.isNotEmpty(alreadyExistAssemblyIds)) {
			//已经有存在的数据了
			alreadyExistAssemblyIdMap = alreadyExistAssemblyIds.stream().collect(
					Collectors.toMap(x -> x, x -> x));
		}
		logger.info("正时总成 接口返回=={}", Jackson.mobile().writeValueAsString(assemblyIds));
		logger.info("正时总成 数据库已存=={}", Jackson.mobile().writeValueAsString(alreadyExistAssemblyIds));

		//判断分总成是否存在
		List<Integer> alreadyExistSubAssemblyIds = standardSubAssemblyRepo
				.selectIdByIds(new ArrayList<>(subAssemblyIds));
		if (CollectionUtils.isNotEmpty(alreadyExistSubAssemblyIds)) {
			//已经有存在的数据了
			alreadyExistSubAssemblyIdMap = alreadyExistSubAssemblyIds.stream().collect(
					Collectors.toMap(x -> x, x -> x));
		}
		logger.info("正时分总成 接口返回=={}", Jackson.mobile().writeValueAsString(subAssemblyIds));
		logger.info("正时分总成 数据库已存=={}", Jackson.mobile().writeValueAsString(alreadyExistSubAssemblyIdMap));

		//判断大分类是否存在
		List<String> alreadyExistCategoryNames = standardCategoryRepo
				.selectIdByNames(new ArrayList<>(categoryNames));
		if (CollectionUtils.isNotEmpty(alreadyExistCategoryNames)) {
			//已经有存在的数据了
			alreadyExistCategoryNameMap = alreadyExistCategoryNames.stream().collect(
					Collectors.toMap(x -> x, x -> x));
		}
		logger.info("正时大分类 接口返回=={}", Jackson.mobile().writeValueAsString(categoryNames));
		logger.info("正时大分类 数据库已存=={}", Jackson.mobile().writeValueAsString(alreadyExistCategoryNameMap));
		handleAssembly(assemblyResps, alreadyExistAssemblyIdMap, alreadyExistSubAssemblyIdMap,
				alreadyExistCategoryNameMap);
		logger.info("Successfully craw time assembly list ....");
	}

	private void handleAssembly(List<GetTimerAssemblyListResp> assemblyResps,
			Map<Integer, Integer> alreadyExistAssemblyIdMap, Map<Integer, Integer> alreadyExistSubAssemblyIdMap,
			Map<String, String> alreadyExistCategoryNameMap) {

		Map<String, StandardAssemblyEntity> storeAssemblyMap = Maps.newHashMap();
		Map<String, StandardSubAssemblyEntity> storeSubAssemblyMap = Maps.newHashMap();
		Map<String, StandardCategoryEntity> storeCategoryMap = Maps.newHashMap();
		assemblyResps.forEach(assembly -> {
			//数据库不存在的总成需要入库
			if (!alreadyExistAssemblyIdMap.containsKey(Integer.valueOf(assembly.getTimerAssemblyId()))
					&& !storeAssemblyMap
					.containsKey(assembly.getTimerAssemblyId())) {
				storeAssemblyMap.put(assembly.getTimerAssemblyId(), convertToStandardAssembly(assembly));
			}
			//数据库不存在的分总成需要入库
			if (!alreadyExistSubAssemblyIdMap.containsKey(Integer.valueOf(assembly.getTimerSubAssemblyId()))
					&& !storeSubAssemblyMap
					.containsKey(assembly.getTimerSubAssemblyId())) {
				storeSubAssemblyMap.put(assembly.getTimerSubAssemblyId(), convertToStandardSubAssembly(assembly));
			}
			//数据库不存在的大分类需要入库
			if (!alreadyExistCategoryNameMap.containsKey(assembly.getTimerType()) && !storeCategoryMap
					.containsKey(assembly.getTimerType())) {
				storeCategoryMap.put(assembly.getTimerType(), convertToStandardCategory(assembly));
			}
		});

		List<StandardAssemblyEntity> assemblyEntityList = new ArrayList<>(storeAssemblyMap.values());
		List<StandardSubAssemblyEntity> subAssemblyEntityList = new ArrayList<>(storeSubAssemblyMap.values());
		List<StandardCategoryEntity> categoryEntityList = new ArrayList<>(storeCategoryMap.values());

		if (CollectionUtils.isNotEmpty(categoryEntityList)) {
			standardCategoryRepo.batchInsert(categoryEntityList);
		}
		logger.info("批量插入正时大分类，参数：{}", Jackson.mobile().writeValueAsString(categoryEntityList));


		initCategoryId(standardCategoryRepo.selectAll(), assemblyEntityList, subAssemblyEntityList,
				null);

		if (CollectionUtils.isNotEmpty(assemblyEntityList)) {
			standardAssemblyRepo.batchInsert(assemblyEntityList);
		}
		logger.info("批量插入正时总成，参数：{}", Jackson.mobile().writeValueAsString(assemblyEntityList));
		if (CollectionUtils.isNotEmpty(subAssemblyEntityList)) {
			standardSubAssemblyRepo.batchInsert(subAssemblyEntityList);
		}
		logger.info("批量插入正时分总成，参数：{}", Jackson.mobile().writeValueAsString(subAssemblyEntityList));

	}

	private void crawlPartInfo() {
		logger.info("Prepare craw time standard part ....");
		List<GetTimerStandardPartsResp> standardPartsResps = superEPCCatalogueService.getTimerStandardParts();

		if (CollectionUtils.isEmpty(standardPartsResps)) {
			logger.error("2.3接口出错，未能查询到数据，请检查正时接口！");
			//TODO 正时标准配件接口返回空时怎么办？？？
			return;
		}
		logger.info("标准配件，EPC接口返回{}条，内容==={}", standardPartsResps.size(), Jackson.mobile().writeValueAsString(standardPartsResps));
		List<List<GetTimerStandardPartsResp>> splitResult = Lists.partition(standardPartsResps, 500);
		splitResult.forEach(slist -> {
			if (CollectionUtils.isEmpty(slist)) {
				return;
			}
			List<Integer> partIds = slist.stream().map(x -> Integer.valueOf(x.getTimerId()))
			                             .collect(Collectors.toList());

			Map<Integer, Integer> alreadyExistPartIdMap = Maps.newHashMap();
			//判断配件是否存在
			List<Integer> alreadyExistPartIds = standardPartRepo.selectIdByIds(partIds);
			if (CollectionUtils.isNotEmpty(alreadyExistPartIds)) {
				//已经有存在的数据了
				alreadyExistPartIdMap = alreadyExistPartIds.stream().collect(
						Collectors.toMap(x -> x, x -> x));
			}

			handlePart(slist, alreadyExistPartIdMap);
		});
		logger.info("Finish craw time standard part ....");

	}

	private void handlePart(List<GetTimerStandardPartsResp> partsRespList,
			Map<Integer, Integer> alreadyExistPartIdMap) {

		List<StandardPartEntity> storePartList = Lists.newArrayList();
		partsRespList.forEach(part -> {
			//数据库不存在的配件需要入库
			if (!alreadyExistPartIdMap.containsKey(Integer.valueOf(part.getTimerId()))) {
				storePartList.add(convertToStandardPart(part));
			}
		});

		initCategoryId(standardCategoryRepo.selectAll(), null, null,
				storePartList);

		if (CollectionUtils.isNotEmpty(storePartList)) {
			standardPartRepo.batchInsert(storePartList);
		}
	}

	private void initCategoryId(List<StandardCategoryEntity> storedCategoryEntityList,
			List<StandardAssemblyEntity> assemblyEntityList, List<StandardSubAssemblyEntity> subAssemblyEntityList,
			List<StandardPartEntity> storePartList) {
		//存在新增时需要将表中数据一起聚合在一起，才不会丢了category id信息
		if (CollectionUtils.isEmpty(storedCategoryEntityList)) {
			throw new ApiException("-1", "大分类不存在，无法继续");
		}
		Map<String, Integer> categoryKvPair = storedCategoryEntityList.stream().collect(
				Collectors.toMap(x -> x.getCategoryName(), x -> x.getCategoryId()));

		if (CollectionUtils.isNotEmpty(assemblyEntityList)) {
			assemblyEntityList.forEach(x -> x.setCategoryId(categoryKvPair.get(x.getCategoryName())));
		}
		if (CollectionUtils.isNotEmpty(subAssemblyEntityList)) {
			subAssemblyEntityList.forEach(x -> x.setCategoryId(categoryKvPair.get(x.getCategoryName())));
		}
		if (CollectionUtils.isNotEmpty(storePartList)) {
			storePartList.forEach(x -> x.setCategoryId(categoryKvPair.get(x.getCategoryName())));
		}

	}

	private StandardPartEntity convertToStandardPart(GetTimerStandardPartsResp resp) {
		StandardPartEntity rlt = new StandardPartEntity();
		rlt.setAssemblyId(Integer.valueOf(resp.getTimerAssemblyId()));
		rlt.setAssemblyName(resp.getTimerAssembly());
		rlt.setSubAssemblyId(Integer.valueOf(resp.getTimerSubAssemblyId()));
		rlt.setSubAssemblyName(resp.getTimerSubAssembly());
		rlt.setCategoryName(resp.getTimerType());
		rlt.setStandardPartId(Integer.valueOf(resp.getTimerId()));
		rlt.setStandardPartName(resp.getTimerName());
		rlt.setStandardPartBriefName(resp.getTimerStandName());
		return rlt;
	}

	private StandardAssemblyEntity convertToStandardAssembly(GetTimerAssemblyListResp resp) {
		StandardAssemblyEntity rlt = new StandardAssemblyEntity();
		rlt.setAssemblyId(Integer.valueOf(resp.getTimerAssemblyId()));
		rlt.setAssemblyName(resp.getTimerAssembly());
		rlt.setCategoryName(resp.getTimerType());
		return rlt;
	}

	private StandardSubAssemblyEntity convertToStandardSubAssembly(GetTimerAssemblyListResp resp) {
		StandardSubAssemblyEntity rlt = new StandardSubAssemblyEntity();
		rlt.setSubAssemblyId(Integer.valueOf(resp.getTimerSubAssemblyId()));
		rlt.setSubAssemblyName(resp.getTimerSubAssembly());
		rlt.setAssemblyId(Integer.valueOf(resp.getTimerAssemblyId()));
		rlt.setAssemblyName(resp.getTimerAssembly());
		rlt.setCategoryName(resp.getTimerType());
		return rlt;
	}

	private StandardCategoryEntity convertToStandardCategory(GetTimerAssemblyListResp resp) {
		StandardCategoryEntity rlt = new StandardCategoryEntity();
		rlt.setCategoryName(resp.getTimerType());
		return rlt;
	}
}

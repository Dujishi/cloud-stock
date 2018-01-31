package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelCategoryRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelPicRepo;
import com.xiaoka.cloud.stock.core.epc.repo.StandardAssemblyRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.StandardAssemblyEntity;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCAdaptationService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAssemblyCatalogueResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPartsInfoResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPicInfoResp;
import com.xiaoka.freework.help.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 该服务用于爬取车款对应的总成及图片数据，并存储到数据库中
 * Created by suqin on 20/11/2017.
 */
@Service
public class CarAssemblyInfoCrawlService {

	private Logger logger = LoggerFactory.getLogger(CarAssemblyInfoCrawlService.class);

	@Resource
	SuperEPCAdaptationService superEPCAdaptationService;

	@Resource
	CarModelCategoryRepo carModelCategoryRepo;
	@Resource
	StandardAssemblyRepo standardAssemblyRepo;

	@Resource
	CarModelPicRepo carModelPicRepo;

	@Transactional
	public void crawlCarAssemblyAndPicInfo(Integer modelId) {
		logger.info("start to crawl car assembly and picture info!");

		long ct = System.currentTimeMillis();
		//处理正时分类（并不是每个车款都存在正时分类）
		this.processTimerCatalogue(modelId);

		long ct2 = System.currentTimeMillis();
		logger.info("处理正时分类，modelId:{},耗时:{}", modelId, (ct2 - ct) / 1000);

		//处理原厂分类
		this.processOriginalCatalogue(modelId);

		long ct3 = System.currentTimeMillis();
		logger.info("处理原厂分类，modelId:{},耗时:{}", modelId, (ct3 - ct) / 1000);
	}

	public List<CarModelPicEntity> crawlCarPicInfo(Integer modelId,List<CarModelCategoryEntity> storeModelCategoryList, Integer type){
		//处理车款对应的分类下的图片信息
		logger.info("start to crawl timer picture info!");
		List<CarModelPicEntity> storeModelPicList = this.crawlPicInfo(modelId, storeModelCategoryList, type);
		logger.info("start to write db!");
		//持久化
		if (CollectionUtils.isNotEmpty(storeModelPicList)) {
			List<List<CarModelPicEntity>> splitResult = Lists.partition(storeModelPicList, 500);
			splitResult.forEach(x -> carModelPicRepo.batchInsert(x));
		}
		carModelCategoryRepo.batchInsert(storeModelCategoryList);
		logger.info("finish to timer catalogue!");
		return storeModelPicList;
	}

	private void processOriginalCatalogue(Integer modelId) {
		logger.info("start to original catalogue!");
		CarModelCategoryEntity param = new CarModelCategoryEntity();
		param.setModelId(modelId);
		param.setType(SuperEPCconstant.TYPE_ORIGINAL);
		List<CarModelCategoryEntity> rlt = carModelCategoryRepo.select(param);

		if (CollectionUtils.isNotEmpty(rlt)) {
			logger.info("车款 {} 原厂分类已存在不处理", modelId);
			return;
		}

		//3.1接口查询车款对应的正时分类
		List<GetAssemblyCatalogueResp> catalogueResps = superEPCAdaptationService
				.getAssemblyCatalogue(modelId, SuperEPCconstant.TYPE_ORIGINAL);

		if (CollectionUtils.isEmpty(catalogueResps)) {
			logger.error("车款 {} 无原厂分类信息，无法处理", modelId);
			return;
		}

		// 车型对应的分类信息
		List<CarModelCategoryEntity> storeModelCategoryList = Lists.newArrayList();
		catalogueResps.forEach(x -> {
			CarModelCategoryEntity cmce = convertToCarModelCategory(x);
			cmce.setModelId(modelId);
			cmce.setType(SuperEPCconstant.TYPE_ORIGINAL);
			storeModelCategoryList.add(cmce);
		});
		//处理车款对应的分类下的图片信息
		logger.info("start to crawl original picture info!");
		/*List<CarModelPicEntity> storeModelPicList = this
				.crawlPicInfo(modelId, storeModelCategoryList, SuperEPCconstant.TYPE_ORIGINAL);
		logger.info("start to write db!");
		//持久化
		if (CollectionUtils.isNotEmpty(storeModelPicList)) {
			List<List<CarModelPicEntity>> splitResult = Lists.partition(storeModelPicList, 200);
			splitResult.forEach(x -> carModelPicRepo.batchInsert(x));
		}*/
		carModelCategoryRepo.batchInsert(storeModelCategoryList);
		logger.info("finish to original catalogue!");
	}

	private void processTimerCatalogue(Integer modelId) {
		logger.info("start to timer catalogue!");
		CarModelCategoryEntity param = new CarModelCategoryEntity();
		param.setModelId(modelId);
		param.setType(SuperEPCconstant.TYPE_TIMER);
		List<CarModelCategoryEntity> rlt = carModelCategoryRepo.select(param);

		if (CollectionUtils.isNotEmpty(rlt)) {
			logger.info("正时分类已存在不处理");
			return;
		}

		//3.1接口查询车款对应的正时分类
		List<GetAssemblyCatalogueResp> catalogueResps = superEPCAdaptationService
				.getAssemblyCatalogue(modelId, SuperEPCconstant.TYPE_TIMER);

		if (CollectionUtils.isEmpty(catalogueResps)) {
			logger.info("车款无正时分类信息，无法处理");
			return;
		}
		List<Integer> assemblyIds = catalogueResps.stream().map(x -> Integer.valueOf(x.getTimerAssemblyId()))
				.collect(Collectors.toList());
		//查询对应的总成数据
		List<StandardAssemblyEntity> assemblyEntityList = standardAssemblyRepo.selectByIds(assemblyIds);

		if (CollectionUtils.isEmpty(assemblyEntityList)) {
			//TODO
			logger.error("正时分类错误，无法处理");
			throw new ApiException("-1", "正时分类错误，无法处理");
		}
		Map<Integer, StandardAssemblyEntity> assemblyEntityMap = assemblyEntityList.stream().collect(
				Collectors.toMap(StandardAssemblyEntity::getAssemblyId, x -> x));
		// 车型对应的分类信息
		List<CarModelCategoryEntity> storeModelCategoryList = Lists.newArrayList();
		catalogueResps.forEach(x -> {
			CarModelCategoryEntity cmce           = convertToCarModelCategory(x);
			StandardAssemblyEntity assemblyEntity = assemblyEntityMap.get(Integer.valueOf(x.getTimerAssemblyId()));
			cmce.setModelId(modelId);
			cmce.setCategoryId(assemblyEntity.getCategoryId());
			cmce.setCategoryName(assemblyEntity.getCategoryName());
			cmce.setType(SuperEPCconstant.TYPE_TIMER);
			storeModelCategoryList.add(cmce);
		});
		//处理车款对应的分类下的图片信息
		/*logger.info("start to crawl timer picture info!");
		List<CarModelPicEntity> storeModelPicList = this
				.crawlPicInfo(modelId, storeModelCategoryList, SuperEPCconstant.TYPE_TIMER);
		logger.info("start to write db!");
		//持久化
		if (CollectionUtils.isNotEmpty(storeModelPicList)) {
			List<List<CarModelPicEntity>> splitResult = Lists.partition(storeModelPicList, 200);
			splitResult.forEach(x -> carModelPicRepo.batchInsert(x));
		}*/
		carModelCategoryRepo.batchInsert(storeModelCategoryList);
		logger.info("finish to timer catalogue!");
	}

	private List<CarModelPicEntity> crawlPicInfo(Integer modelId, List<CarModelCategoryEntity> storeModelCategoryList,
												 Integer type) {
		List<CarModelPicEntity> storeModelPicList = Lists.newArrayList();

		storeModelCategoryList.forEach(category -> {
			List<GetPicInfoResp> picInfoResps;
			if (type == 1) {
				picInfoResps = superEPCAdaptationService
						.getPicInfo(modelId, type, null, null, category.getSubAssemblyId());
			} else {
				picInfoResps = superEPCAdaptationService
						.getPicInfo(modelId, type, category.getAssemblyName(),
								category.getSubAssemblyName(), null);
			}
			List<CarModelPicEntity> subAssemblyPics = Lists.newArrayList();
			if (CollectionUtils.isNotEmpty(picInfoResps)) {
				picInfoResps.forEach(pic -> {
					CarModelPicEntity carModelPicEntity = convertToCarModelPic(pic);
					carModelPicEntity.setAssemblyId(category.getAssemblyId());
					carModelPicEntity.setAssemblyName(category.getAssemblyName());
					carModelPicEntity.setSubAssemblyId(category.getSubAssemblyId());
					carModelPicEntity.setSubAssemblyName(category.getSubAssemblyName());
					carModelPicEntity.setModelId(modelId);
					carModelPicEntity.setType(type);
					subAssemblyPics.add(carModelPicEntity);
				});

				if (type == 1) {
					//正时类目需要取图的名字--原厂分总成名称
					//根据图号分组，然后取每个图号下的任意一个timerId去查询配件信息--原厂分总成
					Map<String, List<CarModelPicEntity>> subPic = subAssemblyPics.stream().collect(
							Collectors.groupingBy(CarModelPicEntity::getPicNum));

					//返回的图片信息有timer_id的情况使用timer_id，如果没有timer_id，则需要根据图片编号查询
					List<Integer> timerIds = Lists.newArrayList();
					List<String>  picnums  = Lists.newArrayList();
					subPic.forEach((k, v) -> {
						for (CarModelPicEntity picEntity : v) {
							if (picEntity.getTimerId() > 0) {
								timerIds.add(picEntity.getTimerId());
								break;
							}
							if (picEntity.getTimerId() < 0) {
								picnums.add(picEntity.getPicNum());
								break;
							}
						}
					});

					Map<String, String> picSubAssembly = Maps.newHashMap();

					if (CollectionUtils.isNotEmpty(timerIds)) {
						//处理根据timer_id查询的配件信息--原厂分总成信息
						List<GetPartsInfoResp> timerPartsInfo = superEPCAdaptationService
								.getPartsInfo(modelId, timerIds, null, null);

						if (CollectionUtils.isNotEmpty(timerPartsInfo)) {
							picSubAssembly.putAll(timerPartsInfo.stream().collect(
									Collectors.toMap(GetPartsInfoResp::getPicNum, GetPartsInfoResp::getSubAssembly,
											(key1, key2) -> key1)));
						}
					}
					if (CollectionUtils.isNotEmpty(picnums)) {
						//处理根据pic_num查询的配件信息--原厂分总成信息
						List<GetPartsInfoResp> picPartsInfo = superEPCAdaptationService
								.getPartsInfo(modelId, null, null, picnums);

						if (CollectionUtils.isNotEmpty(picPartsInfo)) {
							picSubAssembly.putAll(picPartsInfo.stream().collect(
									Collectors.toMap(GetPartsInfoResp::getPicNum, GetPartsInfoResp::getSubAssembly,
											(key1, key2) -> key1)));
						}
					}
					subAssemblyPics.forEach(x ->
							x.setOriginalSubAssemblyName(picSubAssembly.get(x.getPicNum()))
					);
				} else {
					subAssemblyPics.forEach(x ->
							x.setOriginalSubAssemblyName(category.getSubAssemblyName())
					);
				}
				storeModelPicList.addAll(subAssemblyPics);
			}
		});
		return storeModelPicList;
	}

	private CarModelCategoryEntity convertToCarModelCategory(GetAssemblyCatalogueResp resp) {
		CarModelCategoryEntity rlt = new CarModelCategoryEntity();
		rlt.setAssemblyId(
				StringUtils.isNotEmpty(resp.getTimerAssemblyId()) ? Integer.valueOf(resp.getTimerAssemblyId()) : null);
		rlt.setAssemblyName(resp.getAssembly());
		rlt.setSubAssemblyId(StringUtils.isNotEmpty(resp.getTimerSubAssemblyId()) ?
				Integer.valueOf(resp.getTimerSubAssemblyId()) :
				null);
		rlt.setSubAssemblyName(resp.getSubAssembly());
		return rlt;
	}

	private CarModelPicEntity convertToCarModelPic(GetPicInfoResp resp) {
		CarModelPicEntity rlt = new CarModelPicEntity();
		rlt.setTimerId(StringUtils.isNotEmpty(resp.getTimerId()) ? Integer.valueOf(resp.getTimerId()) : null);
		rlt.setEnable(StringUtils.isNotEmpty(resp.getEnable()) ? Integer.valueOf(resp.getEnable()) : null);
		rlt.setKpsName(resp.getKpsName());
		rlt.setPicName(resp.getPicName());
		rlt.setPicNum(resp.getPicNum());
		rlt.setPicPath(resp.getPicPath());
		return rlt;
	}
}

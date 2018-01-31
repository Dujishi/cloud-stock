package com.xiaoka.cloud.stock.service.open.impl;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.stock.repo.CloudDepotRepo;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartPriceRepo;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartPriceUncertainRepo;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudDepotEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceUncertainEntity;
import com.xiaoka.cloud.stock.service.core.util.RetryOperateUtil;
import com.xiaoka.cloud.stock.service.open.StockOpenApiService;
import com.xiaoka.cloud.stock.service.open.constant.MongoDbConstants;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import com.xiaoka.cloud.stock.service.open.core.AssemblePredicate;
import com.xiaoka.cloud.stock.service.open.dto.mongo.StockDataInMongoDto;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierPartSyncParam;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierPartUncertainPrice;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierService;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.thread.ThreadExecutor;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jongo.Jongo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.xiaoka.cloud.stock.service.open.core.AssemblePredicate.*;


/**
 * @author zhouze
 * @date 2017/11/7
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class StockOpenApiServiceImpl implements StockOpenApiService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StockOpenApiServiceImpl.class);

	@Resource
	CloudSupplierService        cloudSupplierService;
	@Resource
	CloudDepotRepo              cloudDepotRepo;
	@Resource
	CloudPartRepo               cloudPartRepo;
	@Resource
	CloudPartPriceRepo          cloudPartPriceRepo;
	@Resource
	CloudPartPriceUncertainRepo cloudPartPriceUncertainRepo;

	@Resource
	private Jongo jongo;

	@Override
	public void initData(SupplierSyncParam supplierSyncParam, String appId) {

		recordParamInMongo(supplierSyncParam, appId, "initData");

		commonHandleData(supplierSyncParam, appId);
	}


	@Override
	public void bulkData(SupplierSyncParam supplierSyncParam, String appId) {

		recordParamInMongo(supplierSyncParam, appId, "bulkData");

		commonHandleData(supplierSyncParam, appId);
	}

	private void commonHandleData(SupplierSyncParam supplierSyncParam, String appId) {
		String                      cSupplierId = supplierSyncParam.getSupplierId();
		List<SupplierPartSyncParam> cParts      = supplierSyncParam.getParts();

		//获取供应商id
		Integer shopId = cloudSupplierService.getSupplierIdByAppIdAndOutSupplierId(appId, supplierSyncParam.getSupplierId());
		if (null == shopId) {
			LOGGER.info("根据AppId未查到商家信息,appId:{}", appId);
			throw OpenApiReturnCodeEnum.FAIL_ILLEGALITY_APP_ID.ifApiException();
		}

		//数据内存分拆,拆出仓库数据、配件数据、价格数据并去重
		List<CloudDepotEntity>              depots                 = Lists.newArrayList();
		List<CloudPartEntity>               parts                  = Lists.newArrayList();
		List<CloudPartPriceEntity>          partPriceList          = Lists.newArrayList();
		List<CloudPartPriceUncertainEntity> uncertainPartPriceList = Lists.newArrayList();

		cParts.forEach(cPart -> {
			if (depots.stream().noneMatch(depotIdPredicate(cPart.getDepotId(), shopId))) {
				CloudDepotEntity cloudDepotEntity = buildCloudDepotEntity(cSupplierId, shopId, cPart);
				depots.add(cloudDepotEntity);
			}

			if (parts.stream().noneMatch(partParamPredicate(cPart, shopId))) {
				CloudPartEntity cloudPartEntity = buildCloudPartEntity(cSupplierId, shopId, cPart);
				parts.add(cloudPartEntity);
			}

			if (partPriceList.stream().noneMatch(priceParamPredicate(cPart, shopId))) {
				CloudPartPriceEntity cloudPartPriceEntity = buildCloudPartPriceEntity(shopId, cPart);
				partPriceList.add(cloudPartPriceEntity);
			}

			List<SupplierPartUncertainPrice> uncertainPrices = cPart.getUncertainPrice();
			if (CollectionUtils.isNotEmpty(uncertainPrices)) {
				uncertainPrices.forEach(uncertainPrice -> {
					boolean unExistFlag = uncertainPartPriceList.stream().noneMatch(uncertainPriceParamPredicate(cPart, uncertainPrice, shopId));
					if (unExistFlag) {
						CloudPartPriceUncertainEntity cloudPartPriceUncertainEntity = buildCloudPartPriceUncertainEntity(cSupplierId, shopId, cPart, uncertainPrice);
						uncertainPartPriceList.add(cloudPartPriceUncertainEntity);
					}
				});
			}
		});

		/**
		 * 执行保存仓库信息处理
		 */
		executeDepotData(depots);

		/**
		 * 执行配件信息处理
		 */
		reNewDepotIdForPart(depots, parts);
		executePartData(parts);

		/**
		 * 执行配件价格信息处理
		 */
		reNewDepotIdAndPartIdForPrice(depots, parts, partPriceList);
		executePriceData(partPriceList);

		/**
		 * 执行未定的价格数据
		 */
		reNewDepotIdAndPartIdForUncertainPrice(depots, parts, uncertainPartPriceList);
		executePriceUncertain(uncertainPartPriceList);
	}

	/**
	 * 执行未定的价格处理
	 *
	 * @param uncertainPartPrice
	 */
	private void executePriceUncertain(List<CloudPartPriceUncertainEntity> uncertainPartPrice) {
		if (CollectionUtils.isEmpty(uncertainPartPrice)) {
			return;
		}
		List<CloudPartPriceUncertainEntity> insertCloudUncertainPriceList = Lists.newArrayList();
		List<CloudPartPriceUncertainEntity> updateCloudUncertainPriceList = Lists.newArrayList();

		List<CloudPartPriceUncertainEntity> hasExistCloudPartsPrice = cloudPartPriceUncertainRepo.queryCloudPartPriceByConditionList(uncertainPartPrice);

		if (CollectionUtils.isNotEmpty(hasExistCloudPartsPrice)) {
			for (CloudPartPriceUncertainEntity uncertainPriceIterator : uncertainPartPrice) {

				CloudPartPriceUncertainEntity cloudPartPriceUncertain =
						hasExistCloudPartsPrice.stream()
								.filter(AssemblePredicate.uncertainPricePredicate(uncertainPriceIterator))
								.findAny().orElse(null);

				if (null != cloudPartPriceUncertain) {
					uncertainPriceIterator.setId(cloudPartPriceUncertain.getId());
					uncertainPriceIterator.setVersion(cloudPartPriceUncertain.getVersion());
					updateCloudUncertainPriceList.add(uncertainPriceIterator);
				} else {
					insertCloudUncertainPriceList.add(uncertainPriceIterator);
				}
			}
		} else {
			insertCloudUncertainPriceList.addAll(uncertainPartPrice);
		}
		//添加
		if (CollectionUtils.isNotEmpty(insertCloudUncertainPriceList)) {
			Lists.partition(insertCloudUncertainPriceList, 200).forEach(partitionList -> {
				try {
					cloudPartPriceUncertainRepo.insertCloudPartsPriceEntities(partitionList);
				} catch (DuplicateKeyException e) {
					RetryOperateUtil.doRetry(() -> retryInsertPartsUncertainPrice(partitionList), 5);
				}
			});
		}
		//修改
		if (CollectionUtils.isNotEmpty(updateCloudUncertainPriceList)) {
			cloudPartPriceUncertainRepo.casUpdateCloudPartPriceEntities(updateCloudUncertainPriceList);
		}
	}

	/**
	 * 执行价格保存处理
	 *
	 * @param partsPrice
	 */
	private void executePriceData(List<CloudPartPriceEntity> partsPrice) {
		List<CloudPartPriceEntity> insertCloudPriceList = Lists.newArrayList();
		List<CloudPartPriceEntity> updateCloudPriceList = Lists.newArrayList();

		List<CloudPartPriceEntity> hasExistCloudPartsPrice = cloudPartPriceRepo.queryCloudPartPriceByConditionList(partsPrice);

		if (CollectionUtils.isNotEmpty(hasExistCloudPartsPrice)) {
			for (CloudPartPriceEntity priceIterator : partsPrice) {

				CloudPartPriceEntity cloudPartPriceEntity =
						hasExistCloudPartsPrice.stream()
								.filter(AssemblePredicate.pricePredicate(priceIterator))
								.findAny().orElse(null);

				if (null != cloudPartPriceEntity) {
					priceIterator.setId(cloudPartPriceEntity.getId());
					priceIterator.setVersion(cloudPartPriceEntity.getVersion());
					updateCloudPriceList.add(priceIterator);
				} else {
					insertCloudPriceList.add(priceIterator);
				}
			}
		} else {
			insertCloudPriceList.addAll(partsPrice);
		}
		//添加
		if (CollectionUtils.isNotEmpty(insertCloudPriceList)) {
			Lists.partition(insertCloudPriceList, 200).forEach(partitionList -> {
				try {
					cloudPartPriceRepo.insertCloudPartsPriceEntities(partitionList);
				} catch (DuplicateKeyException e) {
					RetryOperateUtil.doRetry(() -> retryInsertPartsPrice(partitionList), 5);
				}
			});
		}
		//修改
		if (CollectionUtils.isNotEmpty(updateCloudPriceList)) {
			cloudPartPriceRepo.casUpdateCloudPartPriceEntities(updateCloudPriceList);
		}
	}

	/**
	 * 执行配件信息保存
	 *
	 * @param parts
	 */
	private void executePartData(List<CloudPartEntity> parts) {
		List<CloudPartEntity> insertCloudPartList = Lists.newArrayList();
		List<CloudPartEntity> updateCloudPartList = Lists.newArrayList();

		List<CloudPartEntity> hasExistCloudParts = cloudPartRepo.queryCloudPartsByConditionList(parts);

		if (CollectionUtils.isNotEmpty(hasExistCloudParts)) {
			for (CloudPartEntity partIterator : parts) {

				CloudPartEntity cloudPartEntity =
						hasExistCloudParts.stream()
								.filter(AssemblePredicate.partPredicate(partIterator))
								.findAny().orElse(null);

				if (null != cloudPartEntity) {
					partIterator.setId(cloudPartEntity.getId());
					partIterator.setVersion(cloudPartEntity.getVersion());
					updateCloudPartList.add(partIterator);
				} else {
					insertCloudPartList.add(partIterator);
				}
			}
		} else {
			insertCloudPartList.addAll(parts);
		}
		//添加
		List<CloudPartEntity> hasInsertList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(insertCloudPartList)) {
			Lists.partition(insertCloudPartList, 200).forEach(partitionList -> {
				try {
					cloudPartRepo.insertCloudPartEntities(partitionList);
				} catch (DuplicateKeyException e) {
					RetryOperateUtil.doRetry(() -> retryInsertParts(partitionList), 5);
				}
			});
			hasInsertList.addAll(cloudPartRepo.queryCloudPartsByConditionList(insertCloudPartList));
		}
		//修改
		if (CollectionUtils.isNotEmpty(updateCloudPartList)) {
			cloudPartRepo.casUpdateCloudPartEntities(updateCloudPartList);
		}
		//并集
		hasExistCloudParts.addAll(hasInsertList);

		parts.forEach(part -> {
			CloudPartEntity cloudPartEntity = hasExistCloudParts.stream().filter(AssemblePredicate.partPredicate(part)).findAny().orElse(null);
			if (null != cloudPartEntity) {
				part.setId(cloudPartEntity.getId());
			}
		});
	}

	/**
	 * 执行保存仓库
	 *
	 * @param depots
	 */
	private void executeDepotData(List<CloudDepotEntity> depots) {
		List<CloudDepotEntity> insertCloudDepotList = Lists.newArrayList();
		List<CloudDepotEntity> updateCloudDepotList = Lists.newArrayList();

		List<CloudDepotEntity> cloudDepotEntities = cloudDepotRepo.queryDepotByConditionList(depots);
		if (CollectionUtils.isNotEmpty(cloudDepotEntities)) {
			for (CloudDepotEntity depotIterator : depots) {

				CloudDepotEntity cloudDepotEntity =
						cloudDepotEntities.stream()
								.filter(depotPredicate(depotIterator))
								.findAny().orElse(null);

				if (null != cloudDepotEntity) {
					depotIterator.setId(cloudDepotEntity.getId());
					depotIterator.setVersion(cloudDepotEntity.getVersion());
					updateCloudDepotList.add(depotIterator);
				} else {
					insertCloudDepotList.add(depotIterator);
				}
			}
		} else {
			insertCloudDepotList.addAll(depots);
		}
		//添加
		List<CloudDepotEntity> hasInsertList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(insertCloudDepotList)) {
			try {
				cloudDepotRepo.insertCloudDepotEntities(insertCloudDepotList);
			} catch (DuplicateKeyException e) {
				RetryOperateUtil.doRetry(() -> retryInsertDepots(insertCloudDepotList), 5);
			}
			hasInsertList.addAll(cloudDepotRepo.queryDepotByConditionList(insertCloudDepotList));
		}
		//修改
		if (CollectionUtils.isNotEmpty(updateCloudDepotList)) {
			cloudDepotRepo.casUpdateCloudDepotEntities(updateCloudDepotList);
		}
		//并集
		cloudDepotEntities.addAll(hasInsertList);

		depots.forEach(depot -> {
			CloudDepotEntity cloudDepotEntity = cloudDepotEntities.stream().filter(depotPredicate(depot)).findAny().orElse(null);
			if (null != cloudDepotEntity) {
				depot.setId(cloudDepotEntity.getId());
			}
		});
	}

	/**
	 * 重新尝试插入仓库数据
	 *
	 * @param retryList
	 */
	private void retryInsertDepots(List<CloudDepotEntity> retryList) {
		if (CollectionUtils.isEmpty(retryList)) {
			return;
		}
		List<CloudDepotEntity> hasInsertedDepots = cloudDepotRepo.queryDepotByConditionList(retryList);
		List<CloudDepotEntity> insertList;
		if (CollectionUtils.isNotEmpty(hasInsertedDepots)) {
			insertList = retryList.stream()
					.filter(p -> hasInsertedDepots.stream().noneMatch(depotPredicate(p)))
					.collect(Lists::newArrayList, ArrayList::add, ArrayList::addAll);
		} else {
			insertList = Lists.newArrayList(retryList);
		}
		if (CollectionUtils.isNotEmpty(insertList)) {
			cloudDepotRepo.insertCloudDepotEntities(insertList);
		}
	}

	/**
	 * 重新尝试插入配件数据
	 *
	 * @param retryList
	 */
	private void retryInsertParts(List<CloudPartEntity> retryList) {
		if (CollectionUtils.isNotEmpty(retryList)) {
			return;
		}
		List<CloudPartEntity> hasInsertedParts = cloudPartRepo.queryCloudPartsByConditionList(retryList);
		List<CloudPartEntity> insertList;
		if (CollectionUtils.isNotEmpty(hasInsertedParts)) {
			insertList = retryList.stream()
					.filter(p -> hasInsertedParts.stream().noneMatch(partPredicate(p)))
					.collect(Lists::newArrayList, ArrayList::add, ArrayList::addAll);
		} else {
			insertList = Lists.newArrayList(retryList);
		}
		if (CollectionUtils.isNotEmpty(insertList)) {
			cloudPartRepo.insertCloudPartEntities(insertList);
		}
	}

	/**
	 * 重新尝试插入配件价格
	 *
	 * @param retryList
	 */
	private void retryInsertPartsPrice(List<CloudPartPriceEntity> retryList) {
		if (CollectionUtils.isNotEmpty(retryList)) {
			return;
		}
		List<CloudPartPriceEntity> hasInsertedParts = cloudPartPriceRepo.queryCloudPartPriceByConditionList(retryList);
		List<CloudPartPriceEntity> insertList;
		if (CollectionUtils.isNotEmpty(hasInsertedParts)) {
			insertList = retryList.stream()
					.filter(p -> hasInsertedParts.stream().noneMatch(pricePredicate(p)))
					.collect(Lists::newArrayList, ArrayList::add, ArrayList::addAll);
		} else {
			insertList = Lists.newArrayList(retryList);
		}
		if (CollectionUtils.isNotEmpty(insertList)) {
			cloudPartPriceRepo.insertCloudPartsPriceEntities(insertList);
		}
	}

	/**
	 * 重新尝试插入不确定的配件价格
	 *
	 * @param retryList
	 */
	private void retryInsertPartsUncertainPrice(List<CloudPartPriceUncertainEntity> retryList) {
		if (CollectionUtils.isNotEmpty(retryList)) {
			return;
		}
		List<CloudPartPriceUncertainEntity> hasInsertedParts = cloudPartPriceUncertainRepo.queryCloudPartPriceByConditionList(retryList);
		List<CloudPartPriceUncertainEntity> insertList;
		if (CollectionUtils.isNotEmpty(hasInsertedParts)) {
			insertList = retryList.stream()
					.filter(p -> hasInsertedParts.stream().noneMatch(uncertainPricePredicate(p)))
					.collect(Lists::newArrayList, ArrayList::add, ArrayList::addAll);
		} else {
			insertList = Lists.newArrayList(retryList);
		}
		if (CollectionUtils.isNotEmpty(insertList)) {
			cloudPartPriceUncertainRepo.insertCloudPartsPriceEntities(insertList);
		}
	}

	/**
	 * 根据接口输入参数组装云仓配件不确定的价格数据
	 *
	 * @param cSupplierId
	 * @param shopId
	 * @param cPart
	 * @param uncertainPrice
	 * @return
	 */
	private CloudPartPriceUncertainEntity buildCloudPartPriceUncertainEntity(String cSupplierId, Integer shopId, SupplierPartSyncParam cPart, SupplierPartUncertainPrice uncertainPrice) {
		CloudPartPriceUncertainEntity cloudPartPriceUncertainEntity = new CloudPartPriceUncertainEntity();
		cloudPartPriceUncertainEntity.setcPartId(StringUtils.deleteWhitespace(cPart.getPartId()));
		cloudPartPriceUncertainEntity.setPartName(cPart.getPartName());
		cloudPartPriceUncertainEntity.setcDepotId(StringUtils.deleteWhitespace(cPart.getDepotId()));
		cloudPartPriceUncertainEntity.setShopId(shopId);
		cloudPartPriceUncertainEntity.setcSupplierId(StringUtils.deleteWhitespace(cSupplierId));
		cloudPartPriceUncertainEntity.setOeNo(StringUtils.deleteWhitespace(cPart.getOeNo()));
		cloudPartPriceUncertainEntity.setPriceCode(uncertainPrice.getPriceCode());
		cloudPartPriceUncertainEntity.setPrice(uncertainPrice.getPriceValue());
		return cloudPartPriceUncertainEntity;
	}

	/**
	 * 根据接口输入参数组装云仓配件价格数据
	 *
	 * @param shopId
	 * @param cPart
	 * @return
	 */
	private CloudPartPriceEntity buildCloudPartPriceEntity(Integer shopId, SupplierPartSyncParam cPart) {
		CloudPartPriceEntity cloudPartPriceEntity = new CloudPartPriceEntity();
		cloudPartPriceEntity.setcPartId(StringUtils.deleteWhitespace(cPart.getPartId()));
		cloudPartPriceEntity.setPartName(cPart.getPartName());
		cloudPartPriceEntity.setOeNo(StringUtils.deleteWhitespace(cPart.getOeNo()));
		cloudPartPriceEntity.setcDepotId(StringUtils.deleteWhitespace(cPart.getDepotId()));
		cloudPartPriceEntity.setShopId(shopId);
		cloudPartPriceEntity.setCostPrice(cPart.getCostPrice());
		cloudPartPriceEntity.setTradePrice(cPart.getTradePrice());
		cloudPartPriceEntity.setInsurancePrice(cPart.getInsurancePrice());
		cloudPartPriceEntity.setRepairFactoryPrice(cPart.getRepairFactoryPrice());
		cloudPartPriceEntity.setRepairStationPrice(cPart.getRepairStationPrice());
		return cloudPartPriceEntity;
	}

	/**
	 * 根据接口输入参数组装云仓配件数据
	 *
	 * @param cSupplierId
	 * @param shopId
	 * @param cPart
	 * @return
	 */
	private CloudPartEntity buildCloudPartEntity(String cSupplierId, Integer shopId, SupplierPartSyncParam cPart) {
		CloudPartEntity cloudPartEntity = new CloudPartEntity();
		cloudPartEntity.setcPartId(StringUtils.deleteWhitespace(cPart.getPartId()));
		cloudPartEntity.setcDepotId(StringUtils.deleteWhitespace(cPart.getDepotId()));
		cloudPartEntity.setShopId(shopId);
		cloudPartEntity.setcSupplierId(StringUtils.deleteWhitespace(cSupplierId));
		cloudPartEntity.setOeNo(StringUtils.deleteWhitespace(cPart.getOeNo()));
		cloudPartEntity.setPartName(cPart.getPartName());
		cloudPartEntity.setPartBrand(cPart.getPartBrand());
		cloudPartEntity.setBalanceCount(cPart.getBalanceCount());
		cloudPartEntity.setOriginPlace(cPart.getOriginPlace());
		cloudPartEntity.setManufacturer(cPart.getManufacturer());
		cloudPartEntity.setcOperateMode(cPart.getOperateMode());
		cloudPartEntity.setcCreateTime(cPart.getCreateTime());
		cloudPartEntity.setcUpdateTime(cPart.getUpdateTime());
		return cloudPartEntity;
	}

	/**
	 * 根据接口输入参数组装云仓仓库数据
	 *
	 * @param cSupplierId
	 * @param shopId
	 * @param cPart
	 * @return
	 */
	private CloudDepotEntity buildCloudDepotEntity(String cSupplierId, Integer shopId, SupplierPartSyncParam cPart) {
		CloudDepotEntity cloudDepotEntity = new CloudDepotEntity();
		cloudDepotEntity.setcDepotId(StringUtils.deleteWhitespace(cPart.getDepotId()));
		cloudDepotEntity.setShopId(shopId);
		cloudDepotEntity.setcSupplierId(StringUtils.deleteWhitespace(cSupplierId));
		cloudDepotEntity.setDepotCode(cPart.getDepotCode());
		cloudDepotEntity.setDepotName(cPart.getDepotName());
		cloudDepotEntity.setDepotProvince(cPart.getDepotProvince());
		cloudDepotEntity.setDepotCity(cPart.getDepotCity());
		cloudDepotEntity.setDepotAddress(cPart.getDepotAddress());
		return cloudDepotEntity;
	}

	/**
	 * 记录mongo
	 *
	 * @param supplierSyncParam
	 * @param appId
	 * @param methodName
	 */
	private void recordParamInMongo(SupplierSyncParam supplierSyncParam, String appId, String methodName) {
		StockDataInMongoDto stockDataInMongo = new StockDataInMongoDto();
		stockDataInMongo.setAppId(appId);
		stockDataInMongo.setMethodName(methodName);
		stockDataInMongo.setSupplierSyncParam(supplierSyncParam);
		stockDataInMongo.setCurrentTime(new Date());

		Utils.get(ThreadExecutor.class).execute(new ThreadExecutor.DefaultExecutor() {
			@Override
			public void execute() {
				try {
					jongo.getCollection(MongoDbConstants.COLLECTION_PARAM_RECORD).insert(stockDataInMongo);
				} catch (Exception e) {
					LOGGER.error("异步保存mongo数据出错：[data:{}]", JSON.toJSONString(stockDataInMongo), e);
				}
			}
		});
	}

	private void reNewDepotIdAndPartIdForUncertainPrice(List<CloudDepotEntity> depots, List<CloudPartEntity> parts, List<CloudPartPriceUncertainEntity> uncertainPartPriceList) {
		uncertainPartPriceList.forEach(p -> {
			CloudDepotEntity depotEntity = depots.stream()
					.filter(depotIdPredicate(p.getcDepotId(), p.getShopId()))
					.findAny().orElse(null);
			if (null != depotEntity) {
				p.setDepotId(depotEntity.getId());
			}

			CloudPartEntity cloudPartEntity = parts.stream()
					.filter(part -> part.getcDepotId().equals(p.getcDepotId())
							&& part.getcPartId().equals(p.getcPartId()))
					.findAny().orElse(null);
			if (null != cloudPartEntity) {
				p.setPartId(cloudPartEntity.getId());
			}
		});
	}

	private void reNewDepotIdAndPartIdForPrice(List<CloudDepotEntity> depots, List<CloudPartEntity> parts, List<CloudPartPriceEntity> partPriceList) {
		partPriceList.forEach(p -> {
			CloudDepotEntity depotEntity = depots.stream()
					.filter(depotIdPredicate(p.getcDepotId(), p.getShopId()))
					.findAny().orElse(null);
			if (null != depotEntity) {
				p.setDepotId(depotEntity.getId());
			}

			CloudPartEntity cloudPartEntity = parts.stream()
					.filter(part -> part.getcDepotId().equals(p.getcDepotId())
							&& part.getcPartId().equals(p.getcPartId()))
					.findAny().orElse(null);
			if (null != cloudPartEntity) {
				p.setPartId(cloudPartEntity.getId());
			}
		});
	}

	private void reNewDepotIdForPart(List<CloudDepotEntity> depots, List<CloudPartEntity> parts) {
		parts.forEach(p -> {
			CloudDepotEntity depotEntity = depots.stream().filter(depotIdPredicate(p.getcDepotId(), p.getShopId())).findAny().orElse(null);
			if (null != depotEntity) {
				p.setDepotId(depotEntity.getId());
			}
		});
	}

}

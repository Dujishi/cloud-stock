package com.xiaoka.cloud.stock.service.stock.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.stock.repo.CloudDepotRepo;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartPriceRepo;
import com.xiaoka.cloud.stock.core.stock.repo.CloudPartRepo;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudDepotEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceEntity;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.epc.CarModelPartService;
import com.xiaoka.cloud.stock.service.epc.dto.PartInfoDto;
import com.xiaoka.cloud.stock.service.server.core.parser.dto.RongyiStockDto;
import com.xiaoka.cloud.stock.service.server.service.RongYiErpDataService;
import com.xiaoka.cloud.stock.service.stock.CloudStockService;
import com.xiaoka.cloud.stock.service.stock.constant.StockFlagEnum;
import com.xiaoka.cloud.stock.service.stock.vo.CloudStockInfoVo;
import com.xiaoka.cloud.stock.service.stock.vo.CloudStockPageVo;
import com.xiaoka.cloud.stock.service.stock.vo.RecommendSupplierInfoVo;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierService;
import com.xiaoka.shop.care.soa.api.shop.ShopCareSoaService;
import com.xiaoka.shop.care.soa.api.shop.result.ShopResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

/**
 * @author zhouze
 * @date 2017/11/17
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CloudStockServiceImpl implements CloudStockService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CloudStockServiceImpl.class);

	@Resource
	CloudDepotRepo       cloudDepotRepo;
	@Resource
	CloudPartRepo        cloudPartRepo;
	@Resource
	CloudPartPriceRepo   cloudPartPriceRepo;
	@Resource
	CloudSupplierService cloudSupplierService;
	@Resource
	ShopCareSoaService   shopCareSoaService;
	@Resource
	CarModelPartService  carModelPartService;
	@Resource
	RongYiErpDataService rongYiErpDataService;

	@Override
	public List<CloudStockPageVo> searchStockPartsByOEList(List<String> codesParam, Integer shopId) {
		AssertUtil.assertTrueWithApiException(CollectionUtils.isNotEmpty(codesParam), "零件号参数缺失");
		AssertUtil.assertTrueWithApiException(Objects.nonNull(shopId), "供应商参数缺失");

		List<String> codes = Lists.newArrayList();
		codesParam.forEach(code -> codes.add(StringUtils.deleteWhitespace(code)));

		//当前登录的配件商信息
		ShopResult currentShop = shopCareSoaService.getShopById(shopId);

		//查询供应商组织架构内的所有供应商
		List<Integer> shopIds = cloudSupplierService.getCompanyAllSubSupplierIdList(shopId);

		//初始化返回数据集容器
		List<CloudStockPageVo> cloudStockPageVos = Lists.newArrayList();

		//根据OE码和商户ID列表接口查询当前商家能看到的库存信息
		assembleInitStockVos(codes, cloudStockPageVos, shopIds);

		//补充组装售罄、暂无的其它供应商数据和替换配件数据
		supplementStockVos(cloudStockPageVos, currentShop, shopIds);

		return cloudStockPageVos;
	}

	@Override
	public List<CloudPartEntity> queryStockPartInfoList(List<String> codes, List<Integer> shopIds) {
		List<CloudPartEntity> cloudPartEntities = cloudPartRepo.queryStockPartInfoListByShopIdsAndOeNoes(codes, shopIds);
		return cloudPartEntities;
	}

	/**
	 * 组装查询当前商家能看到的库存信息列表
	 *
	 * @param codes
	 * @param cloudStockPageVos
	 * @param shopIds
	 */
	private void assembleInitStockVos(List<String> codes, List<CloudStockPageVo> cloudStockPageVos, List<Integer> shopIds) {
		//根据OE码和商户ID列表接口查询当前商家能看到的库存信息
		List<CloudPartEntity> cloudPartEntities = queryStockPartInfoList(codes, shopIds);
		if (CollectionUtils.isNotEmpty(cloudPartEntities)) {
			Map<String, List<CloudPartEntity>> cloudPartMaps = cloudPartEntities.stream().collect(Collectors.groupingBy(CloudPartEntity::getOeNo));

			//查询仓库
			List<Integer>                  depotIds  = cloudPartEntities.stream().map(CloudPartEntity::getDepotId).distinct().collect(toList());
			Map<Integer, CloudDepotEntity> depotMaps = getDepotsMap(depotIds);

			//查询价格
			List<Integer>                      partIds   = cloudPartEntities.stream().map(CloudPartEntity::getId).distinct().collect(toList());
			Map<Integer, CloudPartPriceEntity> priceMaps = getPartsPriceMap(partIds);

			codes.stream().distinct().forEach(code -> {
				List<CloudPartEntity> parts = cloudPartMaps.get(code);
				if (CollectionUtils.isNotEmpty(parts)) {
					parts.forEach(part -> {
						CloudStockPageVo cloudStockPageVo = new CloudStockPageVo();
						//组装库存配件数据
						CloudStockInfoVo stockInfoVo = assembleCloudStockInfoVo(depotMaps, priceMaps, part);
						cloudStockPageVo.setStockInfo(stockInfoVo);

						//组装库存标记状态
						if (BigDecimal.ZERO.compareTo(part.getBalanceCount()) < 0) {
							cloudStockPageVo.setFlag(StockFlagEnum.EXIST.getCode());
						} else {
							cloudStockPageVo.setFlag(StockFlagEnum.OUT_OF_STOCK.getCode());
						}
						cloudStockPageVos.add(cloudStockPageVo);
					});
				} else {
					CloudStockPageVo cloudStockPageVo = assembleNoneCloudStockVo(code);
					cloudStockPageVos.add(cloudStockPageVo);
				}
			});
		} else {
			codes.stream().forEach(code -> {
				CloudStockPageVo cloudStockPageVo = assembleNoneCloudStockVo(code);
				cloudStockPageVos.add(cloudStockPageVo);
			});
		}
	}

	/**
	 * 根据已筛选出的结果组装售罄、暂无的其它供应商数据和替换配件数据
	 *
	 * @param cloudStockPageVos
	 * @param currentShop
	 * @param shopIds
	 */
	private void supplementStockVos(List<CloudStockPageVo> cloudStockPageVos, ShopResult currentShop, List<Integer> shopIds) {
		List<CloudStockPageVo> stockPageVos = cloudStockPageVos.stream().filter(p -> !StockFlagEnum.EXIST.getCode().equals(p.getFlag())).collect(toList());
		if (CollectionUtils.isNotEmpty(stockPageVos)) {
			List<String> specialCodes = stockPageVos.stream()
					.map(CloudStockPageVo::getStockInfo)
					.map(CloudStockInfoVo::getPartCode)
					.distinct().collect(toList());

			//其它供应商
			Map<String, ShopResult> otherShopMap = getOtherShopMap(currentShop, shopIds, specialCodes);

			//其它替换件
			Map<String, List<String>> replaceCodeMap = getReplaceCodeMap(specialCodes);

			stockPageVos.forEach(stockPage -> {
				CloudStockInfoVo cloudStockInfoVo = stockPage.getStockInfo();
				if (null != cloudStockInfoVo) {
					String oeNo = cloudStockInfoVo.getPartCode();
					//配件商
					ShopResult shopResult = otherShopMap.get(oeNo);
					if (null != shopResult) {
						RecommendSupplierInfoVo recommendSupplierInfoVo = assembleOtherSupplierInfo(cloudStockInfoVo, shopResult);
						stockPage.setRecommendSupplierInfo(recommendSupplierInfoVo);
					}
					if (null != replaceCodeMap) {
						stockPage.setReplaceCodeList(replaceCodeMap.get(oeNo));
					}
				}

			});
		}
	}

	/**
	 * 获取替换件零件码列表
	 *
	 * @param specialCodes
	 * @return
	 */
	private Map<String, List<String>> getReplaceCodeMap(List<String> specialCodes) {
		Map<String, List<String>> replaceCodeMap = Maps.newHashMap();
		specialCodes.forEach(code -> {
			List<PartInfoDto> partInfoList = carModelPartService.getPartReplaceList(code, null);
			if (CollectionUtils.isNotEmpty(partInfoList)) {
				List<String> replaceCodes = partInfoList.stream().map(PartInfoDto::getPartCode).collect(toList());
				if (CollectionUtils.isNotEmpty(replaceCodes)) {
					replaceCodeMap.put(code, replaceCodes);
				}
			}

		});
		return replaceCodeMap;
	}

	/**
	 * 查询其它供应商数据
	 * 暂时按照同城随机一家商户
	 * todo 这里获取商户需要进一步抽象算法，以满足不同业务需求
	 *
	 * @param currentShop  当前登录商家
	 * @param shopIds      当前登录商家组织结构列表
	 * @param specialCodes oe号码列表，这些oe号是当前商家不存在库存的
	 * @return
	 */
	private Map<String, ShopResult> getOtherShopMap(ShopResult currentShop, List<Integer> shopIds, List<String> specialCodes) {
		Map<String, ShopResult> otherShopMap = Maps.newHashMap();
		//查询存在库存配件数据，排除当前商家列表
		List<CloudPartEntity> otherSupplierParts = cloudPartRepo.queryExistStockPartsByOeNosExcludeShopIds(specialCodes, shopIds);
		if (CollectionUtils.isNotEmpty(otherSupplierParts)) {
			Map<String, List<CloudPartEntity>> maps = otherSupplierParts.stream()
					.collect(Collectors.groupingBy(CloudPartEntity::getOeNo));
			maps.forEach((k, v) -> {
				List<Integer> otherShopIds = v.stream().map(CloudPartEntity::getShopId).distinct().collect(toList());
				if (CollectionUtils.isNotEmpty(otherShopIds)) {
					List<ShopResult> shopResults = shopCareSoaService.getShopResultByShopIds(otherShopIds);
					if (CollectionUtils.isNotEmpty(shopResults)) {
						//取同城随机一家配件商
						ShopResult otherShop = shopResults.stream().filter(shop -> shop.getCityId().equals(currentShop.getCityId())).findAny().orElse(null);
						if (null != otherShop) {
							otherShopMap.put(k, otherShop);
						}
					}
				}
			});
		}
		return otherShopMap;
	}

	/**
	 * 组装其它供应商信息
	 *
	 * @param cloudStockInfoVo
	 * @param shopResult
	 * @return
	 */
	private RecommendSupplierInfoVo assembleOtherSupplierInfo(CloudStockInfoVo cloudStockInfoVo,
															  ShopResult shopResult) {
		RecommendSupplierInfoVo recommendSupplierInfoVo = new RecommendSupplierInfoVo();
		recommendSupplierInfoVo.setPartCode(cloudStockInfoVo.getPartCode());
		recommendSupplierInfoVo.setPartName(cloudStockInfoVo.getPartName());
		recommendSupplierInfoVo.setProduceArea(cloudStockInfoVo.getProduceArea());
		recommendSupplierInfoVo.setSupplierId(shopResult.getCareShopId());
		recommendSupplierInfoVo.setSupplierName(shopResult.getCareShopName());
		recommendSupplierInfoVo.setPhone(shopResult.getContactPhone());
		recommendSupplierInfoVo.setSupplierProv(shopResult.getProv());
		recommendSupplierInfoVo.setSupplierCity(shopResult.getCity());
		return recommendSupplierInfoVo;
	}

	/**
	 * 组装不存在的库存数据
	 *
	 * @param code
	 * @return
	 */
	private CloudStockPageVo assembleNoneCloudStockVo(String code) {
		CloudStockPageVo cloudStockPageVo = new CloudStockPageVo();
		//组装库存配件数据
		CloudStockInfoVo stockInfoVo = new CloudStockInfoVo();
		stockInfoVo.setPartCode(code);
		cloudStockPageVo.setStockInfo(stockInfoVo);

		//设置标记值状态
		cloudStockPageVo.setFlag(StockFlagEnum.NONE.getCode());
		return cloudStockPageVo;
	}

	/**
	 * 组装库存数据
	 *
	 * @param depotMaps
	 * @param priceMaps
	 * @param part
	 * @return
	 */
	private CloudStockInfoVo assembleCloudStockInfoVo(Map<Integer, CloudDepotEntity> depotMaps, Map<Integer, CloudPartPriceEntity> priceMaps, CloudPartEntity part) {
		CloudStockInfoVo stockInfoVo = new CloudStockInfoVo();
		stockInfoVo.setcPartId(part.getcPartId());
		stockInfoVo.setDepot(part.getcDepotId());
		stockInfoVo.setPartId(part.getId());
		stockInfoVo.setPartCode(part.getOeNo());
		stockInfoVo.setPartName(part.getPartName());
		stockInfoVo.setProduceArea(part.getOriginPlace());
		CloudDepotEntity depotEntity = depotMaps.get(part.getDepotId());
		if (null != depotEntity) {
			stockInfoVo.setPartDepot(depotEntity.getDepotName());
		}
		stockInfoVo.setPartStock(part.getBalanceCount());
		CloudPartPriceEntity priceEntity = priceMaps.get(part.getId());
		if (null != priceEntity) {
			stockInfoVo.setCostPrice(priceEntity.getCostPrice());
			stockInfoVo.setTradePrice(priceEntity.getTradePrice());
			stockInfoVo.setInsurerPrice(priceEntity.getInsurancePrice());
			stockInfoVo.setRepairPrice(priceEntity.getRepairFactoryPrice());
			stockInfoVo.setMaintainPrice(priceEntity.getRepairStationPrice());
		}
		return stockInfoVo;
	}

	/**
	 * 获取仓库并map
	 *
	 * @param depotIds
	 * @return
	 */
	private Map<Integer, CloudDepotEntity> getDepotsMap(List<Integer> depotIds) {
		List<CloudDepotEntity> depots = cloudDepotRepo.queryDepotsByIds(depotIds);
		if (CollectionUtils.isEmpty(depotIds)) {
			return Maps.newHashMap();
		}
		return depots.stream().collect(Collectors.toMap(CloudDepotEntity::getId, p -> p, (a, b) -> a));
	}

	/**
	 * 获取配件价格数据
	 *
	 * @param partIds
	 * @return
	 */
	private Map<Integer, CloudPartPriceEntity> getPartsPriceMap(List<Integer> partIds) {
		List<CloudPartPriceEntity> cloudPartPrices = cloudPartPriceRepo.queryPartsPriceByPartIds(partIds);
		if (CollectionUtils.isEmpty(cloudPartPrices)) {
			return Maps.newHashMap();
		}
		return cloudPartPrices.stream().collect(Collectors.toMap(CloudPartPriceEntity::getPartId, p -> p, (a, b) -> a));
	}

	/**
	 * 通过商家id列表查询商家信息并map
	 *
	 * @param shopIds
	 * @return
	 */
	private Map<Integer, ShopResult> getShopsMap(List<Integer> shopIds) {
		List<ShopResult> shopResults = shopCareSoaService.getShopResultByShopIds(shopIds);
		if (CollectionUtils.isNotEmpty(shopResults)) {
			return Maps.newHashMap();
		}
		Map<Integer, ShopResult> shopMaps = shopResults.stream().collect(Collectors.toMap(ShopResult::getCareShopId, p -> p, (a, b) -> a));
		return shopMaps;
	}
}

package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.CarBrandRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarMakeRepo;
import com.xiaoka.cloud.stock.core.epc.repo.Part4sPriceRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarBrandEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarMakeEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.Part4sPriceEntity;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCPriceService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetOePriceResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 该服务用于爬取配件4s店报价，并存储到数据库中
 * Created by suqin on 21/11/2017.
 */
@Service
public class Part4sPriceCrawlService {

	private Logger logger = LoggerFactory.getLogger(Part4sPriceCrawlService.class);

	@Resource
	SuperEPCPriceService superEPCPriceService;

	@Resource
	CarBrandRepo carBrandRepo;
	@Resource
	CarMakeRepo  carMakeRepo;

	@Resource
	Part4sPriceRepo part4sPriceRepo;

	@Transactional
	public void crawlPart4sPriceInfo(String partCode) {
		logger.error("start to 4s price!");

		if (org.apache.commons.lang3.StringUtils.isBlank(partCode)) {
			return;
		}
		List<String> partCodesTemp = Collections.singletonList(org.apache.commons.lang3.StringUtils.deleteWhitespace(partCode));//去除空格

		//3.7接口查询配件的4s店报价
		List<GetOePriceResp> priceResps = superEPCPriceService
				.getOEPrice(partCodesTemp, null, null);

		if (CollectionUtils.isEmpty(priceResps)) {
			logger.error("配件 {} 暂无4s店报价，无法处理", partCodesTemp);
			return;
		}

		List<Part4sPriceEntity> part4sPriceEntities = part4sPriceRepo.selectByPartCodes(partCodesTemp);

		List<GetOePriceResp>    needToAddList    = Lists.newArrayList();
		List<Part4sPriceEntity> needToRemoveList = Lists.newArrayList();

		//原先数据库存在时，判断是否有新增，删减
		if (CollectionUtils.isNotEmpty(part4sPriceEntities)) {
			Map<String, List<Part4sPriceEntity>> partPriceMap = part4sPriceEntities.stream().collect(
					Collectors.groupingBy(Part4sPriceEntity::getPartCode));
			Map<String, List<GetOePriceResp>> priceRespMap = priceResps.stream().collect(
					Collectors.groupingBy(GetOePriceResp::getKpsCode));
			priceRespMap.forEach((k, v) -> {
				List<Part4sPriceEntity> px = partPriceMap.get(k);
				if (CollectionUtils.isEmpty(px)) {
					needToAddList.addAll(v);
					return;
				}
				Map<String, Part4sPriceEntity> pxMap = px.stream().collect(
						Collectors.toMap(x -> x.getBrandName().concat(x.getMakeName()), x -> x, (a, b) -> a));
				v.forEach(x -> {
					Part4sPriceEntity price = pxMap.get(x.getCarBrand().concat(x.getCarOem()));
					if (price == null) {
						needToAddList.add(x);
					}
				});
				Map<String, GetOePriceResp> oePriceRespMap = v.stream().collect(
						Collectors.toMap(x -> x.getCarBrand().concat(x.getCarOem()), x -> x));
				px.forEach(x -> {
					GetOePriceResp oePrice = oePriceRespMap.get(x.getBrandName().concat(x.getMakeName()));
					if (oePrice == null) {
						needToRemoveList.add(x);
					}
				});
			});
		} else {
			needToAddList.addAll(priceResps);
		}

		//需要处理的数据，补充brandId 和 makeId
		if (CollectionUtils.isNotEmpty(needToAddList)) {
			List<Part4sPriceEntity> storePriceList = assembleBrandAndMakeId(needToAddList);
			part4sPriceRepo.batchInsert(storePriceList);
		}

		if (CollectionUtils.isNotEmpty(needToRemoveList)) {
			part4sPriceRepo.batchDelete(needToRemoveList);
		}
	}

	private List<Part4sPriceEntity> assembleBrandAndMakeId(List<GetOePriceResp> needToAddList) {
		List<Part4sPriceEntity> storePriceList = Lists.newArrayList();

		List<String> brandNames = needToAddList.stream().map(x -> x.getCarBrand()).collect(Collectors.toList());
		List<String> makeNames  = needToAddList.stream().map(x -> x.getCarOem()).collect(Collectors.toList());

		//查询品牌、厂商信息
		Map<String, Integer> brandEntities = carBrandRepo.selectByNames(brandNames).stream().collect(
				Collectors.toMap(CarBrandEntity::getBrandName, CarBrandEntity::getBrandId, (a, b) -> a));
		Map<String, Integer> makeEntities = carMakeRepo.selectByNames(makeNames).stream().collect(
				Collectors.toMap(CarMakeEntity::getMakeName, CarMakeEntity::getMakeId, (a, b) -> a));
		needToAddList.forEach(x -> {
			Part4sPriceEntity part4sPriceEntity = convertToPartPrice(x);
			part4sPriceEntity.setBrandId(brandEntities.get(x.getCarBrand()));
			part4sPriceEntity.setMakeId(makeEntities.get(x.getCarOem()));
			storePriceList.add(part4sPriceEntity);
		});
		return storePriceList;
	}

	private Part4sPriceEntity convertToPartPrice(GetOePriceResp resp) {
		Part4sPriceEntity rlt = new Part4sPriceEntity();
		rlt.setPartCode(resp.getKpsCode());
		rlt.setPartCodeTemp(resp.getKpsCodeTmp());
		rlt.setBrandName(resp.getCarBrand());
		rlt.setMakeName(resp.getCarOem());
		rlt.setPartName(resp.getName());
		rlt.setPrice(new BigDecimal(resp.getPrice()));
		rlt.setEpcNo(StringUtils.isNotEmpty(resp.getEpcNo()) ? Integer.valueOf(resp.getEpcNo()) : null);
		return rlt;
	}

	private List<String> removeBlank(List<String> partCodes) {
		List<String> temp = Lists.newArrayList();
		partCodes.forEach(x ->
				temp.add(x.replace(" ", ""))
		);
		return temp;
	}

}

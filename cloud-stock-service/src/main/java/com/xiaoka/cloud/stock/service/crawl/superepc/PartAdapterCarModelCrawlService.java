package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.epc.repo.*;
import com.xiaoka.cloud.stock.core.epc.repo.entity.*;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCPartService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAdapterModelsResp;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 根据配件编码查询车型车系等适用车型信息，并存储
 *
 * @author zhouze
 * @date 2017/11/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PartAdapterCarModelCrawlService {

	private static final Logger LOGGER = LoggerFactory.getLogger(StandardInfoCrawlService.class);

	@Resource
	private SuperEPCPartService superEPCPartService;
	@Resource
	private PartModelRepo       partModelRepo;
	@Resource
	private CarBrandRepo        carBrandRepo;
	@Resource
	private CarMakeRepo         carMakeRepo;
	@Resource
	private CarSeriesRepo       carSeriesRepo;

	public void crawlPartAdapterCarModel(List<String> codes) {
		if (CollectionUtils.isEmpty(codes)) {
			return;
		}

		codes.forEach(anyCode -> {
			String                     code               = StringUtils.deleteWhitespace(anyCode);
			List<GetAdapterModelsResp> adapterModelsResps = superEPCPartService.getAdapterModels(code);
			if (CollectionUtils.isEmpty(adapterModelsResps)) {
				return;
			}

			//品牌
			List<CarBrandEntity> carBrandEntities = carBrandRepo.selectAllBrands();
			Map<String, Integer> carBrandMap      = Maps.newHashMap();
			if (CollectionUtils.isNotEmpty(carBrandEntities)) {
				carBrandMap = carBrandEntities.stream().collect(Collectors.toMap(CarBrandEntity::getBrandName, CarBrandEntity::getBrandId));
			}

			//厂商
			List<CarMakeEntity>  carMakeEntities = carMakeRepo.selectAllCarMakes();
			Map<String, Integer> carMakeMap      = Maps.newHashMap();
			if (CollectionUtils.isNotEmpty(carMakeEntities)) {
				carMakeMap = carMakeEntities.stream().collect(Collectors.toMap(CarMakeEntity::getMakeName, CarMakeEntity::getMakeId));
			}

			//车系
			List<CarSeriesEntity> carSeriesEntities = carSeriesRepo.selectAllCarSeries();
			Map<String, Integer>  carSeriesMap      = Maps.newHashMap();
			if (CollectionUtils.isNotEmpty(carSeriesEntities)) {
				carSeriesMap = carSeriesEntities.stream().collect(Collectors.toMap(CarSeriesEntity::getSeriesBbg, CarSeriesEntity::getSeriesId));
			}

			List<PartModelEntity> insertList = Lists.newArrayList();

			List<PartModelEntity> partModelEntities = partModelRepo.queryPartModelListByCode(code);
			Map<String, Integer>  finalCarBrandMap  = carBrandMap;
			Map<String, Integer>  finalCarMakeMap   = carMakeMap;
			Map<String, Integer>  finalCarSeriesMap = carSeriesMap;
			adapterModelsResps.stream().filter(p -> PartModelUniquePredicate(partModelEntities, p)).forEach(p -> {
				PartModelEntity partModelEntity = assemblePartModelEntity(code, finalCarBrandMap, finalCarMakeMap, finalCarSeriesMap, p);
				insertList.add(partModelEntity);
			});

			LOGGER.info("批量插入数据:{}", JSON.toJSONString(insertList));
			if (CollectionUtils.isNotEmpty(insertList)) {
				Integer count = partModelRepo.insertList(insertList);
				LOGGER.info("批量插入数据成功,返回:{}", count);
			}
		});
	}

	private boolean PartModelUniquePredicate(List<PartModelEntity> partModelEntities, GetAdapterModelsResp p) {
		return CollectionUtils.isNotEmpty(partModelEntities) ?
				partModelEntities.stream().anyMatch(pm -> pm.getModelId().equals(p.getTid())) : true;
	}

	private PartModelEntity assemblePartModelEntity(String code, Map<String, Integer> carBrandMap, Map<String, Integer> carMakeMap, Map<String, Integer> carSeriesMap, GetAdapterModelsResp p) {
		PartModelEntity partModelEntity = new PartModelEntity();
		partModelEntity.setPartCode(code);
		if (null == carBrandMap.get(p.getCOemBrand())) {
			LOGGER.info("查不到相关车牌数据{},零件码:{}", p.getCOemBrand(), code);
			partModelEntity.setBrandId(-1);
		} else {
			partModelEntity.setBrandId(carBrandMap.get(p.getCOemBrand()));
		}
		partModelEntity.setBrandName(p.getCOemBrand());
		if (null == carMakeMap.get(p.getCOemName())) {
			LOGGER.info("查不到相关车款数据{}，零件码:{}", p.getCOemName(), code);
			partModelEntity.setMakeId(-1);
		} else {
			partModelEntity.setMakeId(carMakeMap.get(p.getCOemName()));
		}
		partModelEntity.setMakeName(p.getCOemName());
		if (null == carSeriesMap.get(p.getCSeriesBbg())) {
			LOGGER.info("查不到相关车系数据{},零件码:{}", p.getCSeriesBbg(), code);
			partModelEntity.setSeriesId(-1);
		} else {
			partModelEntity.setSeriesId(carSeriesMap.get(p.getCSeriesBbg()));
		}
		partModelEntity.setSeriesName(p.getCSeriesBbg());
		partModelEntity.setModelId(Integer.valueOf(p.getTid()));
		partModelEntity.setModelName(p.getCTimerModelName());
		partModelEntity.setStructure(p.getCStructure());
		partModelEntity.setType(p.getType());
		partModelEntity.setTimerType(p.getTimerType());
		try {
			partModelEntity.setEpcNo(Integer.valueOf(p.getEpcNo()));
		} catch (NumberFormatException e) {
			LOGGER.info("格式转化错误{}", p.getEpcNo());
			partModelEntity.setEpcNo(-1);
		}
		partModelEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
		return partModelEntity;
	}

}

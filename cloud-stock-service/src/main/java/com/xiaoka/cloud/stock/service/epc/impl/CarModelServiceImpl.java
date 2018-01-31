package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.xiaoka.cloud.stock.core.epc.CarModelQueryService;
import com.xiaoka.cloud.stock.core.epc.PartQueryService;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelConfigEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelVinEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartModelEntity;
import com.xiaoka.cloud.stock.service.epc.CarModelService;
import com.xiaoka.cloud.stock.service.epc.SuperEpcService;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartSuitCarDto;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAdapterModelsResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetCarModelInfoByTidResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTidResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.SuperEPCResp;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * CarModelService
 *
 * @author suqin
 */
@Service
public class CarModelServiceImpl implements CarModelService {

	private static final Splitter SPLITTER = Splitter.on(",").trimResults();
	private static final Joiner   JOINER   = Joiner.on(",").skipNulls();
	private              Logger   logger   = LoggerFactory.getLogger(CarModelService.class);

	@Resource
	private PartQueryService     partQueryService;
	@Resource
	private CarModelQueryService carModelQueryService;
	@Resource
	private SuperEpcService      superEpcService;

	@Override
	public List<CarModelDto> getCarModelListByVin(String vin) {
		List<CarModelDto> carModelDtoList = Lists.newArrayList();
		//调EPC接口获取适配的车型
		List<Integer> tidList = this.getTidByVin(vin);
		if (CollectionUtils.isNotEmpty(tidList)) {
			Set<Integer>         diff               = Sets.newHashSet(tidList);
			List<CarModelEntity> carModelEntityList = carModelQueryService.getCarModelListByIds(tidList);//批量获取
			if (CollectionUtils.isNotEmpty(carModelEntityList)) {
				carModelEntityList = this.getDistinctCarModelEntityList(carModelEntityList);//去重
				carModelDtoList.addAll(carModelEntityList.stream().map(this::transformCarModelEntity).collect(Collectors.toList()));
				diff = Sets.difference(diff, carModelEntityList.stream().map(CarModelEntity::getModelId).collect(Collectors.toSet()));
				Map<Integer, Map<String, String>> resultMap = this.getModeConfig(carModelEntityList.stream().map(CarModelEntity::getModelId).collect(
						Collectors.toList()));
				carModelDtoList.forEach(dto -> dto.setCarModelConfig(resultMap.get(dto.getCarModelId())));
			}
			if (CollectionUtils.isNotEmpty(diff)) {
				logger.info("vin:{}适配的tid中,本地没有的是:{}", vin, JOINER.join(diff));
				List<GetCarModelInfoByTidResp> respList = superEpcService.getCarModelInfoByTid(Lists.newArrayList(diff));
				if (CollectionUtils.isNotEmpty(respList)) {
					carModelDtoList.addAll(respList.stream().map(this::transformGetCarModelInfoByTidResp).collect(Collectors.toList()));
				} else {
					logger.info("tid集合:{}调EPC接口未获取到数据", JOINER.join(diff));
				}
			}
		/*	if (CollectionUtils.isNotEmpty(diff)) {
				//车型有效消息同步整表车型
				epcMessageNotifyService.notifyCarModelUpdate(Lists.newArrayList(diff).get(0));
			}*/
		}
		return carModelDtoList;
	}

	@Override
	public List<PartSuitCarDto> getSuitCarModelListByPartCode(String partCode) {
		List<PartSuitCarDto> partSuitCarDtoList = Lists.newArrayList();
		/*List<PartModelEntity> partEntityList = partQueryService.getCarModelPartEntityByPartCode(partCode);//获取零件号的适用车型
		if (CollectionUtils.isNotEmpty(partEntityList)) {//去重获取车型id
			partSuitCarDtoList.addAll(partEntityList.stream().map(this::transformPartModelEntity).collect(Collectors.toList()));

		}*/
		if (CollectionUtils.isEmpty(partSuitCarDtoList)) {//本地未获取到适用车型走正时EPC3.6接口
			logger.info("零件号:{},本地未获取到适用车型,需要调正时EPC获取");
			List<GetAdapterModelsResp> modelsRespList = superEpcService.getAdapterModels(partCode);
			if (CollectionUtils.isNotEmpty(modelsRespList)) {
				//消息通知更新零件适用车型
				//epcMessageNotifyService.notifyPartAdapterCarModelUpdate(partCode);
				partSuitCarDtoList.addAll(modelsRespList.stream().map(this::transformGetAdapterModelsResp).collect(Collectors.toList()));
			} else {
				logger.info("零件号:{}适用车型调正时接口未获取到数据", partCode);
			}
		}
		return partSuitCarDtoList;
	}

	@Override
	public Map<String, List<PartSuitCarDto>> getSuitCarModelListByPartCodeList(List<String> partCodeList) {
		Map<String, List<PartSuitCarDto>> map = Maps.newHashMap();
		partCodeList.forEach(partCode -> {
			List<PartSuitCarDto> carDtoList = this.getSuitCarModelListByPartCode(partCode);
			if (CollectionUtils.isNotEmpty(carDtoList)) {
				map.put(partCode, carDtoList);
			} else {
				map.put(partCode, Lists.newArrayList());
			}
		});
		return map;
	}

	private List<Integer> getTidByVin(String vin) {
		List<CarModelVinEntity> entityList = carModelQueryService.getCarModelByVin(vin);
		if (CollectionUtils.isNotEmpty(entityList)) {
			return entityList.stream().map(CarModelVinEntity::getModelId).collect(Collectors.toList());
		}
		logger.info("vin码匹配车型本地没有数据走正时接口获取");
		SuperEPCResp<List<GetTidResp>> resp = superEpcService.getTid(vin, null);
		if (Objects.nonNull(resp) && CollectionUtils.isNotEmpty(resp.getResult()) && StringUtils.isNotBlank(resp.getResult().get(0).getTid())) {
			logger.info("vin:{}适配出的tid:{}", vin, resp.getResult().get(0).getTid());
			//vin码消息通知更新
			//epcMessageNotifyService.notifyVinUpdate(vin);
			return SPLITTER.splitToList(resp.getResult().get(0).getTid()).stream().map(Integer::valueOf).collect(Collectors.toList());//获取tid集合
		} else {
			logger.info("vin:{}获取适配车型调正时接口未获取到数据", vin);
		}
		return null;
	}

	private Map<Integer, Map<String, String>> getModeConfig(List<Integer> carModelIdList) {
		Map<Integer, Map<String, String>> resultMap        = Maps.newHashMap();
		List<CarModelConfigEntity>        configEntityList = carModelQueryService.getCarModelConfigListByIds(carModelIdList);
		Set<Integer>                      diff             = Sets.newHashSet(carModelIdList);
		if (CollectionUtils.isNotEmpty(configEntityList)) {
			configEntityList.forEach(entity -> resultMap.put(entity.getModelId(), this.getConfigMap(entity)));
			diff = Sets.difference(diff, configEntityList.stream().map(CarModelConfigEntity::getModelId).collect(Collectors.toSet()));
		}
		logger.info("车型配置中,本地没有的是:{}", JOINER.join(diff));
		if (CollectionUtils.isNotEmpty(diff)) {
			//走正时接口调用获取车型配置
			List<GetCarModelInfoByTidResp> tidRespList = superEpcService.getCarModelInfoByTid(Lists.newArrayList());
			if (CollectionUtils.isNotEmpty(tidRespList)) {
				tidRespList.forEach(resp -> resultMap.put(Integer.valueOf(resp.getTId()), this.getConfigMap(resp)));
			}
		}
		return resultMap;
	}

	private CarModelDto transformCarModelEntity(CarModelEntity carModelEntity) {
		CarModelDto carModelDto = new CarModelDto();
		carModelDto.setCarModelId(carModelEntity.getModelId());
		carModelDto.setCarModelName(carModelEntity.getStandardModelName());//车型名称
		carModelDto.setCarBrandName(carModelEntity.getBrandName());
		carModelDto.setCarSeriesName(carModelEntity.getSeriesName());
		carModelDto.setMakeName(carModelEntity.getMakeName());
		carModelDto.setBrandId(carModelEntity.getBrandId());
		return carModelDto;
	}

	private PartSuitCarDto transformPartModelEntity(PartModelEntity partModelEntity) {
		PartSuitCarDto partSuitCarDto = new PartSuitCarDto();
		partSuitCarDto.setCarModelId(partModelEntity.getModelId());
		partSuitCarDto.setCarModelName(partModelEntity.getModelName());
		partSuitCarDto.setCarBrandName(partModelEntity.getBrandName());
		partSuitCarDto.setCarSeriesName(partModelEntity.getSeriesName());//车系
		partSuitCarDto.setMakeName(partModelEntity.getMakeName());
		partSuitCarDto.setType(partModelEntity.getType());
		return partSuitCarDto;
	}

	private PartSuitCarDto transformGetAdapterModelsResp(GetAdapterModelsResp resp) {
		PartSuitCarDto partSuitCarDto = new PartSuitCarDto();
		partSuitCarDto.setCarModelId(Integer.parseInt(resp.getTid()));
		partSuitCarDto.setCarModelName(resp.getCTimerModelName());
		partSuitCarDto.setCarBrandName(resp.getCOemBrand());
		partSuitCarDto.setCarSeriesName(resp.getCSeriesBbg());//车系
		partSuitCarDto.setMakeName(resp.getCOemName());
		partSuitCarDto.setType(resp.getType());
		return partSuitCarDto;
	}

	/**
	 * 去重modelId相同的车型
	 *
	 * @param carModelEntityList
	 * @return
	 */
	private List<CarModelEntity> getDistinctCarModelEntityList(List<CarModelEntity> carModelEntityList) {
		List<CarModelEntity>               resultList = Lists.newArrayList();
		Map<Integer, List<CarModelEntity>> map        = carModelEntityList.stream().collect(Collectors.groupingBy(CarModelEntity::getModelId));
		map.forEach((k, v) -> resultList.add(v.get(0)));
		return resultList;
	}

	/**
	 * 去重tid相同的车型
	 *
	 * @param respList
	 * @return
	 */
	private List<GetCarModelInfoByTidResp> getDistinctGetCarModelInfoByTidRespList(List<GetCarModelInfoByTidResp> respList) {
		List<GetCarModelInfoByTidResp>              resultList = Lists.newArrayList();
		Map<String, List<GetCarModelInfoByTidResp>> map        = respList.stream().collect(Collectors.groupingBy(GetCarModelInfoByTidResp::getTId));
		map.forEach((k, v) -> resultList.add(v.get(0)));
		return resultList;
	}

	private Map<String, String> getConfigMap(CarModelConfigEntity entity) {
		Map<String, String> map = Maps.newHashMap();
		map.put("GPS导航系统", entity.getGps());
		map.put("氙气大灯", entity.getHidLight());
		map.put("手动自动空调", entity.getAirconMode());
		map.put("铝合金轮毂", entity.getAlloyWheel());
		map.put("自动头灯", entity.getAutoHeadLight());
		map.put("定速巡航", entity.getCruise());
		map.put("后视镜电动调节", entity.getEleRearviewAdj());
		map.put("后视镜电动折叠", entity.getEleRearviewFold());
		map.put("前主座椅电动调节", entity.getEleSeatsAdj());
		map.put("电动天窗", entity.getEleSunroof());
		map.put("前轮胎规格", entity.getFrontTireSpec());
		map.put("后轮胎规格", entity.getRearTireSpec());
		map.put("大灯清洗装置", entity.getHeadLightCleanSys());
		map.put("无钥匙启动", entity.getKeylessBoot());
		map.put("中控彩屏", entity.getLargeColorLcd());
		map.put("真皮座椅", entity.getLeatherSeat());
		map.put("多功能方向盘", entity.getMultiSteeringWheelAdj());
		map.put("全景天窗", entity.getPanoSunroof());
		map.put("前后驻车雷达", entity.getParkingRadar());
		map.put("倒车视频影像", entity.getReversingVideo());
		map.put("前后座椅加热", entity.getSeatHeating());
		return map;
	}

	private Map<String, String> getConfigMap(GetCarModelInfoByTidResp tidResp) {
		Map<String, String> map = Maps.newHashMap();
		map.put("GPS导航系统", tidResp.getGps());
		map.put("氙气大灯", tidResp.getHidLight());
		map.put("手动自动空调", tidResp.getAirconMode());
		map.put("铝合金轮毂", tidResp.getAlloyWheel());
		map.put("自动头灯", tidResp.getAutoHeadLight());
		map.put("定速巡航", tidResp.getCruise());
		map.put("后视镜电动调节", tidResp.getEleRearviewAdj());
		map.put("后视镜电动折叠", tidResp.getEleRearviewFold());
		map.put("前主座椅电动调节", tidResp.getEleSeatsAdj());
		map.put("电动天窗", tidResp.getEleSunroof());
		map.put("前轮胎规格", tidResp.getFrontTireSpec());
		map.put("后轮胎规格", tidResp.getRearTireSpec());
		map.put("大灯清洗装置", tidResp.getHeadLightCleanSys());
		map.put("无钥匙启动", tidResp.getKeylessBoot());
		map.put("中控彩屏", tidResp.getLargeColorLcd());
		map.put("真皮座椅", tidResp.getLeatherSeat());
		map.put("多功能方向盘", tidResp.getMultiSteeringWheelAdj());
		map.put("全景天窗", tidResp.getPanoSunroof());
		map.put("前后驻车雷达", tidResp.getParkingRadar());
		map.put("倒车视频影像", tidResp.getReversingVideo());
		map.put("前后座椅加热", tidResp.getSeatHeating());
		return map;
	}

	private CarModelDto transformGetCarModelInfoByTidResp(GetCarModelInfoByTidResp resp) {
		CarModelDto carModelDto = new CarModelDto();
		carModelDto.setCarModelId(Integer.parseInt(resp.getTId()));
		carModelDto.setCarModelName(resp.getCModelName());
		carModelDto.setCarBrandName(resp.getCOemBrand());
		carModelDto.setCarSeriesName(resp.getCSeriesName());
		carModelDto.setBrandId(Integer.parseInt(resp.getBrandIdTiming()));
		carModelDto.setCarModelConfig(this.getConfigMap(resp));
		return carModelDto;
	}

}

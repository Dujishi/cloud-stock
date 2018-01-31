package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.*;
import com.xiaoka.cloud.stock.core.epc.repo.entity.*;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCCarModelService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCCatalogueService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAllCarModelResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetCarModelInfoByTidResp;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 该服务用于爬取车辆的品牌、车型、车系数据，并存储到数据库中
 * <ul>
 * <li>
 * 1    调用2.1接口分页全量获取车款数据，每页1000条，存储到数据库, {@link #batchSaveCarModel(List)}
 * </li>
 * <li>
 * 2    每1000条数据，批量调用1.4获取车款配置，存储到数据库，{@link #batchSaveCarModelConfig(List)}
 * </li>
 * <li>
 * 3    对车款数据按照车品牌车系分组，将不存在的车系批量存储到数据库，{@link #batchSaveOrUpdateCarSeries(List)} ；
 * 将不存在的车品牌批量存储到数据库，{@link #batchSaveOrUpdateCarBrands(List)}
 * 将不存在的车品牌批量存储到数据库，{@link #batchSaveOrUpdateCarMakes(List)}
 * </li>
 * </ul>
 * <p>
 * <p>
 * <b>NOTE:</b> {@link #batchSaveCarModel(List)} 暂时只能批量执行一次，去重功能后期再考虑
 * </p>
 * <p>
 * <p>
 * Created by sabo on 17/11/2017.
 */
@Service
public class CarInfoCrawlService {

	private static final Logger logger = LoggerFactory.getLogger(CarInfoCrawlService.class);

	@Resource
	SuperEPCCatalogueService superEPCCatalogueService;

	@Resource
	SuperEPCCarModelService superEPCCarModelService;

	@Resource
	CarBrandRepo carBrandRepo;

	@Resource
	CarSeriesRepo carSeriesRepo;

	@Resource
	CarModelRepo carModelRepo;

	@Resource
	CarMakeRepo carMakeRepo;

	@Resource
	CarModelConfigRepo carModelConfigRepo;

	private static final int INSERT_BATCH_SIZE = 1000;

	public void crawlAllCarModel(int specialLoop) {
		logger.info("crawlAllCarModel begin=====");
		int loop = (specialLoop == -1) ? 0 : specialLoop;
		while (true) {
			logger.info("crawlAllCarModel ===== with page {}", loop);
			List<GetAllCarModelResp> getAllCarModelRespList = superEPCCatalogueService.getAllCarModel(loop * 1000);
			if (CollectionUtils.isEmpty(getAllCarModelRespList)) {
				logger.info("crawlAllCarModel end with page {} ===== ", loop);
				break;
			}
			//批量保存车型
			batchSaveCarModel(getAllCarModelRespList);
			//批量保存车型配置
			//batchSaveCarModelConfig(getAllCarModelRespList);
			if (specialLoop != -1) {
				break;
			}
			loop++;
		}
		logger.info("crawlAllCarModel finished=====");
	}

	@Transactional
	public void crawlCarBrandAndSeriesAndMakes() {
		List<CarModelEntity> carModelEntityList = carModelRepo.selectGroupBySeries();
		if (CollectionUtils.isEmpty(carModelEntityList)) {
			logger.error("根据车系group by获取分组为空");
			return;
		}
		batchSaveOrUpdateCarSeries(carModelEntityList);
		batchSaveOrUpdateCarBrands(carModelEntityList);
		batchSaveOrUpdateCarMakes(carModelEntityList);

	}


	private void batchSaveOrUpdateCarSeries(List<CarModelEntity> carModelEntityList) {
		logger.info("Prepare for batch insert {} rows car series", carModelEntityList);
		List<CarSeriesEntity> carSeriesEntityList = new ArrayList<>();
		for (CarModelEntity carModelEntity : carModelEntityList) {
			CarSeriesEntity carSeriesEntity = new CarSeriesEntity();
			carSeriesEntity.setSeriesId(carModelEntity.getSeriesId());
			carSeriesEntity.setSeriesName(carModelEntity.getSeriesName());
			carSeriesEntity.setSeries(carModelEntity.getSeries());
			carSeriesEntity.setSeriesBbg(carModelEntity.getSeriesBbg());
			carSeriesEntity.setSeriesBbgEopYear(carModelEntity.getSeriesBbgEopYear());
			carSeriesEntity.setSeriesBbgSopYear(carModelEntity.getSeriesBbgSopYear());
			carSeriesEntity.setBrandId(carModelEntity.getBrandId());
			carSeriesEntity.setBrandName(carModelEntity.getBrandName());
			carSeriesEntity.setMakeId(carModelEntity.getMakeId());
			carSeriesEntity.setMakeName(carModelEntity.getMakeName());
			carSeriesEntity.setIsValid(1);
			carSeriesEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
			carSeriesEntityList.add(carSeriesEntity);
		}
		//去重逻辑
		List<CarSeriesEntity> insertList     = Lists.newArrayList();
		List<CarSeriesEntity> hasExistedList = carSeriesRepo.selectAllCarSeries();
		if (CollectionUtils.isNotEmpty(hasExistedList)) {
			insertList.addAll(carSeriesEntityList.stream().filter(
					p -> hasExistedList.stream().noneMatch(
							x -> Objects.equals(x.getSeriesId(), p.getSeriesId())
					)
			).collect(Collectors.toList()));

		} else {
			insertList.addAll(carSeriesEntityList);
		}

		if (CollectionUtils.isNotEmpty(insertList)) {
			int result = carSeriesRepo.batchInsert(insertList);
			logger.info("Successfully batch insert {} rows car series ", result);
		}
	}

	private void batchSaveOrUpdateCarBrands(List<CarModelEntity> carModelEntityList) {
		List<CarModelEntity> carModelEntityListSet = SuperEPCUtil.distinctList(carModelEntityList, CarModelEntity::getBrandId);
		logger.info("Prepare for batch insert {} rows car brands within {} rows car model ", carModelEntityListSet.size(), carModelEntityList.size());
		List<CarBrandEntity> carBrandEntityList = new ArrayList<>();
		for (CarModelEntity carModelEntity : carModelEntityListSet) {
			CarBrandEntity carBrandEntity = new CarBrandEntity();
			carBrandEntity.setBrandId(carModelEntity.getBrandId());
			carBrandEntity.setBrandName(carModelEntity.getBrandName());
			carBrandEntity.setFirstLetter(carModelEntity.getFirstLetter());
			carBrandEntity.setIsValid(1);
			carBrandEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
			carBrandEntityList.add(carBrandEntity);
		}
		// 去重逻辑
		List<CarBrandEntity> insertList     = Lists.newArrayList();
		List<CarBrandEntity> hasExistedList = carBrandRepo.selectAllBrands();
		if (CollectionUtils.isNotEmpty(hasExistedList)) {
			insertList.addAll(carBrandEntityList.stream().filter(
					p -> hasExistedList.stream().noneMatch(
							x -> Objects.equals(x.getBrandId(), p.getBrandId())
					)
			).collect(Collectors.toList()));
		} else {
			insertList.addAll(carBrandEntityList);
		}

		if (CollectionUtils.isNotEmpty(insertList)) {
			int result = carBrandRepo.batchInsert(insertList);
			logger.info("Successfully batch insert {} rows car brands ", result);
		}
	}

	private void batchSaveOrUpdateCarMakes(List<CarModelEntity> carModelEntityList) {
		List<CarModelEntity> carModelEntityListSet = SuperEPCUtil.distinctList(carModelEntityList,
				CarModelEntity::getMakeId, CarModelEntity::getBrandId);
		logger.info("Prepare for batch insert {} rows car makes within {} rows car model ", carModelEntityListSet.size(), carModelEntityList.size());
		List<CarMakeEntity> carMakeEntityList = new ArrayList<>();
		for (CarModelEntity carModelEntity : carModelEntityListSet) {
			CarMakeEntity carMakeEntity = new CarMakeEntity();
			carMakeEntity.setMakeId(carModelEntity.getMakeId());
			carMakeEntity.setMakeName(carModelEntity.getMakeName());
			carMakeEntity.setBrandId(carModelEntity.getBrandId());
			carMakeEntity.setBrandName(carModelEntity.getBrandName());
			carMakeEntity.setFirstLetter(carModelEntity.getFirstLetter());
			carMakeEntity.setIsValid(1);
			carMakeEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
			carMakeEntityList.add(carMakeEntity);
		}
		//去重逻辑
		List<CarMakeEntity> insertList     = Lists.newArrayList();
		List<CarMakeEntity> hasExistedList = carMakeRepo.selectAllCarMakes();
		if (CollectionUtils.isNotEmpty(hasExistedList)) {
			insertList.addAll(carMakeEntityList.stream().filter(
					p -> hasExistedList.stream().noneMatch(
							x -> Objects.equals(x.getMakeId(), p.getMakeId())
					)
			).collect(Collectors.toList()));
		} else {
			insertList.addAll(carMakeEntityList);
		}

		int result = carMakeRepo.batchInsert(carMakeEntityList);
		logger.info("Successfully batch insert {} rows car makes ", result);
	}

	/**
	 * //TODO 暂时只能批量执行一次，去重功能后期再考虑
	 *
	 * @param getAllCarModelRespList 每次最大size=1000条
	 */
	private void batchSaveCarModel(List<GetAllCarModelResp> getAllCarModelRespList) {
		logger.info("Prepare for batch insert car model with {} rows getAllCarModelRespList ", getAllCarModelRespList.size());
		List<CarModelEntity> carModelEntityList = new ArrayList<>();
		for (GetAllCarModelResp getAllCarModelResp : getAllCarModelRespList) {
			carModelEntityList.add(convertGetAllCarModelRespIntoCarModelEntity(getAllCarModelResp));
		}

		logger.info("batch insert car model {} rows getAllCarModelRespList ", getAllCarModelRespList.size());
		Lists.partition(carModelEntityList, 200).forEach(list -> {
			//去重逻辑
			List<CarModelEntity> insertList     = Lists.newArrayList();
			List<Integer>        ids            = list.stream().map(CarModelEntity::getModelId).distinct().collect(Collectors.toList());
			List<CarModelEntity> hasExistedList = carModelRepo.selectAllCarModels(ids);
			if (CollectionUtils.isNotEmpty(hasExistedList)) {
				insertList.addAll(list.stream().filter(
						p -> hasExistedList.stream().noneMatch(
								x -> Objects.equals(x.getModelId(), p.getModelId())
										&& Objects.equals(x.getTPostCode(), p.getTPostCode())
										&& Objects.equals(x.getId(), p.getId())
						)
				).collect(Collectors.toList()));
			} else {
				insertList.addAll(list);
			}
			if (CollectionUtils.isNotEmpty(insertList)) {
				int result = carModelRepo.batchInsert(insertList);
				logger.info("Successfully batch insert car model {} rows  with {} rows getAllCarModelRespList ", result, list.size());
			}
		});

	}

	private CarModelEntity convertGetAllCarModelRespIntoCarModelEntity(GetAllCarModelResp getAllCarModelResp) {
		CarModelEntity carModelEntity = new CarModelEntity();
		carModelEntity.setModelId(SuperEPCUtil.integerValueOf(getAllCarModelResp.getTid(), "getAllCarModelResp.getTid()", logger));
		carModelEntity.setModelName(getAllCarModelResp.getCModelName());
		carModelEntity.setModelYear(getAllCarModelResp.getCModelYear());
		carModelEntity.setCapacityInLitreL(getAllCarModelResp.getCCapacityInLitreL());
		carModelEntity.setCapacityInLitreCc(getAllCarModelResp.getCCapacityInLitreCc());
		carModelEntity.setTransmissionType(getAllCarModelResp.getCTransmissionType());
		carModelEntity.setFirstLetter(getAllCarModelResp.getTLetter());
		carModelEntity.setBrandId(SuperEPCUtil.integerValueOf(getAllCarModelResp.getBrandIdTiming(), "getAllCarModelResp.getBrandIdTiming()", logger));
		carModelEntity.setBrandName(getAllCarModelResp.getCOemBrand());
		carModelEntity.setMakeId(SuperEPCUtil.integerValueOf(getAllCarModelResp.getMakeIdTiming(), "getAllCarModelResp.getMakeIdTiming()", logger));
		carModelEntity.setMakeName(getAllCarModelResp.getCOemName());
		carModelEntity.setSeriesId(SuperEPCUtil.integerValueOf(getAllCarModelResp.getSeriesIdTiming(), "getAllCarModelResp.getSeriesIdTiming()", logger));
		carModelEntity.setSeriesName(getAllCarModelResp.getCSeriesName());
		carModelEntity.setSeriesBbg(getAllCarModelResp.getCSeriesBbg());
		carModelEntity.setDmpImp(getAllCarModelResp.getCDmpImp());
		carModelEntity.setMsrpNewLaunach(getAllCarModelResp.getCMsrpNewLaunach());
		carModelEntity.setMsrpNewest(getAllCarModelResp.getCMsrpNewest());
		carModelEntity.setCarBody(getAllCarModelResp.getCCarBody());
		carModelEntity.setDriveForm(getAllCarModelResp.getCDriveForm());
		carModelEntity.setEngineModel(getAllCarModelResp.getCEngineModel());
		carModelEntity.setFuelType(getAllCarModelResp.getCFuelType());
		carModelEntity.setMixturePreparation(getAllCarModelResp.getCMixturePreparation());
		carModelEntity.setModelEop(getAllCarModelResp.getCModelEop());
		carModelEntity.setModelInteriorCode(getAllCarModelResp.getCModelInteriorCode());
		carModelEntity.setModelSop(getAllCarModelResp.getCModelSop());
		carModelEntity.setNmOfTransmissionGear(getAllCarModelResp.getCNmOfTransmissionGear());
		carModelEntity.setPlatform(getAllCarModelResp.getCPlatform());
		carModelEntity.setPower(getAllCarModelResp.getCPower());
		carModelEntity.setQtyOfChairs(getAllCarModelResp.getCQtyOfChairs());
		carModelEntity.setQtyOfDoors(getAllCarModelResp.getCQtyOfDoors());
		carModelEntity.setStructure(getAllCarModelResp.getCStructure());
		carModelEntity.setSuperCharger(getAllCarModelResp.getCSupercharger());
		carModelEntity.setTimerGrade(getAllCarModelResp.getCTimerGrade());
		carModelEntity.setStandardModelId(getAllCarModelResp.getCTimerModelId());
		carModelEntity.setStandardModelName(getAllCarModelResp.getCTimerModelName());
		carModelEntity.setRemarkTransmissionType(getAllCarModelResp.getCTimerRemarkTransmissionType());
		carModelEntity.setStandardTypeId(getAllCarModelResp.getCTimerTypeId());
		carModelEntity.setStandardTypeName(getAllCarModelResp.getCTimerTypeName());
		carModelEntity.setTypeOfAtTransmission(getAllCarModelResp.getCTypeOfAtTransmission());
		carModelEntity.setId(SuperEPCUtil.integerValueOf(getAllCarModelResp.getId(), "getAllCarModelResp.getId()", logger));
		carModelEntity.setEpcNo(SuperEPCUtil.integerValueOf(getAllCarModelResp.getEpcNo(), "getAllCarModelResp.getEpcNo()", logger));
		carModelEntity.setHlVersion(getAllCarModelResp.getTHlVersion());
		carModelEntity.setXid(getAllCarModelResp.getXid());
		carModelEntity.setIsValid(1);
		carModelEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
		carModelEntity.setBatch(getAllCarModelResp.getTBatch());
		carModelEntity.setTCapacityInLitrel(getAllCarModelResp.getTCapacityInLitrel());
		carModelEntity.setTCarYear(getAllCarModelResp.getTCarYear());
		carModelEntity.setTCylinderNum(getAllCarModelResp.getTCylinderNum());
		carModelEntity.setTDrvMode(getAllCarModelResp.getTDrvMode());
		carModelEntity.setTESuffix(getAllCarModelResp.getTESuffix());
		carModelEntity.setTEnvStandard(getAllCarModelResp.getTEnvStandard());
		carModelEntity.setTModeNameWithoutYear(getAllCarModelResp.getTModeNameWithoutYear());
		carModelEntity.setTParagraph(getAllCarModelResp.getTParagraph());
		carModelEntity.setTPostCode(getAllCarModelResp.getTPostCode());
		carModelEntity.setTPowerType(getAllCarModelResp.getTPowerType());
		carModelEntity.setTSetNum(getAllCarModelResp.getTSetNum());
		carModelEntity.setTTransmission(getAllCarModelResp.getTTransmission());
		carModelEntity.setTTypeName(getAllCarModelResp.getTTypeName());
		carModelEntity.setTValid(getAllCarModelResp.getTValid());
		carModelEntity.setSeriesBbgEopYear(getAllCarModelResp.getCSeriesBbgEopYear());
		carModelEntity.setSeriesBbgSopYear(getAllCarModelResp.getCSeriesBbgSopYear());
		carModelEntity.setZcUpdateTime(getAllCarModelResp.getUpdateTime());
		carModelEntity.setOem(getAllCarModelResp.getCOem());
		carModelEntity.setOemAbbrebiation(getAllCarModelResp.getCOemAbbrebiation());
		carModelEntity.setSeries(getAllCarModelResp.getCSeries());
		carModelEntity.setVariantEop(getAllCarModelResp.getCVariantEop());
		carModelEntity.setVariantSop(getAllCarModelResp.getCVariantSop());
		return carModelEntity;
	}

	private void batchSaveCarModelConfig(List<GetAllCarModelResp> getAllCarModelRespList) {
		List<Integer> tidList = new ArrayList<>();
		for (GetAllCarModelResp getAllCarModelResp : getAllCarModelRespList) {
			//TODO 批量 convert getAllCarModelRespList into tids , 一次1000条是否合理？
			tidList.add(SuperEPCUtil.integerValueOf(getAllCarModelResp.getTid(), "getAllCarModelResp.getCTimerModelId()", logger));
		}
		List<GetCarModelInfoByTidResp> getCarModelInfoByTidRespList = superEPCCarModelService.getCarModelInfoByTid(tidList);
		List<CarModelConfigEntity>     carModelConfigEntityList     = new ArrayList<>();
		for (GetCarModelInfoByTidResp getCarModelInfoByTidResp : getCarModelInfoByTidRespList) {
			carModelConfigEntityList.add(convertGetCarModelInfoByTidRespIntoCarModelConfigEntity(getCarModelInfoByTidResp));
		}

		//去重逻辑
		List<CarModelConfigEntity> insertList     = Lists.newArrayList();
		List<Integer>              modelIds       = carModelConfigEntityList.stream().map(CarModelConfigEntity :: getModelId).collect(Collectors.toList());
		List<CarModelConfigEntity> hasExistedList = carModelConfigRepo.selectByModelIds(modelIds);
		if (CollectionUtils.isNotEmpty(hasExistedList)) {
			insertList.addAll(carModelConfigEntityList.stream().filter(
					p -> hasExistedList.stream().noneMatch(
							x -> Objects.equals(x.getModelId(), p.getModelId())
					)
			).collect(Collectors.toList()));
		} else {
			insertList.addAll(carModelConfigEntityList);
		}

		if (CollectionUtils.isNotEmpty(insertList)) {
			carModelConfigRepo.batchInsert(insertList);
		}
	}

	private CarModelConfigEntity convertGetCarModelInfoByTidRespIntoCarModelConfigEntity(GetCarModelInfoByTidResp getCarModelInfoByTidResp) {
		CarModelConfigEntity carModelConfigEntity = new CarModelConfigEntity();
//		carModelConfigEntity.setConfigId(getCarModelInfoByTidResp.get);
		carModelConfigEntity.setModelId(SuperEPCUtil.integerValueOf(getCarModelInfoByTidResp.getTId(), "getCarModelInfoByTidResp.getTId()", logger));
		carModelConfigEntity.setGps(getCarModelInfoByTidResp.getGps());
		carModelConfigEntity.setHidLight(getCarModelInfoByTidResp.getHidLight());
		carModelConfigEntity.setAirconMode(getCarModelInfoByTidResp.getAirconMode());
		carModelConfigEntity.setAlloyWheel(getCarModelInfoByTidResp.getAlloyWheel());
		carModelConfigEntity.setAutoHeadLight(getCarModelInfoByTidResp.getAutoHeadLight());
		carModelConfigEntity.setCruise(getCarModelInfoByTidResp.getCruise());
		carModelConfigEntity.setEleRearviewAdj(getCarModelInfoByTidResp.getEleRearviewAdj());
		carModelConfigEntity.setEleRearviewFold(getCarModelInfoByTidResp.getEleRearviewFold());
		carModelConfigEntity.setEleSeatsAdj(getCarModelInfoByTidResp.getEleSeatsAdj());
		carModelConfigEntity.setEleSunroof(getCarModelInfoByTidResp.getEleSunroof());
		carModelConfigEntity.setFrontTireSpec(getCarModelInfoByTidResp.getFrontTireSpec());
		carModelConfigEntity.setRearTireSpec(getCarModelInfoByTidResp.getRearTireSpec());
		carModelConfigEntity.setHeadLightCleanSys(getCarModelInfoByTidResp.getHeadLightCleanSys());
		carModelConfigEntity.setKeylessBoot(getCarModelInfoByTidResp.getKeylessBoot());
		carModelConfigEntity.setLargeColorLcd(getCarModelInfoByTidResp.getLargeColorLcd());
		carModelConfigEntity.setLeatherSeat(getCarModelInfoByTidResp.getLeatherSeat());
		carModelConfigEntity.setMultiSteeringWheelAdj(getCarModelInfoByTidResp.getMultiSteeringWheelAdj());
		carModelConfigEntity.setPanoSunroof(getCarModelInfoByTidResp.getPanoSunroof());
		carModelConfigEntity.setParkingRadar(getCarModelInfoByTidResp.getParkingRadar());
		carModelConfigEntity.setReversingVideo(getCarModelInfoByTidResp.getReversingVideo());
		carModelConfigEntity.setSeatHeating(getCarModelInfoByTidResp.getSeatHeating());
		carModelConfigEntity.setPicPath(getCarModelInfoByTidResp.getPicPath());
		carModelConfigEntity.setIsValid(1);
		carModelConfigEntity.setCreateBy(SuperEPCconstant.CREATE_BY);
		return carModelConfigEntity;
	}

}

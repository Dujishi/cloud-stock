package com.xiaoka.cloud.stock.service.epc.impl;

import com.google.common.base.Splitter;
import com.xiaoka.cloud.stock.cdd.car.entity.CarUserCarEntity;
import com.xiaoka.cloud.stock.cdd.car.repo.CarUserCarExtRepo;
import com.xiaoka.cloud.stock.cdd.car.repo.CarUserCarRepo;
import com.xiaoka.cloud.stock.core.epc.repo.VinEpcResultRepo;
import com.xiaoka.cloud.stock.core.epc.repo.VinSampleRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.VinEpcResultEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.VinSampleEntity;
import com.xiaoka.cloud.stock.service.epc.EpcSourceEnum;
import com.xiaoka.cloud.stock.service.epc.SuperEpcService;
import com.xiaoka.cloud.stock.service.epc.VinSampleService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAssemblyCatalogueResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetCarModelInfoByTidResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTidResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.SuperEPCResp;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.mid.task.Task;
import com.xiaoka.platform.api.vehicle.result.dictionary.VehicleBrand;
import com.xiaoka.platform.api.vehicle.result.dictionary.VehicleModel;
import com.xiaoka.platform.api.vehicle.result.dictionary.VehicleSeries;
import com.xiaoka.platform.api.vehicle.service.VehicleDictionaryService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 报表：利用hive，关联car_type_model、vin_sample，vin_epc_result获取报表
 */
@Service("vinSampleService")
@Task
public class VinSampleServiceImpl implements VinSampleService {


	private Logger logger = LoggerFactory.getLogger(VinSampleServiceImpl.class);

	/**
	 * 每个车型暂定3个样例vin码
	 */
	private static final int MAX_SAMPLE_COUNT_PER_MODEL_ID = 3;

	/**
	 * 统计今天已经执行了多少条，正时合同规定一天最多不超过40万，所以最大为10w
	 */
	private static final int MAX_ZS_INVOKE_COUNT_PER_DAY = 100000;

	private static final int VIN_SAMPLE_SIZE_PER_PAGE = 500;

	@Resource
	private VehicleDictionaryService vehicleDictionaryService;

	@Resource
	private VinSampleRepo vinSampleRepo;

	@Resource
	private VinEpcResultRepo vinEpcResultRepo;

	@Resource
	private SuperEpcService superEpcService;

	@Resource
	private CarUserCarRepo carUserCarRepo;

	@Resource
	private CarUserCarExtRepo carUserCarExtRepo;

	@Override public void createVinSampleForAllCarBrand() {
		createVinSample(null);

	}

	@Override
	public void createVinSample(List<Integer> carBrandIdList) {

		List<VehicleBrand> vehicleBrandList = vehicleDictionaryService.getCarBrandList();
		if (CollectionUtils.isEmpty(vehicleBrandList)) {
			logger.error("车型库无数据");
			return;
		}
		for (VehicleBrand vehicleBrand : vehicleBrandList) {
			if (CollectionUtils.isEmpty(carBrandIdList)) {
				createVinSampleForBrand(vehicleBrand);
			} else {
				if (!carBrandIdList.contains(vehicleBrand.getBrandId())) {
					continue;
				}
				createVinSampleForBrand(vehicleBrand);
			}
		}

	}

	//	@Scheduled(cron = "0/5 * *  * * ? ")   //每5秒执行一次
	@Override
	public void checkVinSample() {
		checkVinSampleBySource(EpcSourceEnum.ZS);
	}

	private void checkVinSampleBySource(EpcSourceEnum epcSourceEnum) {

		//根据接口调用时间，先统计今天已经调用多少次
		int invokedCount = vinEpcResultRepo.countInvokedToday(epcSourceEnum.getCode());
		logger.info("invokedCount={}", invokedCount);

		int invokableCount = MAX_ZS_INVOKE_COUNT_PER_DAY - invokedCount;
		logger.info("invokableCount={}", invokableCount);
		if (invokableCount <= 0) {
			logger.info("今天服务商===【{}】的调用次数已经用完", epcSourceEnum.getDes());
			return;
		}

		// 分页查询vin码样例，最大数不超过可用的查询次数
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("trustRateColumn", epcSourceEnum.getColumn());
		int vinSampleCount = vinSampleRepo.countVinSample(paramMap);//count没有评分的vin
		if (vinSampleCount > invokableCount) {
			vinSampleCount = invokableCount;
		}

		int totalPage = (vinSampleCount % VIN_SAMPLE_SIZE_PER_PAGE) == 0 ?
				(vinSampleCount / VIN_SAMPLE_SIZE_PER_PAGE) :
				(vinSampleCount / VIN_SAMPLE_SIZE_PER_PAGE) + 1;
		for (int page = 1; page <= totalPage; page++) {
			paramMap.put("start", (page - 1) * VIN_SAMPLE_SIZE_PER_PAGE);
			paramMap.put("offset", VIN_SAMPLE_SIZE_PER_PAGE);

			List<VinSampleEntity> vinSampleEntityList = vinSampleRepo.selectVinSample(paramMap);//查找没有评分的vin
			if (CollectionUtils.isEmpty(vinSampleEntityList)) {
				continue;
			}
			for (VinSampleEntity vinSampleEntity : vinSampleEntityList) {
				List<VinEpcResultEntity> vinEpcResultEntityList = vinEpcResultRepo.selectByVinAndSource(vinSampleEntity.getVin(), epcSourceEnum.getCode());
				if (CollectionUtils.isEmpty(vinEpcResultEntityList)) {//TODO 暂时调用过接口的不重新调用接口
					if (epcSourceEnum == EpcSourceEnum.ZS) {
						VinEpcResultEntity vinEpcResultEntity = createByEpcZS(vinSampleEntity.getVin());
						vinEpcResultRepo.insert(vinEpcResultEntity);

						VinSampleEntity vinSampleEntityParam = new VinSampleEntity();
						vinSampleEntityParam.setVin(vinSampleEntity.getVin());
						vinSampleEntityParam
								.setTrustRate0(calculateTrustRate(vinEpcResultEntity.isHasEpc(), vinEpcResultEntity.isHasModelData()));
						vinSampleRepo.updateBySelective(vinSampleEntityParam);
					} else if (epcSourceEnum == EpcSourceEnum.AM) {
//						vinSampleEntityParam.setTrustRate1(calculateTrustRate());
					}

				}
			}
		}
	}

	private VinEpcResultEntity createByEpcZS(String vin) {
		VinEpcResultEntity vinEpcResultEntity = new VinEpcResultEntity();
		vinEpcResultEntity.setVin(vin);
		vinEpcResultEntity.setIsValid(true);
		vinEpcResultEntity.setHasEpc(false);
		vinEpcResultEntity.setHasModelData(false);
		vinEpcResultEntity.setSource(EpcSourceEnum.ZS.getCode());
		SuperEPCResp<List<GetTidResp>> resp = null;
		try {
			resp = superEpcService.getTid(vin, null);
		} catch (Exception e) {
			logger.error("superEpcService.getTid异常", e);
		}
		if (Objects.nonNull(resp) && CollectionUtils.isNotEmpty(resp.getResult()) && StringUtils.isNotBlank(resp.getResult().get(0).getTid())) {
			logger.info("vin:{}适配出的tid:{}", vin, resp.getResult().get(0).getTid());
			//vin码消息通知更新
			//epcMessageNotifyService.notifyVinUpdate(vin);
			List<Integer> tids = Splitter.on(",").trimResults().splitToList(resp.getResult().get(0).getTid()).stream().map(Integer::valueOf)
			               .collect(Collectors.toList());//获取tid集合
			if (CollectionUtils.isNotEmpty(tids)) {

				List<GetCarModelInfoByTidResp> getCarModelInfoByTidRespList = null;
				try {
					getCarModelInfoByTidRespList = superEpcService.getCarModelInfoByTid(tids);
				} catch (Exception e) {
					logger.error("superEpcService.getCarModelInfoByTid异常", e);
				}

				if (CollectionUtils.isNotEmpty(getCarModelInfoByTidRespList)) {
					vinEpcResultEntity.setHasModelData(true);
					GetCarModelInfoByTidResp carModelInfoByTidResp = getCarModelInfoByTidRespList.get(0);
					vinEpcResultEntity.setCarBrandName(carModelInfoByTidResp.getCOemBrand());
					vinEpcResultEntity.setCarSeriesName(carModelInfoByTidResp.getCSeriesName());
					vinEpcResultEntity.setCarModelName(carModelInfoByTidResp.getCModelName());
					vinEpcResultEntity.setCarBrandFactory(carModelInfoByTidResp.getCOemName());
					vinEpcResultEntity.setTransmission(carModelInfoByTidResp.getCTransmissionType());
					vinEpcResultEntity.setDisplacement(carModelInfoByTidResp.getCCapacityInLitreL());
					vinEpcResultEntity.setModelYear(carModelInfoByTidResp.getCModelYear());
				}
				for (Integer tid : tids) {
					List<GetAssemblyCatalogueResp> getAssemblyCatalogueRespList = null;
					try{
						getAssemblyCatalogueRespList = superEpcService.getAssemblyCatalogue(tid, 0);
					}catch (Exception e){
						logger.error("访问getAssemblyCatalogue异常", e);
					}
					if (CollectionUtils.isNotEmpty(getAssemblyCatalogueRespList)) {
						vinEpcResultEntity.setHasEpc(true);
						break;
					}
				}
			}
		} else {
			logger.info("vin:{}获取适配车型调正时接口未获取到数据", vin);
		}
		return vinEpcResultEntity;

	}

	private Integer calculateTrustRate(boolean hasEpc, boolean hasModelData) {
		if (hasModelData) {
			if (hasEpc) {
				return 2;
			}
			return 1;
		}
		return 0;
	}

	private void createVinSampleForBrand(VehicleBrand vehicleBrand) {
		List<VehicleSeries> vehicleSeriesList = vehicleDictionaryService.getCarSeriesList(vehicleBrand.getBrandId());
		if (CollectionUtils.isEmpty(vehicleSeriesList)) {
			logger.warn("品牌id={} 品牌name={} 下无车系", vehicleBrand.getBrandId(), vehicleBrand.getBrandName());
			return;
		}
		for (VehicleSeries vehicleSeries : vehicleSeriesList) {
			//获取车型列表，或者根据品牌获取车型列表
			List<VehicleModel> vehicleModelList = vehicleDictionaryService.getCarModelList(vehicleSeries.getSeriesId());
			if (CollectionUtils.isNotEmpty(vehicleModelList)) {
				for (VehicleModel vehicleModel : vehicleModelList) {
					//循环车型列表，根据车型查找用户车型，车型对应的VIN码
					Map<String, Boolean> vinMap = selectVinByVehicleModelId(Integer.valueOf(vehicleModel.getModelId()), MAX_SAMPLE_COUNT_PER_MODEL_ID);
					if (vinMap == null || vinMap.size() == 0)  {
						logger.info("车型={}不存在vin码", Jackson.mobile().writeValueAsString(vehicleModel));
						continue;
					}
					List<VinSampleEntity> vinSampleEntityList = new ArrayList<>();
					for (String vinCode : vinMap.keySet()) {
						VinSampleEntity vinSampleEntity = new VinSampleEntity();
						vinSampleEntity.setVin(vinCode);
						vinSampleEntity.setCarBrandId(vehicleBrand.getBrandId());
						vinSampleEntity.setCarBrandName(vehicleBrand.getBrandName());
						vinSampleEntity.setCarSeriesId(vehicleSeries.getSeriesId());
						vinSampleEntity.setCarSeriesName(vehicleSeries.getSeriesName());
						vinSampleEntity.setCarModelId(Integer.valueOf(vehicleModel.getModelId()));
						vinSampleEntity.setCarModelName(vehicleModel.getModelName());
						vinSampleEntity.setTrustRate(vinMap.get(vinCode) ? 1 : 0);
						//						vinSampleEntity.setModelYear(vehicleModel.getD);//TODO
						vinSampleEntity.setGuidePrice(String.valueOf(vehicleModel.getGuidePrice()));
						vinSampleEntity.setIsValid(true);
						vinSampleEntityList.add(vinSampleEntity);
					}
					vinSampleRepo.batchInsertIgnore(vinSampleEntityList);
				}
			}
		}
	}

	//循环车型列表，根据车型查找车型对应的VIN码
	private Map<String, Boolean> selectVinByVehicleModelId(Integer vehicleModelId, int sampleCountPerModelId) {

		CarUserCarEntity carUserCarEntity = new CarUserCarEntity();
		carUserCarEntity.setCarModel(vehicleModelId);
		carUserCarEntity.setIsDeleted(false);

		List<CarUserCarEntity> carUserCarEntityList = carUserCarRepo.selectByModelId(carUserCarEntity);
		if (CollectionUtils.isEmpty(carUserCarEntityList)) {
			return null;
		}

		Map<Integer, String> carVinMap = carUserCarEntityList.stream().collect(Collectors.toMap(CarUserCarEntity::getCarId, CarUserCarEntity::getCarVehicleFrameNo));
		List<Integer> userCarIdList = carUserCarExtRepo.selectByUserCarIdList(carVinMap.keySet());
		Map<String, Boolean> vinMap = new HashMap<>();
		if (CollectionUtils.isNotEmpty(userCarIdList)) {
			for (Integer userCarId : userCarIdList) {
				if (vinMap.containsKey(carVinMap.get(userCarId))) {
					continue;
				} else {
					vinMap.put(carVinMap.get(userCarId), true);
				}
				if (vinMap.size() >= sampleCountPerModelId) {
					return vinMap;
				}
			}
		}

		for (String vin : carVinMap.values()) {
			if (vinMap.containsKey(vin)) {
				continue;
			} else {
				vinMap.put(vin, false);
			}
			if (vinMap.size() >= sampleCountPerModelId) {
				return vinMap;
			}
		}
		return vinMap;
	}

}

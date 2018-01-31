package com.xiaoka.cloud.stock.service.epc.impl;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelPartRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelPicMarkRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicMarkEntity;
import com.xiaoka.cloud.stock.service.epc.CarModelPicMarkService;
import com.xiaoka.cloud.stock.service.epc.ZeroEpcService;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkItem;
import com.xiaoka.cloud.stock.service.epc.util.ImageUtil;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCAdaptationService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPartsInfoResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetPicInfoResp;
import com.xiaoka.ddyc.tool.storage.StorageService4UpYun;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.http.HttpExecutor;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * Created by sabo on 25/11/2017.
 */
@Service
public class CarModelPicMarkServiceImpl implements CarModelPicMarkService {

	private static final Logger logger = LoggerFactory.getLogger(CarModelPicMarkServiceImpl.class);

	@Resource
	CarModelRepo carModelRepo;

	@Resource
	CarModelPartRepo carModelPartRepo;

	@Resource
	CarModelPicMarkRepo carModelPicMarkRepo;

	@Resource
	StorageService4UpYun xkStorageService;

	@Resource
	private SuperEPCAdaptationService superEPCAdaptationService;

	@Resource
	private ZeroEpcService zeroEpcService;

	@Override
	public List<CarModelPicMarkDto> getCarModelPicMarksByModelId(Integer pageNumber, Integer modelId) {
		//分页查询modelId, 分页查多条的场景很低
		List<CarModelEntity> carModelEntityList = carModelRepo.selectGroupByModelId(pageNumber, 1, modelId);
		//根据modelId查询车款下的图片和配件
		List<CarModelPicMarkDto> carModelPicMarkDtoList = new ArrayList<>();
		for (CarModelEntity carModelEntity : carModelEntityList) {
			List<CarModelPartEntity> carModelPartEntityList     = carModelPartRepo.selectGroupByPicNumAndPicName(carModelEntity.getModelId());
			CarModelPicMarkEntity    carModelPicMarkEntityParam = new CarModelPicMarkEntity();
			carModelPicMarkEntityParam.setModelId(carModelEntity.getModelId());
			List<CarModelPicMarkEntity> carModelPicMarkEntityList = carModelPicMarkRepo.select(carModelPicMarkEntityParam);
			carModelPicMarkDtoList
					.addAll(buildByCarModelParrs(carModelPartEntityList, new ArrayList<>(carModelPicMarkEntityList)));
		}
		return carModelPicMarkDtoList;

	}

	@Override
	public List<CarModelPicMarkDto> getCarModelSubAssemblyPicMarksByModelId(Integer modelId, String assemblyName, String subAssemblyName) {
		List<GetPicInfoResp> picInfoRespList = superEPCAdaptationService.getPicInfo(modelId, 0, assemblyName, subAssemblyName, null);
		if (CollectionUtils.isEmpty(picInfoRespList)){
			return Collections.emptyList();
		}
		return getCarModelPicMarkDtoList(modelId, picInfoRespList);
	}

	@Override
	public void insertByCarModelPartEntityTask(Integer pageNumber, Integer pageSize, Integer modelId) {
		logger.info("Prepare insert car model pic mark from pageNumber=={}, with pageSize=={} >>>>>>>>>>>>>>>>>>>>>>>>>>>", pageNumber, pageSize);
		int count      = carModelRepo.countDistinctModelId();
		int totalPages = count / pageSize + 1;
		//分页查询modelId, 分页查多条的场景很低
		List<CarModelEntity> carModelEntityList = carModelRepo.selectGroupByModelId(pageNumber, pageSize, modelId);
		ExecutorService      es                 = Executors.newFixedThreadPool(20);
		//根据modelId查询车款下的图片和配件
		for (CarModelEntity carModelEntity : carModelEntityList) {
			es.execute(() -> {
				List<CarModelPartEntity> carModelPartEntityList = carModelPartRepo.selectGroupByPicNumAndPicName(carModelEntity.getModelId());
				for (CarModelPartEntity carModelPartEntity : carModelPartEntityList) {
					CarModelPicMarkEntity carModelPicMarkEntity = new CarModelPicMarkEntity();
					carModelPicMarkEntity.setModelId(carModelPartEntity.getModelId());
					carModelPicMarkEntity.setPicNum(carModelPartEntity.getPicNum());
					carModelPicMarkEntity.setPicName(carModelPartEntity.getPicName());
					if (CollectionUtils.isNotEmpty(carModelPicMarkRepo.select(carModelPicMarkEntity))) {
						logger.info("model pic mark exists with modelId={}, picNum={}, picName={}", carModelPartEntity.getModelId(),
								carModelPartEntity.getPicNum(), carModelPartEntity.getPicName());
						continue;
					}
					insertByCarModelPartEntity(carModelPartEntity);
				}
			});
		}
		es.shutdown();
		try {
			boolean finshed = es.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			logger.error("awaitTermination error", e);
		}
		logger.info("insert car model pic mark finished !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
	}

	@Override
	public CarModelPicMarkEntity insertByCarModelPartEntity(CarModelPartEntity carModelPartEntity) {
		CarModelPicMarkEntity carModelPicMarkEntity = new CarModelPicMarkEntity();
		carModelPicMarkEntity.setModelId(carModelPartEntity.getModelId());
		carModelPicMarkEntity.setPicNum(carModelPartEntity.getPicNum());
		carModelPicMarkEntity.setPicName(carModelPartEntity.getPicName());

		final String epcPicPath = SuperEPCconstant.EPC_IMG_HOST + carModelPartEntity.getPicPath();
		final String upyunPath  = genePath(carModelPicMarkEntity.getModelId(), carModelPicMarkEntity.getPicNum(), carModelPicMarkEntity.getPicName());

		byte[] picBytes = null;
		try {
			picBytes = Utils.get(HttpExecutor.class).get(epcPicPath).executeAsByte();
		} catch (Exception e) {
			logger.error("get EPC pic=={} failed", epcPicPath);
			carModelPicMarkEntity.setMarkedErrorType(1);
			carModelPicMarkEntity.setPicLocalPath(null);
		}
		if (picBytes != null) {
			if (xkStorageService.writeFile(upyunPath, picBytes)) {
				carModelPicMarkEntity.setPicLocalPath(upyunPath);
				carModelPicMarkEntity.setMarkedErrorType(null);
				logger.info("Successfully write EPC pic=={} to upyun with path={} !!!", epcPicPath, upyunPath);
			} else {
				logger.error("write EPC pic=={} to upyun with path={} failed", epcPicPath, upyunPath);
				carModelPicMarkEntity.setMarkedErrorType(2);
				carModelPicMarkEntity.setPicLocalPath(null);
			}
		} else {
			carModelPicMarkEntity.setMarkedErrorType(1);
			carModelPicMarkEntity.setPicLocalPath(null);
		}
		carModelPicMarkEntity.setHasMarked(0);
		carModelPicMarkEntity.setIsValid(1);
		carModelPicMarkRepo.insert(carModelPicMarkEntity);
		logger.info("Successfully insert model_pic_mark with modelId={}, picNum={}, picName={}", carModelPartEntity.getModelId(),
				carModelPartEntity.getPicNum(), carModelPartEntity.getPicName());
		return carModelPicMarkEntity;
	}

	private String genePath(Integer modelId, String PicNum, String picName) {
		return "/epc" + "/" + String.valueOf(modelId) + "/" + PicNum + "/" + picName + ".png";
	}

	/**
	 * 更新图片的打点信息，
	 *
	 * @param carModelPicMarkDto
	 */
	@Override
	public Integer updateCarModelPicMark(CarModelPicMarkDto carModelPicMarkDto) {

		CarModelPicMarkEntity carModelPicMarkEntityParam = new CarModelPicMarkEntity();
		carModelPicMarkEntityParam.setModelId(carModelPicMarkDto.getModelId());
		carModelPicMarkEntityParam.setPicNum(carModelPicMarkDto.getPicNum());
		carModelPicMarkEntityParam.setPicName(carModelPicMarkDto.getPicName());

		List<CarModelPicMarkEntity> carModelPicMarkEntityList = carModelPicMarkRepo.select(carModelPicMarkEntityParam);
		if (CollectionUtils.isEmpty(carModelPicMarkEntityList)) {
			logger.error("no car model pic found with modelId={}, picNum={}, picName={}", carModelPicMarkDto.getModelId(),
					carModelPicMarkDto.getPicNum(), carModelPicMarkDto.getPicName());
			return 0;
		}

		carModelPicMarkEntityParam.setRawPicHeight(carModelPicMarkDto.getRawPicHeight());
		carModelPicMarkEntityParam.setRawPicWidth(carModelPicMarkDto.getRawPicWidth());
//		carModelPicMarkEntityParam.setMarkedPicHeight(carModelPicMarkDto.getMarkedPicHeight());
//		carModelPicMarkEntityParam.setMarkedPicWidth(carModelPicMarkDto.getMarkedPicWidth());
		if (CollectionUtils.isEmpty(carModelPicMarkDto.getCarModelPicMarkItemList())) {
			carModelPicMarkEntityParam.setMarkSet(null);
		} else {
			carModelPicMarkEntityParam.setMarkSet(Jackson.mobile().writeValueAsString(carModelPicMarkDto.getCarModelPicMarkItemList()));
		}
		carModelPicMarkEntityParam.setHasMarked(1);
		carModelPicMarkEntityParam.setUpdateBy(carModelPicMarkDto.getUpdateBy());
		return carModelPicMarkRepo.updateBySelective(carModelPicMarkEntityParam);
	}

	@Override
	public CarModelPicMarkDto getCarModelPicMark(Integer modelId, String picNum, String picName, String extraParam, CloudSupplierUserDto cloudSupplierUserDto) {
		if (StringUtils.isNotBlank(extraParam)){
			return zeroEpcService.getCarModelPicMarkDto(extraParam, cloudSupplierUserDto);
		}
		CarModelPicMarkEntity carModelPicMarkEntity = new CarModelPicMarkEntity();
		carModelPicMarkEntity.setModelId(modelId);
		carModelPicMarkEntity.setPicNum(picNum);
		carModelPicMarkEntity.setPicName(picName);
		List<CarModelPicMarkEntity> carModelPicMarkEntityList = carModelPicMarkRepo.select(carModelPicMarkEntity);
		if (CollectionUtils.isEmpty(carModelPicMarkEntityList)) {
			logger.error("no car model pic found with modelId={}, picNum={}, picName={}", modelId,
					picNum, picName);
			return null;
		}
		carModelPicMarkEntity = carModelPicMarkEntityList.get(0);
		return buildCarModelPicMarkDto(carModelPicMarkEntity);


	}

	private CarModelPicMarkDto buildCarModelPicMarkDto(CarModelPicMarkEntity carModelPicMarkEntity) {
		CarModelPicMarkDto carModelPicMarkDto = new CarModelPicMarkDto();
		carModelPicMarkDto.setModelId(carModelPicMarkEntity.getModelId());
		carModelPicMarkDto.setPicNum(carModelPicMarkEntity.getPicNum());
		carModelPicMarkDto.setPicName(carModelPicMarkEntity.getPicName());

		carModelPicMarkDto.setRawPicHeight(carModelPicMarkEntity.getRawPicHeight());
		carModelPicMarkDto.setRawPicWidth(carModelPicMarkEntity.getRawPicWidth());
//		carModelPicMarkDto.setMarkedPicHeight(carModelPicMarkEntity.getMarkedPicHeight());
//		carModelPicMarkDto.setMarkedPicWidth(carModelPicMarkEntity.getMarkedPicWidth());
		carModelPicMarkDto.setMarkSet(carModelPicMarkEntity.getMarkSet());
		carModelPicMarkDto.setHasMarked(carModelPicMarkEntity.getHasMarked());
		carModelPicMarkDto.setPicLocalPath(SuperEPCconstant.EPC_IMG_HOST + carModelPicMarkEntity.getPicLocalPath());
		if (StringUtils.isNotBlank(carModelPicMarkEntity.getMarkSet())) {
			carModelPicMarkDto.setCarModelPicMarkItemList(
					Jackson.mobile().readValue(carModelPicMarkEntity.getMarkSet(), new TypeReference<List<CarModelPicMarkItem>>() {
					}));
		}
		return carModelPicMarkDto;
	}

	@Override
	public List<CarModelPicMarkDto> queryCarModelPicMarks(List<CarModelPicMarkEntity> carModelPicMarkEntities) {
		if (CollectionUtils.isEmpty(carModelPicMarkEntities)) {
			return Collections.emptyList();
		}

		List<CarModelPicMarkEntity> carModelPicMarkEntityList = carModelPicMarkRepo.selectByList(carModelPicMarkEntities);
		if (CollectionUtils.isEmpty(carModelPicMarkEntityList)) {
			logger.error("no car model pic found with params:{}", JSON.toJSONString(carModelPicMarkEntities));
			return Collections.emptyList();
		}
		List<CarModelPicMarkDto> carModelPicMarkResults = Lists.newArrayList();
		carModelPicMarkEntityList.forEach(p -> carModelPicMarkResults.add(buildCarModelPicMarkDto(p)));
		return carModelPicMarkResults;
	}

	/**
	 * 将车型下的配件，按照图号+图名称分组，
	 *
	 * @param carModelPartEntityList
	 * @return
	 */
	private List<CarModelPicMarkDto> buildByCarModelParrs(List<CarModelPartEntity> carModelPartEntityList,
														  List<CarModelPicMarkEntity> carModelPicMarkEntityList) {
		List<CarModelPicMarkDto> carModelPicMarkDtoList = new ArrayList<>();
		for (CarModelPartEntity carModelPartEntity : carModelPartEntityList) {
			CarModelPicMarkDto carModelPicMarkDto = new CarModelPicMarkDto();
			carModelPicMarkDto.setModelId(carModelPartEntity.getModelId());
			carModelPicMarkDto.setPicNum(carModelPartEntity.getPicNum());
			carModelPicMarkDto.setPicName(carModelPartEntity.getPicName());
			carModelPicMarkDto.setOptionalPicSequences(Arrays.asList(carModelPartEntity.getPicSequence().split(",")));

			CarModelPicMarkEntity carModelPicMarkEntity = filterByModelIdAndPicNumAndPicName(carModelPartEntity, carModelPicMarkEntityList);
			if (carModelPicMarkEntity == null) {
				carModelPicMarkEntity = insertByCarModelPartEntity(carModelPartEntity);
			} else {
				carModelPicMarkEntityList.remove(carModelPicMarkEntity);//从列表中remove，提升下次查找效率
			}
			carModelPicMarkDto.setRawPicHeight(carModelPicMarkEntity.getRawPicHeight());
			carModelPicMarkDto.setRawPicWidth(carModelPicMarkEntity.getRawPicWidth());
//			carModelPicMarkDto.setMarkedPicHeight(carModelPicMarkEntity.getMarkedPicHeight());
//			carModelPicMarkDto.setMarkedPicWidth(carModelPicMarkEntity.getMarkedPicWidth());
			carModelPicMarkDto.setMarkSet(carModelPicMarkEntity.getMarkSet());
			carModelPicMarkDto.setHasMarked(carModelPicMarkEntity.getHasMarked());
			carModelPicMarkDto.setPicLocalPath(xkStorageService.getBindAddress() + carModelPicMarkEntity.getPicLocalPath());
			if (StringUtils.isNotBlank(carModelPicMarkEntity.getMarkSet())) {
				carModelPicMarkDto.setCarModelPicMarkItemList(
						Jackson.mobile().readValue(carModelPicMarkEntity.getMarkSet(), new TypeReference<List<CarModelPicMarkItem>>() {
						}));
			}
			carModelPicMarkDtoList.add(carModelPicMarkDto);
		}
		return carModelPicMarkDtoList;
	}

	private CarModelPicMarkEntity filterByModelIdAndPicNumAndPicName(CarModelPartEntity carModelPartEntity,
																	 List<CarModelPicMarkEntity> carModelPicMarkEntityList) {
		if (CollectionUtils.isEmpty(carModelPicMarkEntityList)) {
			return null;
		}
		for (CarModelPicMarkEntity carModelPicMarkEntity : carModelPicMarkEntityList) {
			if (Objects.equals(carModelPartEntity.getModelId(), carModelPicMarkEntity.getModelId())
					&& Objects.equals(carModelPartEntity.getPicNum(), carModelPicMarkEntity.getPicNum())
					&& Objects.equals(carModelPartEntity.getPicName(), carModelPicMarkEntity.getPicName())) {
				return carModelPicMarkEntity;
			}
		}
		return null;
	}

	private List<CarModelPicMarkDto> getCarModelPicMarkDtoList(Integer modelId, List<GetPicInfoResp> picInfoRespList){
		List<CarModelPicMarkDto> carModelPicMarkDtoList = Lists.newArrayList();
		//以picNum和picName两个维度进行返回
		Map<String, List<GetPicInfoResp>> picNumMap = picInfoRespList.stream().collect(Collectors.groupingBy(GetPicInfoResp::getPicNum));
		//批量获取图号下的配件
		List<GetPartsInfoResp> getPartsInfoRespList = superEPCAdaptationService.getPartsInfo(modelId, null, null, Lists.newArrayList(picNumMap.keySet()));
		if (CollectionUtils.isEmpty(getPartsInfoRespList)){
			logger.info("modelId:{},picNum集合:{}未获取到配件信息", modelId, Jackson.mobile().writeValueAsString(picNumMap.keySet()));
			return Collections.emptyList();
		}
		Map<String, List<GetPartsInfoResp>> partMap = getPartsInfoRespList.stream().collect(Collectors.groupingBy(GetPartsInfoResp::getPicNum));
		picNumMap.forEach((picNum, picNumList) ->{
			List<GetPartsInfoResp> picNumPartsInfoRespList = partMap.get(picNum);
			if (CollectionUtils.isNotEmpty(picNumPartsInfoRespList)){
				//以picName维度再次处理
				Map<String, List<GetPicInfoResp>> picNameMap = picNumList.stream().collect(Collectors.groupingBy(GetPicInfoResp::getPicName));
				picNameMap.forEach((picName, picNameList) ->{
					GetPicInfoResp getPicInfoResp = picNameList.get(0);
					//筛选出该picName下的配件及序号
					List<GetPartsInfoResp> picNamePartsInfoRespList= picNumPartsInfoRespList.stream().filter(p -> picName.equals(p.getPicName())).collect(
							Collectors.toList());
					CarModelPicMarkDto carModelPicMarkDto = new CarModelPicMarkDto();
					carModelPicMarkDto.setModelId(modelId);
					carModelPicMarkDto.setPicNum(getPicInfoResp.getPicNum());
					carModelPicMarkDto.setPicName(getPicInfoResp.getPicName());
					carModelPicMarkDto.setOptionalPicSequences(picNamePartsInfoRespList.stream().map(GetPartsInfoResp::getPicSequence).collect(
							Collectors.toList()));
					CarModelPicMarkEntity carModelPicMarkEntity = this.getByCarModelPicMarkEntity(modelId, getPicInfoResp);
					carModelPicMarkDto.setRawPicHeight(carModelPicMarkEntity.getRawPicHeight());
					carModelPicMarkDto.setRawPicWidth(carModelPicMarkEntity.getRawPicWidth());
					carModelPicMarkDto.setMarkSet(carModelPicMarkEntity.getMarkSet());
					carModelPicMarkDto.setHasMarked(carModelPicMarkEntity.getHasMarked());
					carModelPicMarkDto.setPicLocalPath(ImageUtil.enCodeUrl(SuperEPCconstant.EPC_IMG_HOST + getPicInfoResp.getPicPath()));
					if (StringUtils.isNotBlank(carModelPicMarkEntity.getMarkSet())) {
						carModelPicMarkDto.setCarModelPicMarkItemList(
								Jackson.mobile().readValue(carModelPicMarkEntity.getMarkSet(), new TypeReference<List<CarModelPicMarkItem>>() {
								}));
					}
					carModelPicMarkDtoList.add(carModelPicMarkDto);
				});
			}
		});
		return carModelPicMarkDtoList;
	}

	private CarModelPicMarkEntity getByCarModelPicMarkEntity(Integer modelId, GetPicInfoResp getPicInfoResp){
		CarModelPicMarkEntity    carModelPicMarkEntityParam = new CarModelPicMarkEntity();
		carModelPicMarkEntityParam.setModelId(modelId);
		carModelPicMarkEntityParam.setIsValid(1);
		carModelPicMarkEntityParam.setPicName(getPicInfoResp.getPicName());
		carModelPicMarkEntityParam.setPicNum(getPicInfoResp.getPicNum());
		List<CarModelPicMarkEntity> carModelPicMarkEntityList = carModelPicMarkRepo.select(carModelPicMarkEntityParam);
		if (CollectionUtils.isNotEmpty(carModelPicMarkEntityList)){
			return carModelPicMarkEntityList.get(0);
		}else {//不存在新建
			CarModelPicMarkEntity carModelPicMarkEntity = new CarModelPicMarkEntity();
			carModelPicMarkEntity.setModelId(modelId);
			carModelPicMarkEntity.setPicNum(getPicInfoResp.getPicNum());
			carModelPicMarkEntity.setPicName(getPicInfoResp.getPicName());
			carModelPicMarkEntity.setHasMarked(0);
			carModelPicMarkEntity.setIsValid(1);
			carModelPicMarkEntity.setPicLocalPath(getPicInfoResp.getPicPath());
			carModelPicMarkRepo.insert(carModelPicMarkEntity);
			logger.info("Successfully insert model_pic_mark with modelId={}, picNum={}, picName={}", modelId,
					getPicInfoResp.getPicNum(), getPicInfoResp.getPicName());
			return carModelPicMarkEntity;
		}
	}

}

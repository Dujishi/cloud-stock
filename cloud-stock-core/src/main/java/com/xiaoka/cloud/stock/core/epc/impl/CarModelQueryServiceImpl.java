package com.xiaoka.cloud.stock.core.epc.impl;

import com.xiaoka.cloud.stock.core.epc.CarModelQueryService;
import com.xiaoka.cloud.stock.core.epc.repo.*;
import com.xiaoka.cloud.stock.core.epc.repo.entity.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/28 21:02
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CarModelQueryServiceImpl implements CarModelQueryService {

	@Resource
	private CarModelCategoryRepo carModelCategoryRepo;
	@Resource
	private CarModelPicRepo carModelPicRepo;
	@Resource
	private CarModelPartRepo carModelPartRepo;
	@Resource
	private CarModelRepo carModelRepo;
	@Resource
	private CarModelVinRepo carModelVinRepo;
	@Resource
	private CarModelConfigRepo carModelConfigRepo;

	@Override
	public List<CarModelVinEntity> getCarModelByVin(String vin) {
		CarModelVinEntity param = new CarModelVinEntity();
		param.setIsValid(1);
		param.setVin(vin);
		return carModelVinRepo.select(param);
	}

	@Override
	public List<CarModelEntity> getCarModelListByIds(List<Integer> modelIdList) {
		return carModelRepo.selectByIds(modelIdList);
	}

	@Override
	public List<CarModelConfigEntity> getCarModelConfigListByIds(List<Integer> modelIdList) {
		if (CollectionUtils.isEmpty(modelIdList)){
			return null;
		}
		return carModelConfigRepo.selectByModelIdsForSlave(modelIdList);
	}

	@Override
	public List<CarModelCategoryEntity> getCarModelCategoryEntityList(Integer carModelId, Integer categoryId, String categoryName, Integer type) {
		CarModelCategoryEntity param = new CarModelCategoryEntity();
		param.setModelId(carModelId);
		if (Objects.nonNull(categoryId)) {
			param.setCategoryId(categoryId);
		}
		if (StringUtils.isNotBlank(categoryName) && Objects.equals(0, type)) {
			param.setAssemblyName(categoryName);
		}
		if (Objects.nonNull(type)) {
			param.setType(type);
		}
		param.setIsValid(1);
		return carModelCategoryRepo.select(param);
	}

	@Override
	public List<CarModelPicEntity> getCarModelPicListByModelIdAndSubAssemblyName(Integer carModelId, Integer type, List<String> subAssemblyIdList) {
		return carModelPicRepo.selectByModelIdAndSubAssemblyName(carModelId, subAssemblyIdList, type);
	}

	@Override
	public List<CarModelPartEntity> getCarPartByCarIdAndPartId(Integer modelId, List<Integer> partIdList) {
		return carModelPartRepo.getCarPartByCarIdAndPartId(modelId, partIdList);
	}

	@Override
	public List<CarModelPartEntity> getCarModelPartEntityList(Integer carModelId, String picNum, String picName, String partCode) {
		CarModelPartEntity param = new CarModelPartEntity();
		param.setModelId(carModelId);
		if (StringUtils.isNotBlank(picNum)) {
			param.setPicNum(picNum);
		}
		if (StringUtils.isNotBlank(partCode)) {
			param.setPartCode(partCode);
		}
		if (StringUtils.isNotBlank(picName)){
			param.setPartName(picName);
		}
		param.setIsValid(1);
		return carModelPartRepo.select(param);
	}
}

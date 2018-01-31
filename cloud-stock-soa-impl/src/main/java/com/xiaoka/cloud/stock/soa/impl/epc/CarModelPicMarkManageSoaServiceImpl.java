package com.xiaoka.cloud.stock.soa.impl.epc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.CarBrandRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelCategoryRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.CarSeriesRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarBrandEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelCategoryEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarSeriesEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.epc.CarModelPicMarkService;
import com.xiaoka.cloud.stock.soa.api.epc.CarModelPicMarkManageSoaService;
import com.xiaoka.cloud.stock.soa.api.epc.dto.*;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by sabo on 27/11/2017.
 */
@Service("carModelPicMarkManageSoaService")
public class CarModelPicMarkManageSoaServiceImpl implements CarModelPicMarkManageSoaService {

	@Resource
	CarModelPicMarkService carModelPicMarkService;

	@Resource
	CarModelRepo  carModelRepo;
	@Resource
	CarBrandRepo  carBrandRepo;
	@Resource
	CarSeriesRepo carSeriesRepo;
	@Resource
	CarModelCategoryRepo carModelCategoryRepo;

	@Override
	public List<CarModelPicMarkDto> getCarModelPicMarksByModelId(Integer pageNumber, Integer modelId) {
		List<com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto> carModelPicMarkDtoList = carModelPicMarkService
				.getCarModelPicMarksByModelId(pageNumber, modelId);
		return BeanUtils.transformList(CarModelPicMarkDto.class, carModelPicMarkDtoList);
	}

	@Override
	public List<CarModelPicMarkDto> getCarModelSubAssemblyPicMarksByModelId(Integer modelId, String assemblyName,String subAssemblyName) {
		List<com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto> carModelPicMarkDtoList = carModelPicMarkService
				.getCarModelSubAssemblyPicMarksByModelId(modelId, assemblyName, subAssemblyName);
		return BeanUtils.transformList(CarModelPicMarkDto.class, carModelPicMarkDtoList);
	}

	@Override
	public void countDistinctModelId() {
		carModelRepo.countDistinctModelId();
	}

	@Override
	public int updateCarModelPicMark(CarModelPicMarkDto carModelPicMarkDto) {
		return carModelPicMarkService.updateCarModelPicMark(BeanUtils.transform(com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto.class, carModelPicMarkDto));
	}

	@Override
	public List<CarBrandDto> queryCarBrands() {
		List<CarBrandEntity> carBrandEntities = carBrandRepo.selectAllBrands();
		return BeanUtils.transformList(CarBrandDto.class, carBrandEntities);
	}

	@Override
	public List<CarSeriesDto> queryCarSeriesByBrand(Integer brandId) {
		if (null == brandId) {
			return Collections.emptyList();
		}

		List<CarSeriesEntity> carSeriesEntities = carSeriesRepo.selectAllCarSeries();
		if (CollectionUtils.isNotEmpty(carSeriesEntities)) {
			List<CarSeriesEntity> filterList = carSeriesEntities.stream().filter(p -> p.getBrandId().equals(brandId)).collect(Collectors.toList());
			return BeanUtils.transformList(CarSeriesDto.class, filterList);
		}
		return Collections.emptyList();
	}

	@Override
	public List<CarModelDto> queryCarModelsBySeries(Integer seriesId) {
		if (null == seriesId) {
			return Collections.emptyList();
		}

		List<CarModelEntity> carModelEntities = carModelRepo.selectListBySeries(seriesId);
		List<CarModelEntity> resultList = Lists.newArrayList();
		if (CollectionUtils.isNotEmpty(carModelEntities)){
			Map<Integer, List<CarModelEntity>> carModelMap = carModelEntities.stream().collect(Collectors.groupingBy(CarModelEntity::getModelId));
			//相同车型的只有一条数据即可
			carModelMap.forEach((k , v) -> resultList.add(v.get(0)));
		}
		return BeanUtils.transformList(CarModelDto.class, resultList);
	}

	@Override
	public List<CarAssemblyDto> queryCarModelCategory(Integer modelId) {
		if (null == modelId){
			return Collections.emptyList();
		}
		CarModelCategoryEntity param = new CarModelCategoryEntity();
		param.setIsValid(1);
		param.setModelId(modelId);
		param.setType(0);//原厂分类
		List<CarModelCategoryEntity> categoryEntityList = carModelCategoryRepo.select(param);
		if (CollectionUtils.isNotEmpty(categoryEntityList)){
			List<CarAssemblyDto> carAssemblyDtoList = Lists.newArrayList();
			Map<String, List<CarModelCategoryEntity>> map = categoryEntityList.stream().collect(
					Collectors.groupingBy(CarModelCategoryEntity::getAssemblyName));
			map.forEach((k ,v) -> {
				CarAssemblyDto carAssemblyDto = new CarAssemblyDto();
				carAssemblyDto.setAssemblyName(k);
				carAssemblyDto.setSubAssemblyDtoList(BeanUtils.transformList(CarSubAssemblyDto.class, v));
				carAssemblyDtoList.add(carAssemblyDto);
			});
			return carAssemblyDtoList;
		}
		return Collections.emptyList();
	}
}

package com.xiaoka.cloud.stock.service.region.impl;

import com.xiaoka.cloud.stock.core.region.CarCityDistrictRepo;
import com.xiaoka.cloud.stock.core.region.CarCityRepo;
import com.xiaoka.cloud.stock.core.region.CarProvinceRepo;
import com.xiaoka.cloud.stock.core.region.entity.CarCityDistrictEntity;
import com.xiaoka.cloud.stock.core.region.entity.CarCityEntity;
import com.xiaoka.cloud.stock.core.region.entity.CarProvinceEntity;
import com.xiaoka.cloud.stock.service.region.RegionService;
import com.xiaoka.freework.help.api.ApiException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegionServiceImpl implements RegionService{

	@Resource
	CarProvinceRepo carProvinceRepo;

	@Resource
	CarCityRepo carCityRepo;

	@Resource
	CarCityDistrictRepo carCityDistrictRepo;

	@Override
	public List<CarProvinceEntity> getProvinceList(String provinceName) {
		CarProvinceEntity carProvinceEntity = new CarProvinceEntity();
		carProvinceEntity.setName(provinceName);
		return carProvinceRepo.select(carProvinceEntity);
	}

	@Override
	public List<CarCityEntity> getCityListByProvName(String provinceName, String cityName) {
		List<CarProvinceEntity> carProvinceEntityList = getProvinceList(provinceName);
		if(carProvinceEntityList==null || carProvinceEntityList.size() != 1){
			throw new ApiException("", provinceName + "省不存在");
		}
		CarCityEntity carCityEntity = new CarCityEntity();
		carCityEntity.setProvince(carProvinceEntityList.get(0).getId());
		carCityEntity.setName(cityName);
		return carCityRepo.select(carCityEntity);
	}

	@Override
	public List<CarCityDistrictEntity> getCityDistrictListByProvAndCityName(String provinceName, String cityName) {
		List<CarCityEntity> carCityEntityList = getCityListByProvName(provinceName, cityName);
		if (carCityEntityList == null || carCityEntityList.size() != 1) {
			throw new ApiException("", cityName + "市不存在");
		}
		CarCityDistrictEntity carCityDistrictEntity = new CarCityDistrictEntity();
		carCityDistrictEntity.setCityId(carCityEntityList.get(0).getId());
		return carCityDistrictRepo.select(carCityDistrictEntity);
	}
}

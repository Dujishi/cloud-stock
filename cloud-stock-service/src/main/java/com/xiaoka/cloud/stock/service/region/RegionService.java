package com.xiaoka.cloud.stock.service.region;

import com.xiaoka.cloud.stock.core.region.entity.CarCityDistrictEntity;
import com.xiaoka.cloud.stock.core.region.entity.CarCityEntity;
import com.xiaoka.cloud.stock.core.region.entity.CarProvinceEntity;

import java.util.List;

public interface RegionService {

	List<CarProvinceEntity> getProvinceList(String provinceName);

	List<CarCityEntity> getCityListByProvName(String provinceName, String cityName);

	List<CarCityDistrictEntity> getCityDistrictListByProvAndCityName(String provinceName, String cityName);
}
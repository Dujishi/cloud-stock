package com.xiaoka.cloud.stock.web.order;

import com.xiaoka.cloud.stock.core.region.entity.CarCityDistrictEntity;
import com.xiaoka.cloud.stock.core.region.entity.CarCityEntity;
import com.xiaoka.cloud.stock.core.region.entity.CarProvinceEntity;
import com.xiaoka.cloud.stock.service.region.RegionService;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class RegionController {

	private Logger logger = LoggerFactory.getLogger(RegionController.class);

	@Resource
	RegionService regionService;

	@RequestMapping(value = "/region/getProvinceList", method = RequestMethod.GET)
	@ResponseBody
	public String getProvinceList() {
		List<CarProvinceEntity> result = regionService.getProvinceList(null);
		return ApiResultWrapper.success(result);
	}

	@RequestMapping(value = "/region/getCityListByProvName", method = RequestMethod.GET)
	@ResponseBody
	public String getCityListByProvName(String provinceName) {
		List<CarCityEntity> result = regionService.getCityListByProvName(provinceName, null);
		return ApiResultWrapper.success(result);
	}

	@RequestMapping(value = "/region/getCityDistrictListByProvAndCityName", method = RequestMethod.GET)
	@ResponseBody
	public String getCityDistrictListByProvAndCityName(String provinceName, String cityName) {
		List<CarCityDistrictEntity> result = regionService.getCityDistrictListByProvAndCityName(provinceName, cityName);
		return ApiResultWrapper.success(result);
	}
}

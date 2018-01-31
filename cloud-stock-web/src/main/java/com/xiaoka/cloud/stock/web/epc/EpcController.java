package com.xiaoka.cloud.stock.web.epc;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.epc.CarModelPicMarkService;
import com.xiaoka.cloud.stock.service.epc.EpcQueryService;
import com.xiaoka.cloud.stock.service.epc.EpcSearchHistoryService;
import com.xiaoka.cloud.stock.service.epc.param.EPCQueryParam;
import com.xiaoka.cloud.stock.service.stock.CloudStockService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/17 13:52
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class EpcController {

	@Resource
	private EpcQueryService epcQueryService;
	@Resource
	private CloudStockService cloudStockService;
	@Resource
	private EpcSearchHistoryService epcSearchHistoryService;
	@Resource
	private CarModelPicMarkService carModelPicMarkService;

	@RequestMapping(value = "/epc/getCategory", method = RequestMethod.GET)
	@ResponseBody
	public String getCategory(@RequestParam(required = false) String vin, @RequestParam(required = false) Integer carModelId) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getCarPartCategoryDto(vin, carModelId, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getPartByNames", method = RequestMethod.POST)
	@ResponseBody
	public String getPartByNames(@RequestBody EPCQueryParam param) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getCarPartInfoDto(param, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getAssembly", method = RequestMethod.GET)
	@ResponseBody
	public String getAssembly(@RequestParam Integer carModelId, @RequestParam(required = false) Integer categoryId, @RequestParam Integer type,
			@RequestParam(required = false) String categoryName, @RequestParam(required = false) String extraParam) {
		return ApiResultWrapper.success(epcQueryService.getCarAssemblyDtoList(carModelId, categoryId, categoryName, extraParam, type));
	}

	@RequestMapping(value = "/epc/getPartPics", method = RequestMethod.GET)
	@ResponseBody
	public String getPartPics(@RequestParam(required = false) Integer carModelId, @RequestParam(required = false) String assemblyId,
			@RequestParam(required = false) String assemblyName, @RequestParam Integer type, @RequestParam(required = false) String extraParam,
			@RequestParam(required = false) String categoryName) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper
				.success(epcQueryService.getCarSubAssemblyDtoList(carModelId, categoryName, assemblyId, assemblyName, extraParam, type, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getPartsInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getPartsInfo(@RequestParam(required = false) Integer carModelId, @RequestParam(required = false) String picNum,
			@RequestParam(required = false) String picName,  @RequestParam(required = false) String extraParam) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		Map<String, Object> result = Maps.newHashMap();
		result.put("partList", epcQueryService.getPartInfoDtoList(carModelId, picNum, picName, extraParam, cloudSupplierUserDto));
		result.put("picMark", carModelPicMarkService.getCarModelPicMark(carModelId, picNum, picName, extraParam, cloudSupplierUserDto));
		return ApiResultWrapper.success(result);
	}

	@RequestMapping(value = "/epc/getPartDetail", method = RequestMethod.GET)
	@ResponseBody
	public String getPartDetail(@RequestParam(required = false) Integer carModelId, @RequestParam(required = false) String partCode,
			@RequestParam String carBrandName) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getPartDetailDto(carModelId, partCode, carBrandName, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getBrandByCodes", method = RequestMethod.POST)
	@ResponseBody
	public String getBrandByCodes(@RequestBody EPCQueryParam param) {
		//查询品牌不需要控制，选择品牌后查询结果时控制：getPartByCodes
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getCarBrandDtoList(param.getPartCodeList(), cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getPartByCodes", method = RequestMethod.POST)
	@ResponseBody
	public String getPartByCodes(@RequestBody EPCQueryParam param) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getPartInfoDtoList(param.getPartCodeList(), param.getBrandName(), cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getPartStock", method = RequestMethod.GET)
	@ResponseBody
	public String getPartStock(@RequestParam(name = "codes",required = false) List<String> codes, HttpServletRequest request) {
		return ApiResultWrapper.success(cloudStockService.searchStockPartsByOEList(codes, SessionUtil.getSupplierId(request)));
	}

	@RequestMapping(value = "/epc/searchHistory", method = RequestMethod.GET)
	@ResponseBody
	public String searchHistory(@RequestParam Integer searchType) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcSearchHistoryService.searchHistory(searchType, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getBrandFirstLetter", method = RequestMethod.GET)
	@ResponseBody
	public String getBrandFirstLetter() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getAllBrandFirstLetter());
	}

	@RequestMapping(value = "/epc/getBrandByFirstLetter", method = RequestMethod.GET)
	@ResponseBody
	public String getBrandByFirstLetter(@RequestParam String firstLetter) {
		return ApiResultWrapper.success(epcQueryService.getBrandByFirstLetter(firstLetter));
	}

	@RequestMapping(value = "/epc/getAuthorisedByFirstLetter", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthorisedByFirstLetter(@RequestParam String firstLetter) {
		AssertUtil.assertTrueWithApiException(StringUtils.isNotBlank(firstLetter), "品牌首字母不能为空");
		Integer userId = SessionUtil.getCurrentUser().getId();
		return ApiResultWrapper.success(epcQueryService.getAuthorisedByFirstLetter(firstLetter, userId));
	}


	@RequestMapping(value = "/epc/getAuthorisedBrand", method = RequestMethod.GET)
	@ResponseBody
	public String getAuthorisedBrand(@RequestParam Integer userId) {
		return ApiResultWrapper.success(epcQueryService.getAuthorisedByFirstLetter(null, userId));
	}

	@RequestMapping(value = "/epc/getAllBrand", method = RequestMethod.GET)
	@ResponseBody
	public String getAllBrand() {
		return ApiResultWrapper.success(epcQueryService.getAllBrand());
	}

	@RequestMapping(value = "/epc/getSeriesByBrand", method = RequestMethod.GET)
	@ResponseBody
	public String getSeriesByBrand(@RequestParam Integer brandId) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getSeriesByBrand(brandId, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getModelYearBySeries", method = RequestMethod.GET)
	@ResponseBody
	public String getModelYearBySeries(@RequestParam Integer brandId, @RequestParam String series) {
		return ApiResultWrapper.success(epcQueryService.getModelYearBySeries(brandId, series));
	}

	@RequestMapping(value = "/epc/getModelBySeries", method = RequestMethod.GET)
	@ResponseBody
	public String getModelBySeries(@RequestParam Integer brandId, @RequestParam String series, @RequestParam(required = false) String modelYear) {
		return ApiResultWrapper.success(epcQueryService.getModelBySeries(brandId, series, modelYear));
	}

	@RequestMapping(value = "/epc/getBrandByModelName", method = RequestMethod.GET)
	@ResponseBody
	public String getBrandByModelName(@RequestParam String modelCondition) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getModelByModelName(modelCondition, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getModelYearByModelName", method = RequestMethod.GET)
	@ResponseBody
	public String getModelYearByModelName(@RequestParam String modelCondition, @RequestParam Integer brandId) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		AssertUtil.assertTrueWithApiException(Objects.nonNull(cloudSupplierUserDto), "请先登录");
		return ApiResultWrapper.success(epcQueryService.getModelYearByModelName(modelCondition, brandId, cloudSupplierUserDto));
	}

	@RequestMapping(value = "/epc/getModelBySearch", method = RequestMethod.GET)
	@ResponseBody
	public String getModelBySearch(@RequestParam String modelCondition, @RequestParam Integer brandId, @RequestParam String modelYear) {
		return ApiResultWrapper.success(epcQueryService.getModelBySearch(modelCondition, brandId, modelYear));
	}


}

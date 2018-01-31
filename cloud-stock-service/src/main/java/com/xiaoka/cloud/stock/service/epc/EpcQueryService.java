package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.service.epc.dto.*;
import com.xiaoka.cloud.stock.service.epc.output.EPCSearchResultDto;
import com.xiaoka.cloud.stock.service.epc.output.PartDetailDto;
import com.xiaoka.cloud.stock.service.epc.param.EPCQueryParam;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

/**
 * 车型查询相关服务
 *
 * @author gancao 2017/11/17 13:57
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface EpcQueryService {

	/**
	 * 通过vin码获取车型和大类
	 *
	 * @param vin
	 * @return
	 * @pa carModelId
	 */
	EPCSearchResultDto getCarPartCategoryDto(String vin, Integer carModelId, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 通过vin码和零件名称查询
	 *
	 * @param param
	 * @return
	 */
	EPCSearchResultDto getCarPartInfoDto(EPCQueryParam param, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 正时分类下的总成
	 *
	 * @param carModelId
	 * @param categoryId
	 * @param type
	 * @return
	 */
	List<PartAssemblyDto> getCarAssemblyDtoList(Integer carModelId, Integer categoryId, String categoryName, String extraParam,Integer type);

	/**
	 * 车型总成下面图片列表信息
	 *
	 * @param carModelId
	 * @param assemblyId
	 * @param assemblyName
	 * @param type
	 * @return
	 */
	List<PartSubAssemblyDto> getCarSubAssemblyDtoList(Integer carModelId, String categoryName, String assemblyId, String assemblyName,
			String extraParam, Integer type, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 获取车型图号下的所有配件
	 *
	 * @param carModelId
	 * @param picNum
	 * @return
	 */
	List<PartInfoDto> getPartInfoDtoList(Integer carModelId, String picNum, String picName, String extraParam, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 配件详情信息(配件信息、适用车型、通用零件)
	 *
	 * @param carModelId
	 * @param partCode
	 * @param carBrandName
	 * @return
	 */
	PartDetailDto getPartDetailDto(Integer carModelId, String partCode, String carBrandName, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 通过配件集合获取车牌品牌集合
	 *
	 * @param partCodeList
	 * @return
	 */
	List<CarBrandDto> getCarBrandDtoList(List<String> partCodeList, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 根据零件号和品牌名称获取配件信息
	 *
	 * @param partCodeList
	 * @param brandName
	 * @return
	 */
	List<PartInfoDto> getPartInfoDtoList(List<String> partCodeList, String brandName, CloudSupplierUserDto cloudSupplierUserDto);

	//**********************************************
	//********** 品牌权限控制及车型查询相关 ***********
	//**********************************************

	/**
	 * 查询品牌首字母
	 *
	 * @return
	 */
	List<CarBrandDto> getBrandFirstLetter(CloudSupplierUserDto cloudSupplierUserDto);

	List<CarBrandDto> getAllBrandFirstLetter();

	/**
	 * 根据首字母查询对应品牌
	 *
	 * @param firstLetter
	 * @return
	 */
	List<CarBrandDto> getBrandByFirstLetter(String firstLetter);

	/**
	 * 根据品牌查车系
	 *
	 * @param brandId
	 * @return
	 */
	List<CarSeriesDto> getSeriesByBrand(Integer brandId, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 根据车系查询车型年份
	 *
	 * @param brandId
	 * @param series
	 * @return
	 */
	List<CarModelWebDto> getModelYearBySeries(Integer brandId, String series);

	/**
	 * 根据车系和车型年份查询车型
	 *
	 * @param brandId
	 * @param series
	 * @param modelYear
	 * @return
	 */
	List<CarModelWebDto> getModelBySeries(Integer brandId, String series, String modelYear);

	/**
	 * 根据车型名称搜索涉及品牌
	 *
	 * @param modelCondition
	 * @return
	 */
	List<CarBrandDto> getBrandByModelName(String modelCondition);

	/**
	 * 根据车型名称查询品牌下的车型年份
	 *
	 * @param modelCondition
	 * @param brandId
	 * @return
	 */
	List<CarModelWebDto> getModelYearByModelName(String modelCondition, Integer brandId, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 根据车型名称、年份、品牌查询车型
	 *
	 * @param modelCondition
	 * @param brandId
	 * @param modelYear
	 * @return
	 */
	List<CarModelWebDto> getModelBySearch(String modelCondition, Integer brandId, String modelYear);

	/**
	 * 根据首字母查询有权限的对应品牌
	 *
	 * @param firstLetter
	 * @param userId
	 * @return
	 */
	List<CarBrandDto> getAuthorisedByFirstLetter(String firstLetter, Integer userId);

	/**
	 * 查询所有品牌
	 *
	 * @return
	 */
	List<CarBrandDto> getAllBrand();

	/**
	 * 根据车型名称模糊查询车型
	 * @param modelCondition
	 * @param cloudSupplierUserDto
	 * @return
	 */
	List<CarBrandDto> getModelByModelName(String modelCondition,CloudSupplierUserDto cloudSupplierUserDto);

}

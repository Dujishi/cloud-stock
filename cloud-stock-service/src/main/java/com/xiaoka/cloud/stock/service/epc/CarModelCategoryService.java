package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.service.epc.dto.PartAssemblyDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartCategoryDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartSubAssemblyDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

/**
 * 车型类目服务(包含总成、分总成)
 *
 * @author gancao 2017/11/18 15:20
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CarModelCategoryService {

	/**
	 * 总成下面所有的图片信息
	 * @param carModelId
	 * @param assemblyId
	 * @param assemblyName
	 * @param type
	 * @return
	 */
	List<PartSubAssemblyDto> getCarSubAssemblyDtoList(Integer carModelId, String assemblyId, String assemblyName, String extraParam, Integer type, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 获取正时分类下的总成
	 * @param carModelId
	 * @param categoryId
	 * @return
	 */
	List<PartAssemblyDto> getCarModelCategory(Integer carModelId, Integer categoryId, String categoryName, String extraParam, Integer type);

	/**
	 * 获取车型的大类
	 * @param carModelId
	 * @return
	 */
	List<PartCategoryDto> getCarModelCategory(Integer carModelId);


}

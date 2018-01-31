package com.xiaoka.cloud.stock.soa.api.epc;

import com.xiaoka.cloud.stock.soa.api.epc.dto.*;

import java.util.List;

/**
 * Created by sabo on 27/11/2017.
 */
public interface CarModelPicMarkManageSoaService {

	List<CarModelPicMarkDto> getCarModelPicMarksByModelId(Integer pageNumber, Integer modelId);

	/**
	 * 原厂分总成名称获取图号
	 * @param modelId
	 * @param subAssemblyName
	 * @param assemblyName
	 * @return
	 */
	List<CarModelPicMarkDto> getCarModelSubAssemblyPicMarksByModelId(Integer modelId, String assemblyName,String subAssemblyName);

	void countDistinctModelId();

	int updateCarModelPicMark(CarModelPicMarkDto carModelPicMarkDto);

	List<CarBrandDto> queryCarBrands();

	List<CarSeriesDto> queryCarSeriesByBrand(Integer brandId);

	List<CarModelDto> queryCarModelsBySeries(Integer seriesId);

	List<CarAssemblyDto> queryCarModelCategory(Integer modelId);



}

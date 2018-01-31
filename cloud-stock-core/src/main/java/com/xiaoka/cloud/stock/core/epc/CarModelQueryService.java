package com.xiaoka.cloud.stock.core.epc;

import com.xiaoka.cloud.stock.core.epc.repo.entity.*;

import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/28 21:01
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CarModelQueryService {

	List<CarModelVinEntity> getCarModelByVin(String vin);

	List<CarModelEntity> getCarModelListByIds(List<Integer> modelIdList);

	List<CarModelConfigEntity> getCarModelConfigListByIds(List<Integer> modelIdList);

	List<CarModelCategoryEntity> getCarModelCategoryEntityList(Integer carModelId, Integer categoryId, String categoryName, Integer type);

	List<CarModelPicEntity> getCarModelPicListByModelIdAndSubAssemblyName(Integer carModelId, Integer type, List<String> subAssemblyIdList);

	List<CarModelPartEntity> getCarPartByCarIdAndPartId(Integer modelId, List<Integer> partIdList);

	List<CarModelPartEntity> getCarModelPartEntityList(Integer carModelId, String picNum, String picName,String partCode);
}

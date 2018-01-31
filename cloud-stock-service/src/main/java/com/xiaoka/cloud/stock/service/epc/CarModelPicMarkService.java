package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPartEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.CarModelPicMarkEntity;
import com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

/**
 * Created by sabo on 27/11/2017.
 */
public interface CarModelPicMarkService {
	List<CarModelPicMarkDto> getCarModelPicMarksByModelId(Integer pageNumber, Integer modelId);

	List<CarModelPicMarkDto> getCarModelSubAssemblyPicMarksByModelId(Integer modelId, String assemblyName, String subAssemblyName);

	void insertByCarModelPartEntityTask(Integer pageNumber, Integer pageSize, Integer modelId);

	CarModelPicMarkEntity insertByCarModelPartEntity(CarModelPartEntity carModelPartEntity);

	Integer updateCarModelPicMark(CarModelPicMarkDto carModelPicMarkDto);

	CarModelPicMarkDto getCarModelPicMark(Integer modelId, String picNum, String picName, String extraParam, CloudSupplierUserDto cloudSupplierUserDto);

	/**
	 * 根据modelId、picNum、picName查询列表数据
	 *
	 * @param carModelPicMarkEntities
	 * @return
	 */
	List<CarModelPicMarkDto> queryCarModelPicMarks(List<CarModelPicMarkEntity> carModelPicMarkEntities);
}

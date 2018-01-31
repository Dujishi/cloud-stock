package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.service.epc.dto.CarModelDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartSuitCarDto;

import java.util.List;
import java.util.Map;

/**
 * CarModelService
 *
 * @author suqin
 */
public interface CarModelService {

	/**
	 * 通过vin码查询适配车型
	 * @param vin
	 * @return
	 */
	List<CarModelDto> getCarModelListByVin(String vin);

	/**
	 * 通过零件号查询适用车型
	 * @param partCode
	 * @return
	 */
	List<PartSuitCarDto> getSuitCarModelListByPartCode(String partCode);

	/**
	 * 通过零件号集合查询适用车型
	 * @param partCodeList
	 * @return
	 */
	Map<String, List<PartSuitCarDto>> getSuitCarModelListByPartCodeList(List<String> partCodeList);

}

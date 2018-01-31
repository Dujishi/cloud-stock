package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.service.epc.dto.CarModelPicMarkDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartInfoDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartSubAssemblyDto;
import com.xiaoka.cloud.stock.service.epc.output.EPCSearchResultDto;
import com.xiaoka.cloud.stock.service.epc.output.PartDetailDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

/**
 * 零零汽 VIN码查询相关服务
 *
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ZeroEpcService {


	/**
	 * 根据VIN码查询VIN码信息
	 *
	 * @param vin
	 * @return
	 */
	EPCSearchResultDto searchZeroVinDataByVin(String vin, CloudSupplierUserDto userDto);

	List<PartSubAssemblyDto> getPartSubAssemblyDtoByZeroGroup(String extraParam, CloudSupplierUserDto userDto);

	List<PartInfoDto> getPartInfoDtoListByZeroSubGroup(String extraParam, CloudSupplierUserDto userDto);

	CarModelPicMarkDto getCarModelPicMarkDto(String extraParam, CloudSupplierUserDto userDto);

	PartDetailDto getPartDetailDtoByZero(String partCode, String carBrandName, CloudSupplierUserDto userDto);

}

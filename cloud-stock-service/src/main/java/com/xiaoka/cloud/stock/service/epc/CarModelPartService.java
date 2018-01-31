package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.service.epc.dto.PartInfoDto;

import java.util.List;

/**
 * 车型配件服务
 *
 * @author gancao
 */
public interface CarModelPartService {

	/**
	 * 根据配件名称和车型取标准配件
	 * @param carModelId
	 * @param partNameList
	 * @return
	 */
	List<PartInfoDto> getPartInfoDtoByNameList(Integer carModelId, List<String> partNameList);

	/**
	 * 根据车型、图号或者零件号获取车型配件列表
	 * @param carModelId
	 * @param picNum
	 * @return
	 */
	List<PartInfoDto> getPartInfoDtoList(Integer carModelId, String picNum, String picName, String partCode);

	/**
	 * 根据零件号、品牌名、厂商名获取4S店报价
	 * @param partCode
	 * @param brandName
	 * @param markName
	 * @return
	 */
	String getPartPrice(String partCode, String brandName, String markName);

	/**
	 * 根据零件号或品牌名称及是否原厂判断查询
	 * @param partCode
	 * @param brandName
	 * @return
	 */
	List<PartInfoDto> getPartReplaceList(String partCode, String brandName);

}

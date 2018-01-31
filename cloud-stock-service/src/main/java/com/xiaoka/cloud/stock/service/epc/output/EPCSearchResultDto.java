package com.xiaoka.cloud.stock.service.epc.output;

import com.xiaoka.cloud.stock.service.epc.dto.CarModelDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartCategoryDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartInfoDto;

import java.io.Serializable;
import java.util.List;

/**
 * EPC基础查询结果
 *
 * @author gancao 2017/11/17 14:03
 * @see [相关类/方法]
 * @since [版本号]
 */
public class EPCSearchResultDto extends  ExtraParamDto implements Serializable {

	private static final long serialVersionUID = -4568191879540781628L;

	/**
	 * 匹配的车型
	 */
	private List<CarModelDto> carModelList;

	/**
	 * 配件标准大类
	 */
	private List<PartCategoryDto> partCategoryList;

	/**
	 * 查询的配件集合
	 */
	private List<PartInfoDto> partList;

	public List<CarModelDto> getCarModelList() {
		return carModelList;
	}

	public void setCarModelList(List<CarModelDto> carModelList) {
		this.carModelList = carModelList;
	}

	public List<PartCategoryDto> getPartCategoryList() {
		return partCategoryList;
	}

	public void setPartCategoryList(List<PartCategoryDto> partCategoryList) {
		this.partCategoryList = partCategoryList;
	}

	public List<PartInfoDto> getPartList() {
		return partList;
	}

	public void setPartList(List<PartInfoDto> partList) {
		this.partList = partList;
	}
}

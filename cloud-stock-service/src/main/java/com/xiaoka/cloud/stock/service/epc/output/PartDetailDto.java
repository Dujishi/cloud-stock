package com.xiaoka.cloud.stock.service.epc.output;

import com.xiaoka.cloud.stock.service.epc.dto.PartInfoDto;
import com.xiaoka.cloud.stock.service.epc.dto.PartSuitCarDto;

import java.io.Serializable;
import java.util.List;

/**
 * 零件详情实体输出对象
 *
 * @author gancao 2017/11/13 11:27
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartDetailDto implements Serializable {

	private static final long serialVersionUID = -8305428395488368783L;

	//配件集合
	private List<PartInfoDto> partInfoList;
	//适用车型
	private List<PartSuitCarDto> suitCarList;
	//通用零件
	private List<PartInfoDto> replacePartList;

	public List<PartInfoDto> getPartInfoList() {
		return partInfoList;
	}

	public void setPartInfoList(List<PartInfoDto> partInfoList) {
		this.partInfoList = partInfoList;
	}

	public List<PartSuitCarDto> getSuitCarList() {
		return suitCarList;
	}

	public void setSuitCarList(List<PartSuitCarDto> suitCarList) {
		this.suitCarList = suitCarList;
	}

	public List<PartInfoDto> getReplacePartList() {
		return replacePartList;
	}

	public void setReplacePartList(List<PartInfoDto> replacePartList) {
		this.replacePartList = replacePartList;
	}
}

package com.xiaoka.cloud.stock.service.crawl.linglingqi.service;

import com.xiaoka.cloud.stock.service.crawl.linglingqi.dto.*;

import java.util.List;

/**
 * @author zhouze
 * @date 2017/12/14
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface ZeroDataCollectService {

	/**
	 * 保存品牌
	 *
	 * @param list
	 */
	void saveBrands(List<ZeroBrandDto> list);


	/**
	 * 保存车型
	 *
	 * @param list
	 */
	void saveCarModels(List<ZeroCarModelDto> list);

	/**
	 * 保存车型主组列表及零件组列表
	 *
	 * @param list
	 */
	void saveGroups(List<ZeroGroupDto> list);

	/**
	 * 保存零件组列表
	 *
	 * @param list
	 */
	void saveSubGroups(List<ZeroSubGroupDto> list);

	/**
	 * 保存零件组图片列表
	 *
	 * @param list
	 */
	void saveSubGroupImgs(List<ZeroSubGroupImgDto> list);

	/**
	 * 保存零件组-零件关系列表
	 *
	 * @param list
	 */
	void saveSubGroupParts(List<ZeroSubGroupPartsDto> list);

	/**
	 * 保存零件信息表
	 *
	 * @param list
	 */
	void savePartInfos(List<ZeroPartInfoDto> list);

	/**
	 * 保存配件价格
	 *
	 * @param list
	 */
	void savePartPrices(List<ZeroPartPriceDto> list);

	/**
	 * 保存适配车型数据列表
	 *
	 * @param list
	 */
	void saveAdapterCarModels(List<ZeroAdapterCarModelDto> list);
}

package com.xiaoka.cloud.stock.service.stock;

import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.cloud.stock.service.stock.vo.CloudStockPageVo;

import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CloudStockService {


	/**
	 * 根据配件码和当前登录配件家id查询该配件商组织架构所有库存数据
	 *
	 * @param codes  配件编码
	 * @param shopId 内部商家id
	 * @return
	 */
	List<CloudStockPageVo> searchStockPartsByOEList(List<String> codes, Integer shopId);

	/**
	 * 查询库存服务
	 *
	 * @param codes   零件码列表
	 * @param shopIds 商家id列表
	 * @return
	 */
	List<CloudPartEntity> queryStockPartInfoList(List<String> codes, List<Integer> shopIds);

}

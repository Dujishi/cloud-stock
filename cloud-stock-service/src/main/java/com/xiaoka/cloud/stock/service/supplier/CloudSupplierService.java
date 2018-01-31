package com.xiaoka.cloud.stock.service.supplier;

import java.util.List;

/**
 * 云仓供应商数据业务层
 *
 * @author gancao 2017/11/11 15:34
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CloudSupplierService {

	/**
	 * 通过appId获取erp签名key
	 * @param appId
	 * @return
	 */
	String getKeyByAppId(String appId);

	/**
	 * 通过appId和外部供应商id来获取内部供应商id
	 * @param appId
	 * @param outSupplierId
	 * @return
	 */
	Integer getSupplierIdByAppIdAndOutSupplierId(String appId, String outSupplierId);

	/**
	 * 判断IP是否有效
	 * @param ip
	 * @return
	 */
	boolean isValidIp(String ip);

	/**
	 * 根据子供应商id获取该子供应商的总公司下所有的子供应商
	 * @param supplierId
	 * @return
	 */
	List<Integer> getCompanyAllSubSupplierIdList(Integer supplierId);
}

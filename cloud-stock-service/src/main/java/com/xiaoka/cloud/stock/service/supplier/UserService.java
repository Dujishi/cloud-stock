package com.xiaoka.cloud.stock.service.supplier;

import com.xiaoka.cloud.stock.service.server.dto.ErpCollectDataDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.dto.ErpCollectStrategyDto;

/**
 * Do something
 *
 * @author gancao 2017/11/15 16:02
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface UserService {

	/**
	 * 根据手机号获取用户,如果没有就向用户中心新增
	 *
	 * @param phone
	 * @return
	 */
	Integer getUserIdAndCreateIfNull(String phone);

	/**
	 * 修改密码
	 *
	 * @param phone
	 * @param password
	 * @param code
	 */
	boolean modifyPassword(String phone, String password, String code);

	/**
	 * 获取验证码
	 *
	 * @param phone
	 * @return
	 */
	String getCode(String phone);

	/**
	 * 登录
	 *
	 * @param userPhone
	 * @param password
	 * @return
	 */
	CloudSupplierUserDto doLogin(String userPhone, String password);

	/**
	 * 根据公司id获取相应ERP采集策略标识
	 *
	 * @param companyId
	 */
	ErpCollectStrategyDto getCompanyStrategy(Integer companyId);

	/**
	 * 是否ERP授权
	 *
	 * @param phone
	 * @return
	 */
	boolean isErpAuthorized(String phone);
}

package com.xiaoka.cloud.stock.service.supplier;

import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.dto.SimpleSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.param.SupplierUserParam;

import java.util.List;

/**
 * 供应商用户服务
 *
 * @author gancao 2017/11/14 10:47
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CloudSupplierUserService {

	/**
	 * 根据id判断用户是否是超级管理员
	 * @param id
	 * @return
	 */
	boolean isSuperAdmin(Integer id);

	/**
	 * 通过手机号获取登录供应商用户信息
	 *
	 * @param phone
	 * @return
	 */
	CloudSupplierUserDto getCloudSupplierUserDtoByPhone(String phone);

	/**
	 * 校验手机号是否有效
	 *
	 * @param phone
	 * @return
	 */
	boolean checkPhone(String phone);

	/**
	 *@param supplierId
	 * @return
	 */
	List<SimpleSupplierUserDto> getCloudSupplierUserList(Integer supplierId, CloudSupplierUserDto userDto);

	/**
	 * 保存或者修改供应商登录帐号
	 * @param param
	 */
	void saveOrUpdateCloudSupplierUser(SupplierUserParam param, CloudSupplierUserDto userDto);

	/**
	 * 删除员工
	 * @param id
	 */
	void deleteCloudSupplierUser(Integer id, CloudSupplierUserDto userDto);

	/**
	 * 设置用户品牌权限
	 * @param userId
	 * @param brandIds
	 * @param operator
	 */
	void setBrandAuth(Integer userId, List<Integer> brandIds, String operator);
}

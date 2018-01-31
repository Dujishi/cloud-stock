package com.xiaoka.cloud.stock.soa.api.supplier;

import com.xiaoka.cloud.stock.soa.api.supplier.dto.*;

/**
 * 云仓SOA接口
 *
 * @author gancao 2017/11/16 13:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CloudSupplierDataSoaService {

	/**
	 * 操作供应商总公司
	 * @param dto
	 */
	void saveSupplierCompany(CloudSupplierCompanyDto dto);

	/**
	 * 操作子供应商
	 * @param dto
	 */
	void saveSupplierCompanySub(CloudSupplierCompanySubDto dto);

	/**
	 * 操作供应商访问权限ip
	 * @param dto
	 */
	void saveSupplierAuthorizeIp(CloudSupplierAuthorizeIpDto dto);

	/**
	 * 操作供应商用户
	 * @param dto
	 */
	void saveSupplierUser(CloudSupplierUserDto dto);

	/**
	 * 操作ERP厂商
	 * @param dto
	 */
	void saveErpFirm(CloudErpFirmDto dto);

	/**
	 * 操作ERP和供应商关联
	 * @param dto
	 */
	void saveErpSupplierMapper(CloudErpSupplierMapperDto dto);
}

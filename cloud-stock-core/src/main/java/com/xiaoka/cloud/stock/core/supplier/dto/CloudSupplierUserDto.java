package com.xiaoka.cloud.stock.core.supplier.dto;

import java.io.Serializable;

/**
 * 云仓登录供应商用户信息
 *
 * @author gancao 2017/11/11 15:37
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CloudSupplierUserDto implements Serializable {

	private static final long serialVersionUID = -7039231655498354276L;
	//用户
	private Integer userId;
	//手机号
	private String phone;
	//供应商总公司id
	private Integer companyId;
	//供应商总公司名称
	private String companyName;
	//公司类型
	private int companyType;
	//供应商id
	private Integer supplierId;
	//供应商名称
	private String supplierName;
	//用户名
	private String name;
	//用户角色id
	private Integer roleId;
	//用户角色名称
	private String roleName;
	//供应商省份
	private String supplierProv;
	//供应商城市
	private String supplierCity;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public int getCompanyType() {
		return companyType;
	}

	public void setCompanyType(int companyType) {
		this.companyType = companyType;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSupplierProv() {
		return supplierProv;
	}

	public void setSupplierProv(String supplierProv) {
		this.supplierProv = supplierProv;
	}

	public String getSupplierCity() {
		return supplierCity;
	}

	public void setSupplierCity(String supplierCity) {
		this.supplierCity = supplierCity;
	}
}

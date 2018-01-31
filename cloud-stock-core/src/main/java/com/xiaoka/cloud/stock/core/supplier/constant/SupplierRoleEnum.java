package com.xiaoka.cloud.stock.core.supplier.constant;

import java.util.Objects;

/**
 * 供应商角色枚举
 *
 * @author gancao 2017/11/13 17:31
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum SupplierRoleEnum {
	超级管理员(1, "超级管理员"),
	管理员(2, "管理员"),
	员工(3, "员工");
	//角色id
	private Integer roleId;
	//角色名称
	private String roleName;

	SupplierRoleEnum(Integer roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public static String getRoleNameById(Integer id){
		if (Objects.nonNull(id)){
			for (SupplierRoleEnum supplierRoleEnum : values()) {
				if (Objects.equals(supplierRoleEnum.getRoleId(), id)){
					return supplierRoleEnum.getRoleName();
				}
			}
		}
		return null;
	}
}

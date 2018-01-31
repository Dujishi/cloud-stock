package com.xiaoka.cloud.stock.service.supplier.dto;

import java.io.Serializable;

public class SimpleSupplierUserDto implements Serializable{

    private static final long serialVersionUID = -8316397656074951630L;

    private Integer id;

    private Integer supplierId;

    private String phone;

    private String name;

    private String roleName;

    private Integer roleId;

    //是否当前登录帐号
    private Integer isloginUser = 0;
    //是否可以编辑
    private Integer canEdit = 0;
    //是否可以删除
    private Integer canDelete = 0;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getIsloginUser() {
        return isloginUser;
    }

    public void setIsloginUser(Integer isloginUser) {
        this.isloginUser = isloginUser;
    }

    public Integer getCanEdit() {
        return canEdit;
    }

    public void setCanEdit(Integer canEdit) {
        this.canEdit = canEdit;
    }

    public Integer getCanDelete() {
        return canDelete;
    }

    public void setCanDelete(Integer canDelete) {
        this.canDelete = canDelete;
    }
}
package com.xiaoka.cloud.stock.service.supplier.param;

import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

public class SupplierUserParam implements Serializable{

    private static final long serialVersionUID = -1336525506493746459L;

    private Integer id;

    private Integer supplierId;

    @NotBlank(message = "手机号不能为空")
    private String phone;

    @NotBlank(message = "用户名不能为空")
    private String name;

    @NotNull(message = "角色不能为空")
    private Integer roleId;

    private List<Integer> brandIds;

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

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public List<Integer> getBrandIds() {
        return brandIds;
    }

    public void setBrandIds(List<Integer> brandIds) {
        this.brandIds = brandIds;
    }
}
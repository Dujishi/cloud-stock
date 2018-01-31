package com.xiaoka.cloud.stock.soa.api.supplier.dto;

import java.io.Serializable;
import java.util.Date;

public class CloudErpSupplierMapperDto implements Serializable{

    private static final long serialVersionUID = 3933486141257326488L;

    private Integer id;

    private Integer erpId;

    private Integer supplierId;

    private String outSupplierId;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getErpId() {
        return erpId;
    }

    public void setErpId(Integer erpId) {
        this.erpId = erpId;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    public String getOutSupplierId() {
        return outSupplierId;
    }

    public void setOutSupplierId(String outSupplierId) {
        this.outSupplierId = outSupplierId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
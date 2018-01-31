package com.xiaoka.cloud.stock.soa.api.supplier.dto;

import java.io.Serializable;
import java.util.Date;

public class CloudErpFirmDto implements Serializable{

    private static final long serialVersionUID = 2016012971988745762L;

    private Integer id;

    private String appId;

    private String key;

    private String erpFirmName;

    private Integer isDelete;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getErpFirmName() {
        return erpFirmName;
    }

    public void setErpFirmName(String erpFirmName) {
        this.erpFirmName = erpFirmName;
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
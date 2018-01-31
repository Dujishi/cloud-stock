package com.xiaoka.cloud.stock.core.crawl.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for car choose
 * ZeroCarModelChooseEntity
 *
 * @author gancao
 */
public class ZeroCarModelChooseEntity implements Serializable{

    private static final long serialVersionUID = 6666359592107929058L;
    private Integer id;
    private String auth;
    private String uri;
    private String phone;
    private Integer successStatus;
    private Integer amount;
    private String brand;
    private String carModel;
    private String market;
    private String year;
    private String engine;
    private String gearBox;
    private String lastGroupName;
    private String lastSubGroupName;
    private Integer isValid;
    private Date createTime;
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAuth() {
        return auth;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getMarket() {
        return market;
    }

    public void setMarket(String market) {
        this.market = market;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getGearBox() {
        return gearBox;
    }

    public void setGearBox(String gearBox) {
        this.gearBox = gearBox;
    }

    public Integer getIsValid() {
        return isValid;
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
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

    public Integer getSuccessStatus() {
        return successStatus;
    }

    public void setSuccessStatus(Integer successStatus) {
        this.successStatus = successStatus;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getLastGroupName() {
        return lastGroupName;
    }

    public void setLastGroupName(String lastGroupName) {
        this.lastGroupName = lastGroupName;
    }

    public String getLastSubGroupName() {
        return lastSubGroupName;
    }

    public void setLastSubGroupName(String lastSubGroupName) {
        this.lastSubGroupName = lastSubGroupName;
    }
}
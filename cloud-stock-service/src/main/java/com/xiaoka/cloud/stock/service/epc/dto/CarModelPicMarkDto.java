package com.xiaoka.cloud.stock.service.epc.dto;


import java.util.Date;
import java.util.List;

/**
 * for car brand
 * CarModelPicMarkEntity
 *
 * @author suqin
 */
public class CarModelPicMarkDto {

    private Integer modelId;
    private String picNum;
    private String picName;
    private String picLocalPath;
    private Integer rawPicHeight;
    private Integer rawPicWidth;
    private String markSet;
    private Integer hasMarked;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private Integer isValid;
    private List<String> optionalPicSequences;
    private List<CarModelPicMarkItem> carModelPicMarkItemList;


    public Integer getModelId() {
        return this.modelId;
    }

    public String getPicNum() {
        return this.picNum;
    }

    public String getPicName() {
        return this.picName;
    }

    public String getPicLocalPath() {
        return this.picLocalPath;
    }

    public Integer getRawPicHeight() {
        return this.rawPicHeight;
    }

    public Integer getRawPicWidth() {
        return this.rawPicWidth;
    }


    public String getMarkSet() {
        return this.markSet;
    }

    public Integer getHasMarked() {
        return this.hasMarked;
    }

    public Date getCreateTime() {
        return this.createTime;
    }

    public String getCreateBy() {
        return this.createBy;
    }

    public Date getUpdateTime() {
        return this.updateTime;
    }

    public String getUpdateBy() {
        return this.updateBy;
    }

    public Integer getIsValid() {
        return this.isValid;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public void setPicNum(String picNum) {
        this.picNum = picNum == null ? null : picNum.trim();
    }

    public void setPicName(String picName) {
        this.picName = picName == null ? null : picName.trim();
    }

    public void setPicLocalPath(String picLocalPath) {
        this.picLocalPath = picLocalPath == null ? null : picLocalPath.trim();
    }

    public void setRawPicHeight(Integer rawPicHeight) {
        this.rawPicHeight = rawPicHeight;
    }

    public void setRawPicWidth(Integer rawPicWidth) {
        this.rawPicWidth = rawPicWidth;
    }

    public void setMarkSet(String markSet) {
        this.markSet = markSet == null ? null : markSet.trim();
    }

    public void setHasMarked(Integer hasMarked) {
        this.hasMarked = hasMarked;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime == null ? null : (Date) createTime.clone();
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy == null ? null : createBy.trim();
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime == null ? null : (Date) updateTime.clone();
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy == null ? null : updateBy.trim();
    }

    public void setIsValid(Integer isValid) {
        this.isValid = isValid;
    }

    public List<CarModelPicMarkItem> getCarModelPicMarkItemList() {
        return carModelPicMarkItemList;
    }

    public void setCarModelPicMarkItemList(List<CarModelPicMarkItem> carModelPicMarkItemList) {
        this.carModelPicMarkItemList = carModelPicMarkItemList;
    }

    public List<String> getOptionalPicSequences() {
        return optionalPicSequences;
    }

    public void setOptionalPicSequences(List<String> optionalPicSequences) {
        this.optionalPicSequences = optionalPicSequences;
    }
}
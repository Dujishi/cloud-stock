package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * for car brand
 * CarModelPicMarkEntity
 *
 * @author suqin
 */
public class CarModelPicMarkEntity implements Serializable{

    private static final long serialVersionUID = -8287279230224736726L;
    private Integer modelId;
    private String picNum;
    private String picName;
    private String picLocalPath;
    private Integer rawPicHeight;
    private Integer rawPicWidth;
//    private Integer markedPicHeight;
//    private Integer markedPicWidth;
    private Integer markedErrorType;
    private String markSet;
    private Integer hasMarked;
    private Date createTime;
    private String createBy;
    private Date updateTime;
    private String updateBy;
    private Integer isValid;

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

//    public Integer getMarkedPicHeight() {
//        return this.markedPicHeight;
//    }
//
//    public Integer getMarkedPicWidth() {
//        return this.markedPicWidth;
//    }

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

//    public void setMarkedPicHeight(Integer markedPicHeight) {
//        this.markedPicHeight = markedPicHeight;
//    }
//
//    public void setMarkedPicWidth(Integer markedPicWidth) {
//        this.markedPicWidth = markedPicWidth;
//    }

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

    public Integer getMarkedErrorType() {
        return markedErrorType;
    }

    public void setMarkedErrorType(Integer markedErrorType) {
        this.markedErrorType = markedErrorType;
    }
}
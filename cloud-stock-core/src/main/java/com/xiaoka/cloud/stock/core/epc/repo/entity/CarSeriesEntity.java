package com.xiaoka.cloud.stock.core.epc.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * CarSeriesEntity
 *
 * @author suqin
 */
public class CarSeriesEntity implements Serializable {

	private static final long serialVersionUID = 3217253154959730675L;
	private Integer seriesId;
	private String seriesName;
	private String series;
	private String seriesBbg;
	private String seriesBbgEopYear;
	private String seriesBbgSopYear;
	private Integer brandId;
	private String brandName;
	private Integer makeId;
	private String makeName;
	private Integer isValid;
	private Date createTime;
	private String createBy;
	private Date updateTime;
	private String updateBy;

	public Integer getSeriesId() {
		return this.seriesId;
	}

	public String getSeriesName() {
		return this.seriesName;
	}

	public String getSeries() {
		return this.series;
	}

	public String getSeriesBbg() {
		return this.seriesBbg;
	}

	public String getSeriesBbgEopYear() {
		return this.seriesBbgEopYear;
	}

	public String getSeriesBbgSopYear() {
		return this.seriesBbgSopYear;
	}

	public Integer getBrandId() {
		return this.brandId;
	}

	public String getBrandName() {
		return this.brandName;
	}

	public Integer getMakeId() {
		return this.makeId;
	}

	public String getMakeName() {
		return this.makeName;
	}

	public Integer getIsValid() {
		return this.isValid;
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

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName == null ? null : seriesName.trim();
	}

	public void setSeries(String series) {
		this.series = series == null ? null : series.trim();
	}

	public void setSeriesBbg(String seriesBbg) {
		this.seriesBbg = seriesBbg == null ? null : seriesBbg.trim();
	}

	public void setSeriesBbgEopYear(String seriesBbgEopYear) {
		this.seriesBbgEopYear = seriesBbgEopYear == null ? null : seriesBbgEopYear.trim();
	}

	public void setSeriesBbgSopYear(String seriesBbgSopYear) {
		this.seriesBbgSopYear = seriesBbgSopYear == null ? null : seriesBbgSopYear.trim();
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName == null ? null : brandName.trim();
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName == null ? null : makeName.trim();
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
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

}
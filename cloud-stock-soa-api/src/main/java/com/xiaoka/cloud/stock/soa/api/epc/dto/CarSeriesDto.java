package com.xiaoka.cloud.stock.soa.api.epc.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/11/29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarSeriesDto implements Serializable {
	private static final long serialVersionUID = 2456524107013460622L;

	private Integer seriesId;
	private String  seriesName;
	private String  series;
	private String  seriesBbg;
	private String  seriesBbgEopYear;
	private String  seriesBbgSopYear;
	private Integer brandId;
	private String  brandName;
	private Integer makeId;
	private String  makeName;

	public Integer getSeriesId() {
		return seriesId;
	}

	public void setSeriesId(Integer seriesId) {
		this.seriesId = seriesId;
	}

	public String getSeriesName() {
		return seriesName;
	}

	public void setSeriesName(String seriesName) {
		this.seriesName = seriesName;
	}

	public String getSeries() {
		return series;
	}

	public void setSeries(String series) {
		this.series = series;
	}

	public String getSeriesBbg() {
		return seriesBbg;
	}

	public void setSeriesBbg(String seriesBbg) {
		this.seriesBbg = seriesBbg;
	}

	public String getSeriesBbgEopYear() {
		return seriesBbgEopYear;
	}

	public void setSeriesBbgEopYear(String seriesBbgEopYear) {
		this.seriesBbgEopYear = seriesBbgEopYear;
	}

	public String getSeriesBbgSopYear() {
		return seriesBbgSopYear;
	}

	public void setSeriesBbgSopYear(String seriesBbgSopYear) {
		this.seriesBbgSopYear = seriesBbgSopYear;
	}

	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public String getBrandName() {
		return brandName;
	}

	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}

	public Integer getMakeId() {
		return makeId;
	}

	public void setMakeId(Integer makeId) {
		this.makeId = makeId;
	}

	public String getMakeName() {
		return makeName;
	}

	public void setMakeName(String makeName) {
		this.makeName = makeName;
	}
}

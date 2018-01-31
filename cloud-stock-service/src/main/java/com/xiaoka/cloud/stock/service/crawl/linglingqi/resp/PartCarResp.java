package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/19 14:31
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartCarResp implements Serializable {

	private static final long serialVersionUID = -3904730144597578230L;

	private String url;
	@JsonProperty("uri_param")
	private UriParam uriParam;
	@JsonProperty("group_name")
	private String groupName;
	private String year;
	@JsonProperty("main_group_name")
	private String mainGroupName;
	@JsonProperty("cars_model")
	private String carsModel;
	private String market;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public UriParam getUriParam() {
		return uriParam;
	}

	public void setUriParam(UriParam uriParam) {
		this.uriParam = uriParam;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMainGroupName() {
		return mainGroupName;
	}

	public void setMainGroupName(String mainGroupName) {
		this.mainGroupName = mainGroupName;
	}

	public String getCarsModel() {
		return carsModel;
	}

	public void setCarsModel(String carsModel) {
		this.carsModel = carsModel;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}
}

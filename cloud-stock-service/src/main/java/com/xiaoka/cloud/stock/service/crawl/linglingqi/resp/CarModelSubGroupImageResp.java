package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/12/20 14:54
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarModelSubGroupImageResp implements Serializable {

	private static final long serialVersionUID = -3380644916701471706L;

	private int h;
	@JsonProperty("imgurl")
	private String imgUrl;
	private String auth;
	@JsonProperty("mapdata")
	private List<List<String>> mapData;
	private int w;

	public int getH() {
		return h;
	}

	public void setH(int h) {
		this.h = h;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public List<List<String>> getMapData() {
		return mapData;
	}

	public void setMapData(List<List<String>> mapData) {
		this.mapData = mapData;
	}

	public int getW() {
		return w;
	}

	public void setW(int w) {
		this.w = w;
	}
}

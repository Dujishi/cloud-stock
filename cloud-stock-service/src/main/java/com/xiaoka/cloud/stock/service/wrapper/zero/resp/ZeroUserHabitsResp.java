package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/11 14:25
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroUserHabitsResp implements Serializable {

	private static final long serialVersionUID = -1211642467050441676L;

	@JsonProperty("vin_filter")
	private String vinFilter;

	public String getVinFilter() {
		return vinFilter;
	}

	public void setVinFilter(String vinFilter) {
		this.vinFilter = vinFilter;
	}
}

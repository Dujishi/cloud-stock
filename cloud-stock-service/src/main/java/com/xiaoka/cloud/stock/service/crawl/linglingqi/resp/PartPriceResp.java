package com.xiaoka.cloud.stock.service.crawl.linglingqi.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/12/19 14:13
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartPriceResp implements Serializable{

	private static final long serialVersionUID = -4332819453621506002L;
	private List<PartPriceDetailResp> data;
	@JsonProperty("is_original_part")
	private int isOriginalPart;
	private String title;

	public List<PartPriceDetailResp> getData() {
		return data;
	}

	public void setData(List<PartPriceDetailResp> data) {
		this.data = data;
	}

	public int getIsOriginalPart() {
		return isOriginalPart;
	}

	public void setIsOriginalPart(int isOriginalPart) {
		this.isOriginalPart = isOriginalPart;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}

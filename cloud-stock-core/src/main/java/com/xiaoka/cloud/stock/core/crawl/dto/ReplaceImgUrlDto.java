package com.xiaoka.cloud.stock.core.crawl.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/20
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ReplaceImgUrlDto implements Serializable {
	private static final long serialVersionUID = 3625034817424940931L;

	private String oldUrl;

	private String newUrl;

	public String getOldUrl() {
		return oldUrl;
	}

	public void setOldUrl(String oldUrl) {
		this.oldUrl = oldUrl;
	}

	public String getNewUrl() {
		return newUrl;
	}

	public void setNewUrl(String newUrl) {
		this.newUrl = newUrl;
	}
}

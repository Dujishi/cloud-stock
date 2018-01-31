package com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto;

import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;

import java.io.Serializable;
import java.util.Map;

/**
 * Do something
 *
 * @author gancao 2017/12/28 19:21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class AccountMsgDto implements Serializable {

	private static final long serialVersionUID = -1891929780579764803L;

	private ZeroAccountEntity zeroAccountEntity;
	private Map<String, String> cookieMap;

	public ZeroAccountEntity getZeroAccountEntity() {
		return zeroAccountEntity;
	}

	public void setZeroAccountEntity(ZeroAccountEntity zeroAccountEntity) {
		this.zeroAccountEntity = zeroAccountEntity;
	}

	public Map<String, String> getCookieMap() {
		return cookieMap;
	}

	public void setCookieMap(Map<String, String> cookieMap) {
		this.cookieMap = cookieMap;
	}
}

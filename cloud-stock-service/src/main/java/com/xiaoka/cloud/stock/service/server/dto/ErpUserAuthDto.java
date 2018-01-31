package com.xiaoka.cloud.stock.service.server.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2018/1/11
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ErpUserAuthDto implements Serializable {
	private static final long serialVersionUID = -2918565499191818289L;

	private String userId;

	private String shopId;

	private String cookies;

	private String specialId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getCookies() {
		return cookies;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public String getSpecialId() {
		return specialId;
	}

	public void setSpecialId(String specialId) {
		this.specialId = specialId;
	}
}

package com.xiaoka.cloud.stock.client.net.dto;

import java.io.Serializable;
import java.util.Map;

import com.xiaoka.cloud.stock.client.net.contants.RequestTypeEnum;

/**
 * @author zhouze
 * @date 2018/1/11
 * @see [相关类/方法]
 * @since [版本号]
 */
public class TransferDto implements Serializable {
	private static final long serialVersionUID = -2483724739308833102L;

	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 请求类型
	 * {@link RequestTypeEnum}
	 */
	private String type;
	/**
	 * 请求参数
	 */
	private String requestBody;

	/**
	 * 请求头
	 */
	private Map<String, String> headers;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

}

package com.xiaoka.cloud.stock.service.server.dto;

import com.xiaoka.cloud.stock.service.server.constants.RequestTypeEnum;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhouze
 * @date 2017/12/25
 * @see [相关类/方法]
 * @since [版本号]
 */
public class TransferDto implements Serializable {
	private static final long serialVersionUID = 1300115309587072448L;

	public TransferDto() {
	}

	public TransferDto(String channel, String url, String type) {
		this.channel = channel;
		this.url = url;
		this.type = type;
	}

	private String channel;
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

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

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

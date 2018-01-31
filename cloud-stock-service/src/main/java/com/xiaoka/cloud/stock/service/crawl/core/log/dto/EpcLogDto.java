package com.xiaoka.cloud.stock.service.crawl.core.log.dto;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2018/1/12 15:01
 * @see [相关类/方法]
 * @since [版本号]
 */
public class EpcLogDto implements Serializable {

	private static final long serialVersionUID = 5229932779149886777L;

	private Integer supplierId;
	private Integer userId;
	private String source;
	private String ip;
	private String realIp;
	private long execTime;
	/**
	 * {@link com.xiaoka.cloud.stock.service.crawl.core.log.constant.EpcLogTypeEnum}
	 */
	private String type;
	private String url;
	private String args;
	private String message;
	private String status;

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getRealIp() {
		return realIp;
	}

	public void setRealIp(String realIp) {
		this.realIp = realIp;
	}

	public long getExecTime() {
		return execTime;
	}

	public void setExecTime(long execTime) {
		this.execTime = execTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public Integer getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Integer supplierId) {
		this.supplierId = supplierId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}

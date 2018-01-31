package com.xiaoka.cloud.stock.core.constraint.repo.entity;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/5
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ValveCacheEntity implements Serializable {
	private static final long serialVersionUID = 7606473713629306475L;

	/**
	 * 键
	 */
	private String key;
	/**
	 * 包名类名
	 */
	private String className;
	/**
	 * 方法名
	 */
	private String methodName;
	/**
	 * 参数JSON
	 */
	private String paramJson;
	/**
	 * 方法长签名
	 */
	private String methodSign;
	/**
	 * 最后一次有效时间
	 * 参与计算有效期
	 */
	private Long   lastTime;
	/**
	 * 最后一次更新时间
	 */
	private String updateTime;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodSign() {
		return methodSign;
	}

	public String getParamJson() {
		return paramJson;
	}

	public void setParamJson(String paramJson) {
		this.paramJson = paramJson;
	}

	public void setMethodSign(String methodSign) {
		this.methodSign = methodSign;
	}

	public Long getLastTime() {
		return lastTime;
	}

	public void setLastTime(Long lastTime) {
		this.lastTime = lastTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}

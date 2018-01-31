package com.xiaoka.cloud.stock.client.business.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * 数据采集的策略信息
 *
 * @author zhouze
 * @date 2018/1/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public class StrategyDto implements Serializable {
	private static final long serialVersionUID = -6749069797809203916L;

	/**
	 * 策略标记
	 */
	private String flag;

	/**
	 * 附加信息
	 */
	private Map<String, String> addInfo;

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Map<String, String> getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(Map<String, String> addInfo) {
		this.addInfo = addInfo;
	}
}

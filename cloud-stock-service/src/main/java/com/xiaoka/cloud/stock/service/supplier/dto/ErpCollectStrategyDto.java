package com.xiaoka.cloud.stock.service.supplier.dto;

import java.io.Serializable;
import java.util.Map;

/**
 * @author zhouze
 * @date 2018/1/16
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ErpCollectStrategyDto implements Serializable {
	private static final long serialVersionUID = -644870187720711532L;

	/**
	 * 标识
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

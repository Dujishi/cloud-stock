package com.xiaoka.cloud.stock.service.open.dto.mongo;

import com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouze
 * @date 2017/11/21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class StockDataInMongoDto implements Serializable {
	private static final long serialVersionUID = -102780799106077537L;

	private SupplierSyncParam supplierSyncParam;

	private String appId;

	private String methodName;

	private Date currentTime;

	public SupplierSyncParam getSupplierSyncParam() {
		return supplierSyncParam;
	}

	public void setSupplierSyncParam(SupplierSyncParam supplierSyncParam) {
		this.supplierSyncParam = supplierSyncParam;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public Date getCurrentTime() {
		return currentTime;
	}

	public void setCurrentTime(Date currentTime) {
		this.currentTime = currentTime;
	}
}

package com.xiaoka.cloud.stock.service.open.dto.param;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/7
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SupplierSyncParam implements Serializable {
	private static final long serialVersionUID = 924463278900196600L;

	/**
	 * 外部供应商id，是外围系统用来确定供应商的唯一标识
	 */
	private String supplierId;

	/**
	 * 供应商配件
	 */
	private List<SupplierPartSyncParam> parts;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public List<SupplierPartSyncParam> getParts() {
		return parts;
	}

	public void setParts(List<SupplierPartSyncParam> parts) {
		this.parts = parts;
	}
}

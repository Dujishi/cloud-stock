package input;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/10
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SupplierSyncInput implements Serializable {
	/**
	 * 外部供应商id，是外围系统用来确定供应商的唯一标识
	 */
	private String supplierId;

	/**
	 * 供应商配件
	 */
	private List<SupplierStockPartSyncInput> parts;

	public String getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(String supplierId) {
		this.supplierId = supplierId;
	}

	public List<SupplierStockPartSyncInput> getParts() {
		return parts;
	}

	public void setParts(List<SupplierStockPartSyncInput> parts) {
		this.parts = parts;
	}
}

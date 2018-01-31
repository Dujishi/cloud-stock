package com.xiaoka.cloud.stock.core.stock.repo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 未定的配件价格
 *
 * @author zhouze
 */
public class CloudPartPriceUncertainEntity implements Serializable {
	private static final long serialVersionUID = -5587926944445887930L;
	/**
	 * 主键
	 */
	private Integer    id;
	/**
	 * 配件id
	 */
	private Integer    partId;
	/**
	 * 外部配件id
	 */
	private String     cPartId;
	/**
	 * 配件名称
	 */
	private String     partName;
	/**
	 * 仓库id
	 */
	private Integer    depotId;
	/**
	 * 外部仓库id
	 */
	private String     cDepotId;
	/**
	 * 商家id
	 */
	private Integer    shopId;
	/**
	 * 外部供应商id
	 */
	private String     cSupplierId;
	/**
	 * 零件码
	 */
	private String     oeNo;
	/**
	 * 价格key
	 */
	private String     priceCode;
	/**
	 * 价格值
	 */
	private BigDecimal price;
	/**
	 * 是否有效
	 */
	private Integer    isValid;
	/**
	 * 版本号
	 */
	private Integer    version;
	/**
	 * 创建时间
	 */
	private Date       createTime;
	/**
	 * 修改时间
	 */
	private Date       updateTime;
	/**
	 * 备注
	 */
	private String     remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getcPartId() {
		return cPartId;
	}

	public void setcPartId(String cPartId) {
		this.cPartId = cPartId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getDepotId() {
		return depotId;
	}

	public void setDepotId(Integer depotId) {
		this.depotId = depotId;
	}

	public String getcDepotId() {
		return cDepotId;
	}

	public void setcDepotId(String cDepotId) {
		this.cDepotId = cDepotId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public String getcSupplierId() {
		return cSupplierId;
	}

	public void setcSupplierId(String cSupplierId) {
		this.cSupplierId = cSupplierId;
	}

	public String getOeNo() {
		return oeNo;
	}

	public void setOeNo(String oeNo) {
		this.oeNo = oeNo;
	}

	public String getPriceCode() {
		return priceCode;
	}

	public void setPriceCode(String priceCode) {
		this.priceCode = priceCode;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Integer getIsValid() {
		return isValid;
	}

	public void setIsValid(Integer isValid) {
		this.isValid = isValid;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
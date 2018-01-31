package com.xiaoka.cloud.stock.core.stock.repo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 云仓配件价格
 *
 * @author zhouze
 */
public class CloudPartPriceEntity implements Serializable {
	private static final long serialVersionUID = 4499029467286391847L;
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
	 * 零件号
	 */
	private String     oeNo;
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
	 * 成本价
	 */
	private BigDecimal costPrice;
	/**
	 * 同行价
	 */
	private BigDecimal tradePrice;
	/**
	 * 保险公司价格
	 */
	private BigDecimal insurancePrice;
	/**
	 * 修理厂价格
	 */
	private BigDecimal repairFactoryPrice;
	/**
	 * 4S店价格
	 */
	private BigDecimal repairStationPrice;
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

	public String getOeNo() {
		return oeNo;
	}

	public void setOeNo(String oeNo) {
		this.oeNo = oeNo;
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

	public BigDecimal getCostPrice() {
		return costPrice;
	}

	public void setCostPrice(BigDecimal costPrice) {
		this.costPrice = costPrice;
	}

	public BigDecimal getTradePrice() {
		return tradePrice;
	}

	public void setTradePrice(BigDecimal tradePrice) {
		this.tradePrice = tradePrice;
	}

	public BigDecimal getInsurancePrice() {
		return insurancePrice;
	}

	public void setInsurancePrice(BigDecimal insurancePrice) {
		this.insurancePrice = insurancePrice;
	}

	public BigDecimal getRepairFactoryPrice() {
		return repairFactoryPrice;
	}

	public void setRepairFactoryPrice(BigDecimal repairFactoryPrice) {
		this.repairFactoryPrice = repairFactoryPrice;
	}

	public BigDecimal getRepairStationPrice() {
		return repairStationPrice;
	}

	public void setRepairStationPrice(BigDecimal repairStationPrice) {
		this.repairStationPrice = repairStationPrice;
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
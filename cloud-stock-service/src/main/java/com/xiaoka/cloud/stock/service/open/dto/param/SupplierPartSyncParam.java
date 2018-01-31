package com.xiaoka.cloud.stock.service.open.dto.param;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/7
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SupplierPartSyncParam implements Serializable {
	private static final long serialVersionUID = 924463278900196600L;

	/**
	 * 外部系统仓库Id
	 */
	private String     depotId;
	/**
	 * 仓库编号
	 */
	private String     depotCode;
	/**
	 * 仓库名称
	 */
	private String     depotName;
	/**
	 * 仓库所在省份
	 */
	private String     depotProvince;
	/**
	 * 仓库所在城市
	 */
	private String     depotCity;
	/**
	 * 仓库地址
	 */
	private String     depotAddress;
	/**
	 * 外部系统的配件id
	 */
	private String     partId;
	/**
	 * 零件号
	 */
	private String     oeNo;
	/**
	 * 配件名称
	 */
	private String     partName;
	/**
	 * 配件库存余量
	 */
	private BigDecimal balanceCount;
	/**
	 * 产地
	 */
	private String     originPlace;
	/**
	 * 配件品牌
	 */
	private String     partBrand;
	/**
	 * 生产厂商/制造商
	 */
	private String     manufacturer;
	/**
	 * 运营价、成本价
	 */
	private BigDecimal costPrice;
	/**
	 * 提供给同行价格
	 */
	private BigDecimal tradePrice;
	/**
	 * 提供给保险公司价
	 */
	private BigDecimal insurancePrice;
	/**
	 * 提供给修理厂价格
	 */
	private BigDecimal repairFactoryPrice;
	/**
	 * 维修站价/4S店价格
	 */
	private BigDecimal repairStationPrice;
	/**
	 * 操作类型
	 */
	private Integer    operateMode;
	/**
	 * 创建时间
	 */
	private String     createTime;
	/**
	 * 修改时间
	 */
	private String     updateTime;

	/**
	 * 不确定的供应商价格数据
	 */
	private List<SupplierPartUncertainPrice> uncertainPrice;

	public String getDepotId() {
		return depotId;
	}

	public void setDepotId(String depotId) {
		this.depotId = depotId;
	}

	public String getDepotCode() {
		return depotCode;
	}

	public void setDepotCode(String depotCode) {
		this.depotCode = depotCode;
	}

	public String getDepotName() {
		return depotName;
	}

	public void setDepotName(String depotName) {
		this.depotName = depotName;
	}

	public String getDepotProvince() {
		return depotProvince;
	}

	public void setDepotProvince(String depotProvince) {
		this.depotProvince = depotProvince;
	}

	public String getDepotCity() {
		return depotCity;
	}

	public void setDepotCity(String depotCity) {
		this.depotCity = depotCity;
	}

	public String getDepotAddress() {
		return depotAddress;
	}

	public void setDepotAddress(String depotAddress) {
		this.depotAddress = depotAddress;
	}

	public String getPartId() {
		return partId;
	}

	public void setPartId(String partId) {
		this.partId = partId;
	}

	public String getOeNo() {
		return oeNo;
	}

	public void setOeNo(String oeNo) {
		this.oeNo = oeNo;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public BigDecimal getBalanceCount() {
		return balanceCount;
	}

	public void setBalanceCount(BigDecimal balanceCount) {
		this.balanceCount = balanceCount;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public void setOriginPlace(String originPlace) {
		this.originPlace = originPlace;
	}

	public String getPartBrand() {
		return partBrand;
	}

	public void setPartBrand(String partBrand) {
		this.partBrand = partBrand;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
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

	public Integer getOperateMode() {
		return operateMode;
	}

	public void setOperateMode(Integer operateMode) {
		this.operateMode = operateMode;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public List<SupplierPartUncertainPrice> getUncertainPrice() {
		return uncertainPrice;
	}

	public void setUncertainPrice(List<SupplierPartUncertainPrice> uncertainPrice) {
		this.uncertainPrice = uncertainPrice;
	}
}

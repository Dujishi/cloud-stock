package com.xiaoka.cloud.stock.core.stock.repo.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 云仓配件
 *
 * @author zhouze
 */
public class CloudPartEntity implements Serializable {
	private static final long serialVersionUID = 13366150563421565L;
	/**
	 * 主键
	 */
	private Integer    id;
	/**
	 * 外部配件id
	 */
	private String     cPartId;
	/**
	 * 库存id
	 */
	private Integer    depotId;
	/**
	 * 外部库存id
	 */
	private String     cDepotId;
	/**
	 * 供应商Id
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
	 * 配件名称
	 */
	private String     partName;
	/**
	 * 配件品牌
	 */
	private String     partBrand;
	/**
	 * 库存余量
	 */
	private BigDecimal balanceCount;
	/**
	 * 产地
	 */
	private String     originPlace;
	/**
	 * 生产厂商
	 */
	private String     manufacturer;
	/**
	 * 操作类型
	 * {@link com.xiaoka.cloud.stock.core.stock.constant.OperateModeEnum}
	 */
	private Integer    cOperateMode;
	/**
	 * 外部创建时间
	 */
	private String     cCreateTime;
	/**
	 * 外部修改时间
	 */
	private String     cUpdateTime;
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

	public String getcPartId() {
		return cPartId;
	}

	public void setcPartId(String cPartId) {
		this.cPartId = cPartId;
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

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartBrand() {
		return partBrand;
	}

	public void setPartBrand(String partBrand) {
		this.partBrand = partBrand;
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

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public Integer getcOperateMode() {
		return cOperateMode;
	}

	public void setcOperateMode(Integer cOperateMode) {
		this.cOperateMode = cOperateMode;
	}

	public String getcCreateTime() {
		return cCreateTime;
	}

	public void setcCreateTime(String cCreateTime) {
		this.cCreateTime = cCreateTime;
	}

	public String getcUpdateTime() {
		return cUpdateTime;
	}

	public void setcUpdateTime(String cUpdateTime) {
		this.cUpdateTime = cUpdateTime;
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
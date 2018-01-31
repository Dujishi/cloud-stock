package com.xiaoka.cloud.stock.core.stock.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 云仓仓库
 *
 * @author zhouze
 */
public class CloudDepotEntity implements Serializable {

	private static final long serialVersionUID = -3134022053272905111L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 外部仓库id
	 */
	private String  cDepotId;
	/**
	 * 商家id
	 */
	private Integer shopId;
	/**
	 * 外部供应商id
	 */
	private String  cSupplierId;
	/**
	 * 仓库编码
	 */
	private String  depotCode;
	/**
	 * 仓库名称
	 */
	private String  depotName;
	/**
	 * 省份
	 */
	private String  depotProvince;
	/**
	 * 城市
	 */
	private String  depotCity;
	/**
	 * 地址
	 */
	private String  depotAddress;
	/**
	 * 是否有效
	 */
	private Integer isValid;
	/**
	 * 版本号
	 */
	private Integer version;
	/**
	 * 创建时间
	 */
	private Date    createTime;
	/**
	 * 修改时间
	 */
	private Date    updateTime;
	/**
	 * 备注
	 */
	private String  remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
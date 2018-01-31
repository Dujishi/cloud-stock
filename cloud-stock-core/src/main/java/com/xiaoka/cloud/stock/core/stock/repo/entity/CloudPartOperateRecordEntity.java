package com.xiaoka.cloud.stock.core.stock.repo.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * 云仓操作记录
 *
 * @author zhouze
 */
public class CloudPartOperateRecordEntity implements Serializable {
	private static final long serialVersionUID = 4403390976125288934L;
	/**
	 * 主键
	 */
	private Integer id;
	/**
	 * 仓库id
	 */
	private Integer depotId;
	/**
	 * 商家id
	 */
	private Integer shopId;
	/**
	 * 配件id
	 */
	private Integer partId;
	/**
	 * 配件名称
	 */
	private String  partName;
	/**
	 * 操作类型
	 */
	private Integer cOperateMode;
	/**
	 * 创建时间
	 */
	private String  cCreateTime;
	/**
	 * 修改时间
	 */
	private String  cUpdateTime;
	/**
	 * 是否有效
	 */
	private Integer isValid;
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

	public Integer getDepotId() {
		return depotId;
	}

	public void setDepotId(Integer depotId) {
		this.depotId = depotId;
	}

	public Integer getShopId() {
		return shopId;
	}

	public void setShopId(Integer shopId) {
		this.shopId = shopId;
	}

	public Integer getPartId() {
		return partId;
	}

	public void setPartId(Integer partId) {
		this.partId = partId;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
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
package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 零件组
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSubGroupDto implements Serializable {
	private static final long serialVersionUID = -1653030764760345894L;
	private Integer cId;
	/**
	 * 主组id
	 */
	private Integer groupId;
	/**
	 * 零件组id
	 */
	private Integer subGroupId;
	/**
	 * 主组名称
	 */
	private String  groupName;
	/**
	 * 主组图片地址
	 */
	private String  groupImg;
	/**
	 * 零件组
	 */
	private String  subGroup;
	/**
	 * 零件组名称
	 */
	private String  subGroupName;
	/**
	 * 零件组图片地址
	 */
	private String  subGroupUrl;
	/**
	 * 零件组型号名
	 */
	private String  subModel;
	/**
	 * 零件组编号
	 */
	private String  subMid;
	/**
	 * 零件组描述
	 */
	private String  subDesc;
	/**
	 * 车型
	 */
	private String  carModel;
	/**
	 * 品牌
	 */
	private String  brand;
	/**
	 * 市场
	 */
	private String  market;
	/**
	 * 年份
	 */
	private String  year;
	/**
	 * 访问auth
	 */
	private String  auth;

	private String uri;

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getSubGroupId() {
		return subGroupId;
	}

	public void setSubGroupId(Integer subGroupId) {
		this.subGroupId = subGroupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getGroupImg() {
		return groupImg;
	}

	public void setGroupImg(String groupImg) {
		this.groupImg = groupImg;
	}

	public String getSubGroup() {
		return subGroup;
	}

	public void setSubGroup(String subGroup) {
		this.subGroup = subGroup;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public String getSubGroupUrl() {
		return subGroupUrl;
	}

	public void setSubGroupUrl(String subGroupUrl) {
		this.subGroupUrl = subGroupUrl;
	}

	public String getSubModel() {
		return subModel;
	}

	public void setSubModel(String subModel) {
		this.subModel = subModel;
	}

	public String getSubMid() {
		return subMid;
	}

	public void setSubMid(String subMid) {
		this.subMid = subMid;
	}

	public String getSubDesc() {
		return subDesc;
	}

	public void setSubDesc(String subDesc) {
		this.subDesc = subDesc;
	}

	public String getCarModel() {
		return carModel;
	}

	public void setCarModel(String carModel) {
		this.carModel = carModel;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getMarket() {
		return market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}
}

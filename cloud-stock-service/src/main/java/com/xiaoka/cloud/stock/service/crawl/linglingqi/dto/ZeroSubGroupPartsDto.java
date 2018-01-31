package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 零件组与零件关系表
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSubGroupPartsDto implements Serializable {
	private static final long serialVersionUID = -8052722078554580760L;

	private Integer cId;
	/**
	 * 主组id
	 */
	private Integer groupId;
	/**
	 * 子组id
	 */
	private Integer subGroupId;
	/**
	 * 主组名称
	 */
	private String  groupName;
	/**
	 * 主组图片
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
	 * 零件号
	 */
	private String  pid;
	/**
	 * 真实零件号
	 */
	private String  realPid;
	/**
	 * 图片地址
	 */
	private String  imgUrl;
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
	 * 锚点id
	 */
	private String  itId;
	/**
	 * 锚点展示名
	 */
	private String  num;
	/**
	 * 件数
	 */
	private String  count;
	/**
	 * 访问auth
	 */
	private String  auth;

	private String labelName;

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

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getRealPid() {
		return realPid;
	}

	public void setRealPid(String realPid) {
		this.realPid = realPid;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
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

	public String getItId() {
		return itId;
	}

	public void setItId(String itId) {
		this.itId = itId;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}
}

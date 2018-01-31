package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 主组
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroGroupDto implements Serializable {
	private static final long serialVersionUID = 7119585042503823017L;

	private Integer cId;
	/**
	 * 组id
	 */
	private Integer groupId;
	/**
	 * 主组序列
	 */
	private String groupNum;
	/**
	 * 主组名称
	 */
	private String groupName;
	/**
	 * 主组图片地址
	 */
	private String groupImg;
	/**
	 * 车型数据
	 */
	private String carModel;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 市场
	 */
	private String market;
	/**
	 * 年份
	 */
	private String year;
	/**
	 * 发动机
	 */
	private String engine;
	/**
	 * 变速箱
	 */
	private String gearBox;
	/**
	 * 访问auth
	 */
	private String auth;

	/**
	 * 零件组列表
	 */
	private List<ZeroSubGroupDto> zeroSubGroups;

	public Integer getcId() {
		return cId;
	}

	public void setcId(Integer cId) {
		this.cId = cId;
	}

	public String getGroupNum() {
		return groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
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

	public String getEngine() {
		return engine;
	}

	public void setEngine(String engine) {
		this.engine = engine;
	}

	public String getGearBox() {
		return gearBox;
	}

	public void setGearBox(String gearBox) {
		this.gearBox = gearBox;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public List<ZeroSubGroupDto> getZeroSubGroups() {
		return zeroSubGroups;
	}

	public void setZeroSubGroups(List<ZeroSubGroupDto> zeroSubGroups) {
		this.zeroSubGroups = zeroSubGroups;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
}

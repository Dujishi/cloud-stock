package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 零件组图片锚点信息表，同一张图片可能存在多条数据
 *
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSubGroupImgDto implements Serializable {
	private static final long serialVersionUID = -7000164202865119532L;

	/**
	 * 主组id
	 */
	private Integer    groupId;
	/**
	 * 子组id
	 */
	private Integer    subGroupId;
	/**
	 * 主组名称
	 */
	private String     groupName;
	/**
	 * 零件组
	 */
	private String     subgroup;
	/**
	 * 零件组名称
	 */
	private String     subGroupName;
	/**
	 * 图片地址
	 */
	private String     imgUrl;
	/**
	 * 宽
	 */
	private BigDecimal width;
	/**
	 * 高
	 */
	private BigDecimal height;

	private String itId;

	private BigDecimal x1;

	private BigDecimal y1;

	private BigDecimal x2;

	private BigDecimal y2;
	/**
	 * 访问auth
	 */
	private String     auth;

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

	public String getSubgroup() {
		return subgroup;
	}

	public void setSubgroup(String subgroup) {
		this.subgroup = subgroup;
	}

	public String getSubGroupName() {
		return subGroupName;
	}

	public void setSubGroupName(String subGroupName) {
		this.subGroupName = subGroupName;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public BigDecimal getWidth() {
		return width;
	}

	public void setWidth(BigDecimal width) {
		this.width = width;
	}

	public BigDecimal getHeight() {
		return height;
	}

	public void setHeight(BigDecimal height) {
		this.height = height;
	}

	public String getItId() {
		return itId;
	}

	public void setItId(String itId) {
		this.itId = itId;
	}

	public BigDecimal getX1() {
		return x1;
	}

	public void setX1(BigDecimal x1) {
		this.x1 = x1;
	}

	public BigDecimal getY1() {
		return y1;
	}

	public void setY1(BigDecimal y1) {
		this.y1 = y1;
	}

	public BigDecimal getX2() {
		return x2;
	}

	public void setX2(BigDecimal x2) {
		this.x2 = x2;
	}

	public BigDecimal getY2() {
		return y2;
	}

	public void setY2(BigDecimal y2) {
		this.y2 = y2;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}
}

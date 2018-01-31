package com.xiaoka.cloud.stock.service.epc.dto;

import java.io.Serializable;

/**
 * 零件信息实体对象
 *
 * @author gancao 2017/11/13 10:59
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartInfoDto implements Serializable {

	private static final long serialVersionUID = 1680346290764575952L;

	//零件号
	private String partCode;
	//零件名称
	private String partName;
	//总成id
	private Integer assemblyId;
	//总成名称
	private String assemblyName;
	//分总成id
	private Integer subAssemblyId;
	//分总成名称
	private String subAssemblyName;
	//分总零件图号
	private String picNum;
	//图号名
	private String picName;
	//分总成图片地址
	private String picUrl;
	//图片序号
	private String picSequence;
	//厂商名称
	private String oemName;
	//厂商品牌
	private String oemBrand;
	//类型
	private String source;
	//4S报价
	private String price;
	//备注
	private String remark;

	private int sequence = 999;

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public Integer getAssemblyId() {
		return assemblyId;
	}

	public void setAssemblyId(Integer assemblyId) {
		this.assemblyId = assemblyId;
	}

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public Integer getSubAssemblyId() {
		return subAssemblyId;
	}

	public void setSubAssemblyId(Integer subAssemblyId) {
		this.subAssemblyId = subAssemblyId;
	}

	public String getSubAssemblyName() {
		return subAssemblyName;
	}

	public void setSubAssemblyName(String subAssemblyName) {
		this.subAssemblyName = subAssemblyName;
	}

	public String getPicNum() {
		return picNum;
	}

	public void setPicNum(String picNum) {
		this.picNum = picNum;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public String getOemName() {
		return oemName;
	}

	public void setOemName(String oemName) {
		this.oemName = oemName;
	}

	public String getOemBrand() {
		return oemBrand;
	}

	public void setOemBrand(String oemBrand) {
		this.oemBrand = oemBrand;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getPicSequence() {
		return picSequence;
	}

	public void setPicSequence(String picSequence) {
		this.picSequence = picSequence;
	}

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}
}

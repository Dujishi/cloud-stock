package com.xiaoka.cloud.stock.service.epc.dto;

import com.xiaoka.cloud.stock.service.epc.output.ExtraParamDto;

import java.io.Serializable;

/**
 * 零件分总成实体对象
 *
 * @author gancao 2017/11/13 10:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartSubAssemblyDto extends ExtraParamDto implements Serializable {

	private static final long serialVersionUID = 4264807825515653494L;

	//分总成id
	private Integer subAssemblyId;
	//分总成名称
	private String subAssemblyName;
	//分总零件图号
	private String picNum;
	//图号名称
	private String picName;
	//分总成图片地址
	private String picUrl;
	//打点图片
	private CarModelPicMarkDto picMark;

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

	public String getPicName() {
		return picName;
	}

	public void setPicName(String picName) {
		this.picName = picName;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public CarModelPicMarkDto getPicMark() {
		return picMark;
	}

	public void setPicMark(CarModelPicMarkDto picMark) {
		this.picMark = picMark;
	}
}

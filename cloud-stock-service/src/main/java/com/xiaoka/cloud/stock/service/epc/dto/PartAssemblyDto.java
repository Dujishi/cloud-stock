package com.xiaoka.cloud.stock.service.epc.dto;

import com.xiaoka.cloud.stock.service.epc.output.ExtraParamDto;

import java.io.Serializable;
import java.util.List;

/**
 * 零件总成实体对象
 *
 * @author gancao 2017/11/13 10:50
 * @see [相关类/方法]
 * @since [版本号]
 */
public class PartAssemblyDto extends ExtraParamDto implements Serializable {

	private static final long serialVersionUID = -4258187826760773328L;

	//总成id
	private String assemblyId;
	//总成名称
	private String assemblyName;
	private Integer type;
	//分总成
	private List<PartSubAssemblyDto> subAssemblyList;

	public String getAssemblyId() {
		return assemblyId;
	}

	public void setAssemblyId(String assemblyId) {
		this.assemblyId = assemblyId;
	}

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public List<PartSubAssemblyDto> getSubAssemblyList() {
		return subAssemblyList;
	}

	public void setSubAssemblyList(List<PartSubAssemblyDto> subAssemblyList) {
		this.subAssemblyList = subAssemblyList;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}
}

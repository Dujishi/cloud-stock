package com.xiaoka.cloud.stock.soa.api.epc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/12/7 14:21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarAssemblyDto implements Serializable {

	private static final long serialVersionUID = -4465785616702928468L;

	private String assemblyName;
	private List<CarSubAssemblyDto> subAssemblyDtoList;

	public String getAssemblyName() {
		return assemblyName;
	}

	public void setAssemblyName(String assemblyName) {
		this.assemblyName = assemblyName;
	}

	public List<CarSubAssemblyDto> getSubAssemblyDtoList() {
		return subAssemblyDtoList;
	}

	public void setSubAssemblyDtoList(List<CarSubAssemblyDto> subAssemblyDtoList) {
		this.subAssemblyDtoList = subAssemblyDtoList;
	}
}

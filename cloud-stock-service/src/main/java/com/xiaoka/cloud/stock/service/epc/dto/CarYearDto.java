package com.xiaoka.cloud.stock.service.epc.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2018/1/24 10:49
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CarYearDto implements Serializable {

	private static final long serialVersionUID = 2689997406675348113L;

	private String year;
	private List<CarModelWebDto> carModelList;

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<CarModelWebDto> getCarModelList() {
		return carModelList;
	}

	public void setCarModelList(List<CarModelWebDto> carModelList) {
		this.carModelList = carModelList;
	}
}

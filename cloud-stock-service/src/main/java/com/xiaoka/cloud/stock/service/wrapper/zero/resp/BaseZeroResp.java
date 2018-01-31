package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/12/13 17:16
 * @see [相关类/方法]
 * @since [版本号]
 */
public class BaseZeroResp<T> implements Serializable {

	private static final long serialVersionUID = -2438274647894538699L;
	private Integer code;
	private String msg;
	private int time;
	private String brand;
	private String img;
	private T data;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}
}

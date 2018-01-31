package com.xiaoka.cloud.stock.service.wrapper.zero.resp;

import java.io.Serializable;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2018/1/9 15:29
 * @see [相关类/方法]
 * @since [版本号]
 */
public class VinSearchZeroResp implements Serializable {

	private static final long serialVersionUID = -3807496961963985034L;

	private int code;
	private String brand;
	private List<String> mains;
	private String vin;
	private String imgname;
	private String imglogo;
	private String vins;
	private String name;
	private List<String> formats;
	private List<String> sub;
	private String cid;
	private String uri;
	private int time;
	private String msg;
	private String brandurl;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public List<String> getMains() {
		return mains;
	}

	public void setMains(List<String> mains) {
		this.mains = mains;
	}

	public String getVin() {
		return vin;
	}

	public void setVin(String vin) {
		this.vin = vin;
	}

	public String getImgname() {
		return imgname;
	}

	public void setImgname(String imgname) {
		this.imgname = imgname;
	}

	public String getImglogo() {
		return imglogo;
	}

	public void setImglogo(String imglogo) {
		this.imglogo = imglogo;
	}

	public String getVins() {
		return vins;
	}

	public void setVins(String vins) {
		this.vins = vins;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getFormats() {
		return formats;
	}

	public void setFormats(List<String> formats) {
		this.formats = formats;
	}

	public List<String> getSub() {
		return sub;
	}

	public void setSub(List<String> sub) {
		this.sub = sub;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getBrandurl() {
		return brandurl;
	}

	public void setBrandurl(String brandurl) {
		this.brandurl = brandurl;
	}
}

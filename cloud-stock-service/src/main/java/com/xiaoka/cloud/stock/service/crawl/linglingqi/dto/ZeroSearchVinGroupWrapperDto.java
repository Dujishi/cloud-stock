package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 根据VIN码查询主组数据实体
 * url : https://www.007vin.com/ppyvin/vingroup?code=volvo&vin=YV1FS40C3E2318156
 *
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroSearchVinGroupWrapperDto implements Serializable {
	private static final long serialVersionUID = -4032499750206745814L;

	private String code;

	private String msg;

	private List<ZeroSearchVinGroupDto> data;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<ZeroSearchVinGroupDto> getData() {
		return data;
	}

	public void setData(List<ZeroSearchVinGroupDto> data) {
		this.data = data;
	}
}

package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import java.io.Serializable;
import java.util.List;

/**
 * 查找替换件输出总对象
 * Created by sabo on 17/11/2017.
 */
public class GetReplacePartsRespList implements Serializable {

	private static final long serialVersionUID = 423295290640132703L;
	private List<GetReplacePartsRespListResult> result;
	private String desc;
	private String type;

	public List<GetReplacePartsRespListResult> getResult() {
		return result;
	}

	public void setResult(List<GetReplacePartsRespListResult> result) {
		this.result = result;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}

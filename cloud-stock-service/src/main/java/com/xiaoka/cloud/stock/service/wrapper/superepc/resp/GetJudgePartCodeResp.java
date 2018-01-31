package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/11/20 17:17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class GetJudgePartCodeResp implements Serializable {

	private static final long serialVersionUID = 8538135841835451915L;

	@JsonProperty("epc_no")
	private String epcNo;
	private String id;
	@JsonProperty("part_code")
	private String partCode;
	private String source;
	private String status;

	public String getEpcNo() {
		return epcNo;
	}

	public void setEpcNo(String epcNo) {
		this.epcNo = epcNo;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPartCode() {
		return partCode;
	}

	public void setPartCode(String partCode) {
		this.partCode = partCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}

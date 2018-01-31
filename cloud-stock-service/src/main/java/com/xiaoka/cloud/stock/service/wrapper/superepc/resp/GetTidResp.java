package com.xiaoka.cloud.stock.service.wrapper.superepc.resp;

import java.io.Serializable;

/**
 * Created by sabo on 16/11/2017.
 */
public class GetTidResp implements Serializable {

	private static final long serialVersionUID = 6223808588928400628L;
	/**
	 * 逗号分割的多个ID
	 */
	private String tid;

//	/**
//	 * 将逗号分割的多个tid转化为集合
//	 */
//	private List<Integer> tids;

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

//	public List<Integer> getTids() {
//		return tids;
//	}
//
//	public void setTids(List<Integer> tids) {
//		this.tids = tids;
//	}
}

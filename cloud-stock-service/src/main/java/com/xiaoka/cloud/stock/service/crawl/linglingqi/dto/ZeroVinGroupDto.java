package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ZeroVinGroupDto implements Serializable {
	private static final long serialVersionUID = -7638461108143837848L;

	/**
	 * 零零汽主组id
	 */
	private Integer groupId;
	/**
	 * 零零汽主组名称
	 */
	private String  groupName;

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
}

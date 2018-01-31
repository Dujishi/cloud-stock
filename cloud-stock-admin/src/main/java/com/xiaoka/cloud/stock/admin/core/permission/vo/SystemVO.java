/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: SystemVO.java
 * Author:   lansi
 * Date:    2017/6/15 16:54
 * Description:
 */
package com.xiaoka.cloud.stock.admin.core.permission.vo;

import com.xiaoka.ups.client.dto.ModuleDto;
import com.xiaoka.ups.client.dto.SystemDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * SystemVO
 *
 * @author 兰斯
 * @date 2017/6/15
 */
public class SystemVO implements Serializable {
	private static final long serialVersionUID = -5359085167251171802L;

	private String systemName;

	private String defaultPath;
	
	private UserVO user;

	private List<ModuleDto> children = new ArrayList<ModuleDto>();

	private List<SystemDto> systemDtos;

	public List<SystemDto> getSystemDtos() {
		return systemDtos;
	}

	public void setSystemDtos(List<SystemDto> systemDtos) {
		this.systemDtos = systemDtos;
	}

	public String getSystemName() {
		return systemName;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public UserVO getUser() {
		return user;
	}

	public void setUser(UserVO user) {
		this.user = user;
	}

	public String getDefaultPath() {
		return defaultPath;
	}

	public void setDefaultPath(String defaultPath) {
		this.defaultPath = defaultPath;
	}

	public List<ModuleDto> getChildren() {
		return children;
	}

	public void setChildren(List<ModuleDto> children) {
		this.children = children;
	}
}
/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: MenuVO.java
 * Author:   lansi
 * Date:    2017/5/18 16:30
 * Description:
 */
package com.xiaoka.cloud.stock.admin.core.permission.vo;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MenuVO {
	private String id;

	private String title;

	private String icon;

	private String url;

	private List<MenuVO> children = new ArrayList<>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	public void addChild(MenuVO menu){
		this.children.add(menu);
	}

	public List<MenuVO> getChildren() {
		return children;
	}

	public void setChildren(List<MenuVO> children) {
		this.children = children;
	}
}
/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: SystemVO.java
 * Author:   lansi
 * Date:    2017/6/15 16:54
 * Description:
 */
package com.xiaoka.cloud.stock.admin.core.permission.vo;

import java.io.Serializable;

/**
 * SystemVO
 *
 * @author 兰斯
 * @date 2017/6/15
 */
public class UserVO implements Serializable {

	private static final long serialVersionUID = -917608830092412866L;

	private String name;

	private String nickName;


	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

}
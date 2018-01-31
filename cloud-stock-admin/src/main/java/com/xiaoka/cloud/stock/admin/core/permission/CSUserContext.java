package com.xiaoka.cloud.stock.admin.core.permission;

import com.xiaoka.cloud.stock.admin.core.permission.vo.UserVO;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.ups.client.UpsClient;
import com.xiaoka.ups.client.dto.UserDto;

public class CSUserContext {

	/**
	 * 获取当前登录用户
	 */
	public static UserVO getUser(){
		UserDto userDto = Utils.get(UpsClient.class).getUser();
		UserVO user = new UserVO();
		user.setName(userDto.getName());
		user.setNickName(userDto.getNickName());
		return user;
	}
}

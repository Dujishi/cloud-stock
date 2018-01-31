package com.xiaoka.cloud.stock.service.crawl.linglingqi.dto;

import java.io.Serializable;

/**
 * 007请求帐号信息
 *
 * @author gancao 2017/12/25 14:51
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RequestAccountDto implements Serializable {

	private static final long serialVersionUID = -7895929690447624165L;

	//手机号
	private String phone;
	//密码
	private String password;
	//代理ip
	private String ip;
	//代理端口
	private String port;

}

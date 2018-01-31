package com.xiaoka.cloud.stock.web.user.param;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Do something
 *
 * @author gancao 2017/11/15 15:03
 * @see [相关类/方法]
 * @since [版本号]
 */
public class LoginParam implements Serializable{

	private static final long serialVersionUID = 8918738185931507730L;

	//手机号
	@NotNull(message = "手机号不能为空")
	private String phone;
	//密码
	@NotNull(message = "密码不能为空")
	private String password;
	//验证码
	private String code;

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

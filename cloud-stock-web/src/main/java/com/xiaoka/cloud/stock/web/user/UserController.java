package com.xiaoka.cloud.stock.web.user;

import com.xiaoka.cloud.stock.service.core.annotation.SessionPermission;
import com.xiaoka.cloud.stock.service.supplier.UserService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.user.param.LoginParam;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/13 16:00
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class UserController {

	private Logger logger = LoggerFactory.getLogger(UserController.class);

	@Resource
	private UserService userService;

	@RequestMapping(value = "/user/modify/pswd", method = RequestMethod.POST)
	@SessionPermission(check = false)
	public @ResponseBody
	String modifyPassword(@RequestBody @Valid LoginParam param) {
		if (userService.modifyPassword(param.getPhone(), param.getPassword(), param.getCode())) {
			return ApiResultWrapper.success("");
		}
		return ApiResultWrapper.fail("-1", "密码重置失败");
	}

	@RequestMapping(value = "/user/code/get", method = RequestMethod.GET)
	@SessionPermission(check = false)
	public @ResponseBody
	String getCheckCode(@RequestParam String phone) {
		return ApiResultWrapper.success(userService.getCode(phone));
	}

	@RequestMapping(value = "/user/erp/isErpAuthorized", method = RequestMethod.GET)
	public @ResponseBody
	String isErpAuthorized(HttpServletRequest request) {
		if (Objects.nonNull(SessionUtil.getUser(request))) {
			return ApiResultWrapper.success("");
		}
		CloudSupplierUserDto user = SessionUtil.getUser(request);
		return ApiResultWrapper.success(userService.isErpAuthorized(user.getPhone()));
	}
}

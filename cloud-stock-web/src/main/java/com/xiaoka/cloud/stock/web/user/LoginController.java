package com.xiaoka.cloud.stock.web.user;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.service.core.annotation.SessionPermission;
import com.xiaoka.cloud.stock.service.supplier.UserService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.dto.ErpCollectStrategyDto;
import com.xiaoka.cloud.stock.web.user.param.LoginParam;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/13 13:42
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class LoginController {

	@Resource
	private UserService userService;

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@SessionPermission(check = false)
	@ResponseBody
	public String doLogin(@RequestBody @Valid LoginParam param, HttpServletRequest request) {
		CloudSupplierUserDto cloudSupplierUserDto = userService.doLogin(param.getPhone(), param.getPassword());
		SessionUtil.login(request, cloudSupplierUserDto);
		//读取当前配件商的采集策略
		ErpCollectStrategyDto erpCollectStrategyDto = userService.getCompanyStrategy(cloudSupplierUserDto.getCompanyId());
		return ApiResultWrapper.success(JSON.toJSONString(erpCollectStrategyDto));
	}

	@RequestMapping(value = "/have/login", method = RequestMethod.GET)
	@SessionPermission(check = false)
	@ResponseBody
	public String haveLogin(HttpServletRequest request) {
		if (Objects.nonNull(SessionUtil.getUser(request))) {
			return ApiResultWrapper.success("");
		}
		return ApiResultWrapper.fail("-1", "");
	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	@ResponseBody
	public String doLogout(HttpServletRequest request) {
		SessionUtil.logout(request);
		return ApiResultWrapper.success("");
	}

}

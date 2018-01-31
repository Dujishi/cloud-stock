package com.xiaoka.cloud.stock.web.common;

import com.xiaoka.cloud.stock.service.core.annotation.IpCheck;
import com.xiaoka.cloud.stock.service.core.annotation.SessionPermission;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.Objects;

/**
 * @author zhouze
 * @date 2017/11/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class HashController {

	@RequestMapping(value = "/hashString")
	@SessionPermission(check = false)
	@IpCheck(check = false)
	public @ResponseBody
	String hashString(String param, Integer mol) {
		if (StringUtils.isBlank(param)) {
			return ApiResultWrapper.success("");
		}
		Integer val = param.hashCode();
		Integer mor = null;
		if (null != mol) {
			mor = Math.abs(val % mol);
		}
		return ApiResultWrapper.success(String.format("原值:%s,hash值:%s,取模的值:%s", param, val, mor));
	}

	@RequestMapping(value = "/hashInt")
	@SessionPermission(check = false)
	@IpCheck(check = false)
	public @ResponseBody
	String hashInt(Integer param, Integer mol) {
		if (null == param) {
			return ApiResultWrapper.success("");
		}
		Integer val = param.hashCode();
		Integer mor = null;
		if (null != mol) {
			mor = Math.abs(val % mol);
		}
		return ApiResultWrapper.success(String.format("原值:%s,hash值:%s,取模的值:%s", param, val, mor));
	}

}

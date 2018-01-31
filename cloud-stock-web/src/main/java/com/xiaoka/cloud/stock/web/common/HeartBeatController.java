package com.xiaoka.cloud.stock.web.common;

import com.xiaoka.cloud.stock.service.core.annotation.IpCheck;
import com.xiaoka.cloud.stock.service.core.annotation.SessionPermission;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * 应用监控检测
 *
 * @author gancao 2017/11/13 14:12
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class HeartBeatController {

	@RequestMapping(value = "/health/alive")
	@SessionPermission(check = false)
	@IpCheck(check = false)
	public @ResponseBody String Heartbeat() {
		return ApiResultWrapper.success(new Date());
	}

}

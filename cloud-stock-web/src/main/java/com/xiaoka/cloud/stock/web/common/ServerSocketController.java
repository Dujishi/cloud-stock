package com.xiaoka.cloud.stock.web.common;

import com.xiaoka.cloud.stock.service.core.annotation.IpCheck;
import com.xiaoka.cloud.stock.service.core.annotation.SessionPermission;
import com.xiaoka.cloud.stock.service.server.core.mapping.ClientNamesMapping;
import com.xiaoka.cloud.stock.service.server.dto.ErpCollectDataDto;
import com.xiaoka.cloud.stock.service.server.dto.TransferDto;
import com.xiaoka.cloud.stock.service.server.service.ProcessErpCollectService;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author zhouze
 * @date 2017/12/26
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class ServerSocketController {

	@Resource
	ProcessErpCollectService processErpCollectService;

	@RequestMapping(value = "/server/testIt", method = RequestMethod.GET)
	@SessionPermission(check = false)
	@IpCheck(check = false)
	public @ResponseBody
	String testIt(String param) {
		if (StringUtils.isBlank(param) || !Objects.equals(param, "socket")) {
			return ApiResultWrapper.success("");
		}
		TransferDto transferDto = new TransferDto();
		transferDto.setChannel(ClientNamesMapping.mapData().get(1));
		transferDto.setHeaders(null);
		transferDto.setType("GET");
		transferDto.setUrl("https://cs.ddyc.com/health/alive");
		ErpCollectDataDto erp = processErpCollectService.doneCollectDataFromErp(ClientNamesMapping.mapData().get(1), transferDto);
		if (null == erp) {
			return "";
		}
		return erp.getResponse();
	}

}

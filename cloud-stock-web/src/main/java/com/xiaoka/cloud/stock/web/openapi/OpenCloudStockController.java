package com.xiaoka.cloud.stock.web.openapi;

import com.xiaoka.cloud.stock.service.core.annotation.ApiSignAuth;
import com.xiaoka.cloud.stock.service.core.util.RequestUtils;
import com.xiaoka.cloud.stock.service.open.StockOpenApiService;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiConstants;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author zhouze
 * @date 2017/11/13
 * @see [相关类/方法]
 * @since [版本号]
 */
@Controller
public class OpenCloudStockController {

	@Resource
	StockOpenApiService stockOpenApiService;

	@RequestMapping(value = "/openApi/sync/initData", method = RequestMethod.POST)
	@ResponseBody
	@ApiSignAuth(true)
	public String initData(@RequestBody SupplierSyncParam supplierSyncParam, HttpServletRequest request) {
		// 获取请求的AppId
		String appId = RequestUtils.getParamVal(request, OpenApiConstants.PARAM_APPID);

		stockOpenApiService.initData(supplierSyncParam, appId);
		return OpenApiReturnCodeEnum.SUCCESS.asResponse();
	}

	@RequestMapping(value = "/openApi/sync/bulkData", method = RequestMethod.POST)
	@ResponseBody
	@ApiSignAuth(true)
	public String bulkData(@RequestBody SupplierSyncParam supplierSyncParam, HttpServletRequest request) {
		// 获取请求的AppId
		String appId = RequestUtils.getParamVal(request, OpenApiConstants.PARAM_APPID);

		stockOpenApiService.bulkData(supplierSyncParam, appId);
		return OpenApiReturnCodeEnum.SUCCESS.asResponse();
	}
}

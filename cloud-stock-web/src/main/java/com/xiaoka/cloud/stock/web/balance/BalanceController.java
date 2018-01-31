package com.xiaoka.cloud.stock.web.balance;

import com.xiaoka.cloud.stock.service.balance.BalanceService;
import com.xiaoka.cloud.stock.service.balance.dto.BalanceDto;
import com.xiaoka.cloud.stock.service.balance.dto.CsBankInfoDto;
import com.xiaoka.cloud.stock.service.balance.dto.CsCityAndBankDto;
import com.xiaoka.cloud.stock.service.balance.dto.ExtractRequestDto;
import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierUserService;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
public class BalanceController {

	private Logger logger = LoggerFactory.getLogger(BalanceController.class);

	@Resource
	BalanceService balanceService;

	@Resource
	private CloudSupplierUserService cloudSupplierUserService;

	@RequestMapping(value = "/balance/myBalance", method = RequestMethod.GET)
	@ResponseBody
	public String myBalance() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		BalanceDto balanceDto = balanceService.myBalance(cloudSupplierUserDto);
		return ApiResultWrapper.success(balanceDto);
	}

	@RequestMapping(value = "/balance/getCityAndBank", method = RequestMethod.GET)
	@ResponseBody
	public String getCityAndBank() {
//		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		CsCityAndBankDto csCityAndBankDto = balanceService.getCityAndBank();
		return ApiResultWrapper.success(csCityAndBankDto);
	}

	@RequestMapping(value = "/balance/myBalanceDetails", method = RequestMethod.GET)
	@ResponseBody
	public String myBalanceDetails(Integer pageNumber) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		Map<String, Object> result = balanceService.myBalanceDetails(cloudSupplierUserDto, pageNumber);
		return ApiResultWrapper.success(result);
	}

	@RequestMapping(value = "/balance/extractValidate", method = RequestMethod.POST)
	@ResponseBody
	public String extractValidate() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		Map<String, Object> result = balanceService.extractValidate(cloudSupplierUserDto.getSupplierId(), cloudSupplierUserDto.getPhone());
		return ApiResultWrapper.success(result);
	}

	@RequestMapping(value = "/balance/extract", method = RequestMethod.POST)
	@ResponseBody
	public String extract(@RequestBody ExtractRequestDto extractRequestDto) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		balanceService.extract(extractRequestDto, cloudSupplierUserDto);
		return ApiResultWrapper.success(null);
	}

	@RequestMapping(value = "/balance/sendSettlementCode", method = RequestMethod.POST)
	@ResponseBody
	public String sendSettlementCode() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		balanceService.sendSettlementCode(cloudSupplierUserDto.getPhone());
		return ApiResultWrapper.success(null);
	}

	@RequestMapping(value = "/balance/getBankInfo", method = RequestMethod.GET)
	@ResponseBody
	public String getBankInfo() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		CsBankInfoDto csBankInfoDto = balanceService.getBankInfo(cloudSupplierUserDto);
		return ApiResultWrapper.success(csBankInfoDto);
	}

	@RequestMapping(value = "/balance/updateBankInfo", method = RequestMethod.POST)
	@ResponseBody
	public String updateBankInfo(@RequestBody CsBankInfoDto csBankInfoDto) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		if(StringUtils.isBlank(csBankInfoDto.getBankNo()) || !csBankInfoDto.getBankNo().matches("\\d+")){
			throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, "银行卡号不合法");
		}
		balanceService.updateBankInfo(csBankInfoDto, cloudSupplierUserDto);
		return ApiResultWrapper.success(null);
	}


	@RequestMapping(value = "/balance/sendBankEditSms", method = RequestMethod.POST)
	@ResponseBody
	public String sendBankEditSms() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		checkPermissionWithSuperAdmin(cloudSupplierUserDto);

		balanceService.sendBankEditSms(cloudSupplierUserDto.getPhone());
		return ApiResultWrapper.success(null);
	}

	private void checkPermissionWithSuperAdmin(CloudSupplierUserDto cloudSupplierUserDto) {
		logger.info("当前用户不是超级管理员! user=={}", Jackson.mobile().writeValueAsString(cloudSupplierUserDto));
		AssertUtil.assertTrueWithApiException(cloudSupplierUserService.isSuperAdmin(cloudSupplierUserDto.getId()), "当前用户不是超级管理员！");
	}
}

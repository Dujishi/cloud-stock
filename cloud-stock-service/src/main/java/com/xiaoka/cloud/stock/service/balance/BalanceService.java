package com.xiaoka.cloud.stock.service.balance;

import com.xiaoka.cloud.stock.service.balance.dto.*;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;
import java.util.Map;

public interface BalanceService {
	BalanceDto myBalance(CloudSupplierUserDto cloudSupplierUserDto);

	Map<String, Object> myBalanceDetails(CloudSupplierUserDto cloudSupplierUserDto, Integer pageNumber);

	Map<String,Object> extractValidate(Integer supplierId, String phone);

	void sendBankEditSms(String phone);

	void sendSettlementCode(String phone);

	CsBankInfoDto getBankInfo(CloudSupplierUserDto cloudSupplierUserDto);

	void extract(ExtractRequestDto extractRequestDto, CloudSupplierUserDto cloudSupplierUserDto);

	void updateBankInfo(CsBankInfoDto csBankInfoDto, CloudSupplierUserDto cloudSupplierUserDto);

	CsCityAndBankDto getCityAndBank();
}

package com.xiaoka.cloud.stock.service.balance.impl;

import com.xiaoka.cloud.stock.service.balance.BalanceService;
import com.xiaoka.cloud.stock.service.balance.dto.*;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.help.page.PageList;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.stmt.soa.bank.BankInfoSoaService;
import com.xiaoka.stmt.soa.bank.RoleType;
import com.xiaoka.stmt.soa.bank.intput.BankInfoDto;
import com.xiaoka.stmt.soa.bank.output.CityAndBankDto;
import com.xiaoka.stmt.soa.supplier.StmtSupplierAccountSoaService;
import com.xiaoka.stmt.soa.supplier.input.SupplierBalanceDetailSoaParam;
import com.xiaoka.stmt.soa.supplier.output.SupplierAccountSoaDTO;
import com.xiaoka.stmt.soa.supplier.output.SupplierBalanceDetailSoaDTO;
import com.xiaoka.stmt.soa.withdraw.WithdrawSoaService;
import com.xiaoka.stmt.soa.withdraw.intput.WithdrawCheckDto;
import com.xiaoka.stmt.soa.withdraw.intput.WithdrawRequestDto;
import com.xiaoka.stmt.soa.withdraw.output.WithdrawCheckResultDto;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BalanceServiceImpl implements BalanceService {

	private Logger logger = LoggerFactory.getLogger(BalanceServiceImpl.class);

	private static final String HIDDEN_ASTRISK = "*";

	private static final int KEEP_LENGTH = 4;

	@Resource
	private StmtSupplierAccountSoaService stmtSupplierAccountSoaService;

	@Resource
	private BankInfoSoaService bankInfoSoaService;

	@Resource
	private WithdrawSoaService withdrawSoaService;

	@Override public BalanceDto myBalance(CloudSupplierUserDto cloudSupplierUserDto) {
		logger.info("invoke myBalance=={}", Jackson.mobile().writeValueAsString(cloudSupplierUserDto));
		BalanceDto balanceDto = new BalanceDto();
		balanceDto.setSupplierName(cloudSupplierUserDto.getSupplierName());
		balanceDto.setPhone(cloudSupplierUserDto.getPhone());
		SupplierAccountSoaDTO supplierAccountSoaDTO = stmtSupplierAccountSoaService.getAccountAmount(cloudSupplierUserDto.getSupplierId());
		if (supplierAccountSoaDTO != null) {
			balanceDto.setRealSubAmount(supplierAccountSoaDTO.getRealSubAmount());
			balanceDto.setWithdrawAmount(supplierAccountSoaDTO.getWithdrawAmount());
		}
		CsBankInfoDto csBankInfoDto = getBankInfo(cloudSupplierUserDto);
		if (csBankInfoDto != null) {
			balanceDto.setBankNo(astriskBanNo(csBankInfoDto.getBankNo()));
			balanceDto.setBankBranch(csBankInfoDto.getBankName());
		}
		return balanceDto;
	}

	private String astriskBanNo(String bankNo) {
		if (StringUtils.isBlank(bankNo)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		final int length = bankNo.length();
		for(int i=0; i<length; i++) {
			if (i >= KEEP_LENGTH && i < length - KEEP_LENGTH) {
				sb.append(HIDDEN_ASTRISK);
			} else {
				sb.append(bankNo.charAt(i));
			}
		}
		return sb.toString();
	}

	@Override public Map<String, Object> myBalanceDetails(CloudSupplierUserDto cloudSupplierUserDto, Integer pageNumber) {
		logger.info("invoke myBalanceDetails=={}", Jackson.mobile().writeValueAsString(cloudSupplierUserDto));
		SupplierBalanceDetailSoaParam supplierBalanceDetailSoaParam = new SupplierBalanceDetailSoaParam();
		supplierBalanceDetailSoaParam.setOriginSupplierId(cloudSupplierUserDto.getSupplierId());
		supplierBalanceDetailSoaParam.setPage(pageNumber);
		supplierBalanceDetailSoaParam.setPageSize(20);

		Map<String, Object> result = new HashMap<>();
		PageList<SupplierBalanceDetailSoaDTO> supplierBalanceDetailSoaDTOPageList = stmtSupplierAccountSoaService.pageListBalanceDetail(supplierBalanceDetailSoaParam);
		if (supplierBalanceDetailSoaDTOPageList != null && CollectionUtils.isNotEmpty(supplierBalanceDetailSoaDTOPageList.getData())) {
			result.put("data", BeanUtils.transformList(BalanceDetailDto.class, supplierBalanceDetailSoaDTOPageList.getData()));
			result.put("total", supplierBalanceDetailSoaDTOPageList.getTotalSize());
		} else {
			result.put("data", null);
			result.put("total", 0);
		}
		return result;
	}

	@Override public Map<String, Object> extractValidate(Integer supplierId, String phone) {
		WithdrawCheckDto withdrawCheckDto = new WithdrawCheckDto();
		withdrawCheckDto.setPhone(phone);
		withdrawCheckDto.setRoleId(supplierId);
		withdrawCheckDto.setRoleType(RoleType.UNIT_PROVIDER);
		Map<String, Object> extractValidateResult = new HashMap<>();
		WithdrawCheckResultDto withdrawCheckResultDto = withdrawSoaService.prewWithDrawCheck(withdrawCheckDto);
		extractValidateResult.put("phone", phone);
		if (withdrawCheckResultDto.getMaxWithdrawAmt() != null && BigDecimal.ZERO.compareTo(withdrawCheckResultDto.getMaxWithdrawAmt()) <= 0) {
			extractValidateResult.put("extractable", true);
			extractValidateResult.put("extractableBalance", withdrawCheckResultDto.getMaxWithdrawAmt());
		} else {
			throw new ApiException("", "暂无可提现金额");
		}
		return extractValidateResult;
	}

	@Override public void sendBankEditSms(String phone) {
		bankInfoSoaService.sendBankEditSms(phone);
	}

	@Override public void sendSettlementCode(String phone) {
		withdrawSoaService.sendSettlementCode(phone);
	}

	@Override public CsBankInfoDto getBankInfo(CloudSupplierUserDto cloudSupplierUserDto) {
		BankInfoDto bankInfoDto = bankInfoSoaService.getBankInfo(String.valueOf(cloudSupplierUserDto.getSupplierId()), RoleType.UNIT_PROVIDER);
		if (bankInfoDto == null) {
			bankInfoDto = new BankInfoDto();
		}
		bankInfoDto.setPhone(cloudSupplierUserDto.getPhone());
		return BeanUtils.transform(CsBankInfoDto.class, bankInfoDto);
	}

	@Override public void extract(ExtractRequestDto extractRequestDto, CloudSupplierUserDto cloudSupplierUserDto) {
		extractRequestDto.setPhone(cloudSupplierUserDto.getPhone());
		extractRequestDto.setRoleId(cloudSupplierUserDto.getSupplierId());
		extractRequestDto.setRoleType(RoleType.UNIT_PROVIDER);
		WithdrawRequestDto withdrawRequestDto = BeanUtils.transform(WithdrawRequestDto.class, extractRequestDto);

		withdrawSoaService.withDraw(withdrawRequestDto);
	}

	@Override public void updateBankInfo(CsBankInfoDto csBankInfoDto, CloudSupplierUserDto cloudSupplierUserDto) {
		csBankInfoDto.setRoleId(String.valueOf(cloudSupplierUserDto.getSupplierId()));
		csBankInfoDto.setRoleType(RoleType.UNIT_PROVIDER);
		csBankInfoDto.setPhone(cloudSupplierUserDto.getPhone());
		BankInfoDto bankInfoDto = BeanUtils.transform(BankInfoDto.class, csBankInfoDto);
		bankInfoSoaService.saveBankInfoWithSmsCheck(bankInfoDto);
	}

	@Override public CsCityAndBankDto getCityAndBank() {
		CityAndBankDto cityAndBankDto = bankInfoSoaService.getCityAndBank();
		return BeanUtils.transform(CsCityAndBankDto.class, cityAndBankDto);
	}
}

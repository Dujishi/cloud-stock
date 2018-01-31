package com.xiaoka.cloud.stock.service.balance.impl;

import com.xiaoka.cloud.stock.service.balance.BalanceService;
import com.xiaoka.cloud.stock.service.balance.dto.*;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.container.ContainerConstant;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context-pay.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
})
public class BalanceServiceImplTest extends ContainerTest{

	@Resource
	private BalanceService balanceService;

	// 初始化初始变量：运行环境、运行模式、运行分区名、运行分区的应用集合
	@BeforeClass
	public static void beforeClass() {
		System.setProperty(ContainerConstant.APP_ENV, "int");
		// System.setProperty(ContainerConstant.APP_RUN_MODE, "server");
		System.setProperty(ContainerConstant.PROJECT_ZONE_NAME, "develop-pay");
		System.setProperty(ContainerConstant.PROJECT_ZONE_APPS, "stmt");
	}

	@Test
	public void myBalance() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setSupplierId(32825);
		cloudSupplierUserDto.setPhone("15356650909");
		cloudSupplierUserDto.setName("萨博");

		BalanceDto balanceDto = balanceService.myBalance(cloudSupplierUserDto);
		System.out.println(Jackson.mobile().writeValueAsString(balanceDto));
	}

	@Test
	public void myBalanceDetails() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setSupplierId(32825);
		cloudSupplierUserDto.setPhone("15356650909");
		cloudSupplierUserDto.setName("萨博");
		Map<String, Object> result = balanceService.myBalanceDetails(cloudSupplierUserDto, 1);
		System.out.println(Jackson.mobile().writeValueAsString(result));
	}

	@Test
	public void extractValidate() throws Exception {
		Map<String,Object> map = balanceService.extractValidate(32825, "15356650909");
		System.out.println(Jackson.mobile().writeValueAsString(map));
	}

	@Test
	public void sendBankEditSms() throws Exception {
		balanceService.sendBankEditSms("15356650909");
	}

	@Test
	public void sendSettlementCode() throws Exception {
		balanceService.sendSettlementCode("15356650909");
	}

	@Test
	public void getBankInfo() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setSupplierId(32825);
		cloudSupplierUserDto.setPhone("15356650909");
		cloudSupplierUserDto.setName("萨博");

		CsBankInfoDto csBankInfoDto = balanceService.getBankInfo(cloudSupplierUserDto);
		System.out.println(Jackson.mobile().writeValueAsString(csBankInfoDto));
	}

	@Test
	public void extract() throws Exception {
		ExtractRequestDto extractRequestDto = new ExtractRequestDto();
		extractRequestDto.setSmsCode("8017");
		extractRequestDto.setWithdrawAmount(new BigDecimal(1));

		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setSupplierId(32825);
		cloudSupplierUserDto.setPhone("15356650909");
		cloudSupplierUserDto.setName("萨博");

		balanceService.extract(extractRequestDto, cloudSupplierUserDto);
	}

	@Test
	public void updateBankInfo() throws Exception {
		CsBankInfoDto csBankInfoDto = new CsBankInfoDto();
		csBankInfoDto.setPhone("15356650909");
		csBankInfoDto.setBankBranch("中国建设银行股份有限公司石林支行");
		csBankInfoDto.setBankCity("杭州");
		csBankInfoDto.setBankCityCode("571");
		csBankInfoDto.setBankType("1003");
		csBankInfoDto.setBankName("中国建设银行");
		csBankInfoDto.setBankNo("6217003860010624250");
		csBankInfoDto.setBankUser("李春");
		csBankInfoDto.setBankAreaCode("12");
		csBankInfoDto.setBankAccType(1);
		csBankInfoDto.setSmsCode("1590");

		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setSupplierId(32825);
		cloudSupplierUserDto.setPhone("15356650909");
		cloudSupplierUserDto.setName("萨博");

		balanceService.updateBankInfo(csBankInfoDto, cloudSupplierUserDto);
	}

	@Test
	public void getCityAndBank() throws Exception {
		CsCityAndBankDto csCityAndBankDto = balanceService.getCityAndBank();
		System.out.println(Jackson.mobile().writeValueAsString(csCityAndBankDto));
	}

}
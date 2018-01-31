package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.core.order.CsCustomerHintRepo;
import com.xiaoka.cloud.stock.core.order.entity.CsCustomerHintEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.order.CustomerHintService;
import com.xiaoka.cloud.stock.service.order.dto.CustomerHintDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CustomerHintServiceImpl implements CustomerHintService{

	@Resource
	IndentHelper indentHelper;

	@Resource
	CsCustomerHintRepo csCustomerHintRepo;

	@Override public List<CustomerHintDto> getCustomerHintList(CloudSupplierUserDto cloudSupplierUserDto, String customerName) {
		CsCustomerHintEntity CsCustomerHintEntityParam = new CsCustomerHintEntity();
		CsCustomerHintEntityParam.setCustomerName(customerName);
		CsCustomerHintEntityParam.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		List<CsCustomerHintEntity> csCustomerHintEntityList = csCustomerHintRepo.select(CsCustomerHintEntityParam);
		return BeanUtils.transformList(CustomerHintDto.class, csCustomerHintEntityList);
	}
}

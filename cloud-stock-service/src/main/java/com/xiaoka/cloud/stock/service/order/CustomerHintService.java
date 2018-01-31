package com.xiaoka.cloud.stock.service.order;

import com.xiaoka.cloud.stock.service.order.dto.CustomerHintDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;

public interface CustomerHintService {

	List<CustomerHintDto> getCustomerHintList(CloudSupplierUserDto cloudSupplierUserDto, String customerName);
}

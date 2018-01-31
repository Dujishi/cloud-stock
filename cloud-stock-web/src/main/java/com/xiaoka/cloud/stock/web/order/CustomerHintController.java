package com.xiaoka.cloud.stock.web.order;

import com.xiaoka.cloud.stock.service.order.CustomerHintService;
import com.xiaoka.cloud.stock.service.order.dto.CustomerHintDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class CustomerHintController {

	@Resource
	CustomerHintService customerHintService;

	@RequestMapping(value = "/customer/getCustomerHintList", method = RequestMethod.GET)
	@ResponseBody
	public String getCustomerHintList(String customerName) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		List<CustomerHintDto> indentDtoList = customerHintService.getCustomerHintList(cloudSupplierUserDto, customerName);
		return ApiResultWrapper.success(indentDtoList);
	}
}

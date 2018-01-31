package com.xiaoka.cloud.stock.web.order;

import com.xiaoka.cloud.stock.service.order.OrderService;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * 处理云仓订单相关的页面请求
 */
@Controller
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(OrderController.class);

	@Resource
	OrderService orderService;

	@RequestMapping(value = "/order/getOrderList", method = RequestMethod.GET)
	@ResponseBody
	public String getOrderList(String queryStr, Integer orderStatus, Integer pageNumber) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		Map<String, Object> result = orderService.getOrderList(cloudSupplierUserDto, queryStr, orderStatus, pageNumber, null);
		return ApiResultWrapper.success(result);
	}

	@RequestMapping(value = "/order/getOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public String getOrderDetail(@RequestParam  String orderNo) {
		if (StringUtils.isBlank(orderNo)) {
			throw new ApiException("", "订单编号不能为空");
		}
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		OrderDto orderDto = orderService.getOrderDetail(cloudSupplierUserDto, orderNo);
		return ApiResultWrapper.success(orderDto);
	}

	@RequestMapping(value = "/order/modifyOrder", method = RequestMethod.POST)
	@ResponseBody
	public String modifyOrder(@RequestBody OrderDto orderDto) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		String indentNo = orderService.modifyOrder(cloudSupplierUserDto, orderDto.getOrderNo());
		return ApiResultWrapper.success(indentNo);
	}

	@RequestMapping(value = "/order/deleteOrder", method = RequestMethod.POST)
	@ResponseBody
	public String deleteOrder(@RequestBody OrderDto orderDto) {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		orderService.deleteOrder(cloudSupplierUserDto, orderDto.getOrderNo());
		return ApiResultWrapper.success(null);
	}

//	@RequestMapping(value = "/order/copyOrderH5", method = RequestMethod.POST)
//	@ResponseBody
//	public String copyOrderH5(@RequestBody String orderNo) {
//		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
//		String url = orderService.copyOrderH5(cloudSupplierUserDto, orderNo);
//		return ApiResultWrapper.success(url);
//	}

}

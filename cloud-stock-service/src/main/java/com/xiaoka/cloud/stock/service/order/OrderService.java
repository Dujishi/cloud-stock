package com.xiaoka.cloud.stock.service.order;

import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;

import java.util.List;
import java.util.Map;

public interface OrderService {

	/**
	 * 根据配送单创建订单
	 * @param cloudSupplierUserDto
	 * @param indentDto
	 * @return
	 */
	String createOrder(CloudSupplierUserDto cloudSupplierUserDto, IndentDto indentDto);

	/**
	 * 根据订单单号、用户名称、联系电话、订单状态查询订单列表
	 * @param cloudSupplierUserDto
	 * @param queryStr
	 * @param orderStatus
	 * @return
	 */
	Map<String, Object> getOrderList(CloudSupplierUserDto cloudSupplierUserDto, String queryStr, Integer orderStatus, Integer pageNumber, Integer pageSize);

	/**
	 * 根据订单编号，查询订单详情
	 * @param cloudSupplierUserDto
	 * @param orderNo
	 * @return
	 */
	OrderDto getOrderDetail(CloudSupplierUserDto cloudSupplierUserDto, String orderNo);

	OrderDto getOrderById(Integer orderId);

	OrderDto getOrderDetailForPay(String payUrlCode);

	/**
	 * 根据订单编号修改订单
	 * @param cloudSupplierUserDto
	 * @param orderNo
	 * @return
	 */
	String modifyOrder(CloudSupplierUserDto cloudSupplierUserDto, String orderNo);

	/**
	 * 根据订单编号删除订单，同时删除配货单
	 * @param cloudSupplierUserDto
	 * @param orderNo
	 */
	void deleteOrder(CloudSupplierUserDto cloudSupplierUserDto, String orderNo);

	/**
	 * 复制订单h5地址，只有点击过复制的h5才能被公网直接访问
	 * @param cloudSupplierUserDto
	 * @param orderNo
	 * @return
	 */
	String copyOrderH5(CloudSupplierUserDto cloudSupplierUserDto, String orderNo);

	void updateStatusToPaid(Integer orderId);
}

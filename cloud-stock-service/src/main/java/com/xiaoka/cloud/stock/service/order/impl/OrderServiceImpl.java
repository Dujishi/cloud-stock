package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.core.order.CsIndentRepo;
import com.xiaoka.cloud.stock.core.order.CsOrderPartRepo;
import com.xiaoka.cloud.stock.core.order.CsOrderRepo;
import com.xiaoka.cloud.stock.core.order.entity.CsIndentEntity;
import com.xiaoka.cloud.stock.core.order.entity.CsOrderEntity;
import com.xiaoka.cloud.stock.core.order.entity.CsOrderPartEntity;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.mqmessage.producer.CsOrderStatusChangeNotifyService;
import com.xiaoka.cloud.stock.service.order.*;
import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.service.order.dto.OrderPartDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	private static final Integer PAGE_SIZE = 20;

	@Resource
	CsOrderRepo csOrderRepo;

	@Resource
	CsIndentRepo csIndentRepo;

	@Resource
	CsOrderPartRepo csOrderPartRepo;

	@Resource
	IndentService indentService;

	@Resource
	IndentHelper indentHelper;

	@Resource
	CsOrderStatusChangeNotifyService csOrderStatusChangeNotifyService;

	@Transactional
	@Override
	public String createOrder(CloudSupplierUserDto cloudSupplierUserDto, IndentDto indentDto) {
		indentService.saveIndentDetail(cloudSupplierUserDto, indentDto);

		IndentDto indentDtoResult = indentService.getIndentDetail(cloudSupplierUserDto, indentDto.getIndentNo());

		//1. 根据 indentDtoResult创建 orderEntity
		CsOrderEntity csOrderEntity = BeanUtils.transform(CsOrderEntity.class, indentDtoResult);
		List<CsOrderPartEntity> csOrderPartEntityList = BeanUtils.transformList(CsOrderPartEntity.class, indentDtoResult.getIndentPartDtoList());
		BigDecimal total = BigDecimal.ZERO;
		for (CsOrderPartEntity csOrderPartEntity : csOrderPartEntityList) {
			if (csOrderPartEntity.getSubtotal() == null) {
				logger.error("csOrderPartEntity.getSubtotal() is null {}", Jackson.mobile().writeValueAsString(csOrderPartEntity));
				continue;
			}
			total = total.add(csOrderPartEntity.getSubtotal());
		}
		csOrderEntity.setId(null);
		csOrderEntity.setIsValid(true);
		csOrderEntity.setStatus(OrderStatus.Prepay.getCode());
		csOrderEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csOrderEntity.setSalesmanName(cloudSupplierUserDto.getName());
		csOrderEntity.setCreateBy(cloudSupplierUserDto.getName());
		csOrderEntity.setTotal(BigDecimalUtil.scaleOf(total));
		csOrderEntity.setPayPrice(BigDecimalUtil.scaleOf(total.subtract(csOrderEntity.getDiscountPrice())));
		csOrderEntity.setPayUrlCode(indentHelper.getPayUrlCode());
		csOrderRepo.insert(csOrderEntity);

		CsOrderEntity csOrderEntityParam = new CsOrderEntity();
		csOrderEntityParam.setId(csOrderEntity.getId());
		csOrderEntityParam.setOrderNo(indentHelper.getOrderNo(csOrderEntity.getId()));
		csOrderRepo.updateBySelective(csOrderEntityParam);

		//2 根据indentDtoResult.getIndentPartDtoList() 创建CsOrderPartEntity

		for (CsOrderPartEntity csOrderPartEntity : csOrderPartEntityList) {
			csOrderPartEntity.setCsIndentPartId(csOrderPartEntity.getId());
			csOrderPartEntity.setId(null);
			csOrderPartEntity.setOrderNo(csOrderEntityParam.getOrderNo());
			csOrderPartEntity.setIsValid(true);
			csOrderPartEntity.setCreateBy(cloudSupplierUserDto.getName());
			csOrderPartRepo.insert(csOrderPartEntity);
		}

		//3 更新配货单的状态
		indentService.updateStatusByIndentNo(indentDtoResult.getIndentNo(), IndentStatus.Prepay.getCode());
		return csOrderEntityParam.getOrderNo();
	}

	@Override public Map<String, Object> getOrderList(CloudSupplierUserDto cloudSupplierUserDto, String queryStr, Integer orderStatus,
			Integer pageNumber, Integer pageSize) {

		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("salesmanId", cloudSupplierUserDto.getId());
		paramMap.put("sellerCode", indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		paramMap.put("status", orderStatus);
		if (pageSize == null) {
			paramMap.put("start", (pageNumber - 1) * PAGE_SIZE);
			paramMap.put("offset", PAGE_SIZE);
		} else {
			paramMap.put("start", (pageNumber - 1) * pageSize);
			paramMap.put("offset", pageSize);
		}
		if (StringUtils.isNotBlank(queryStr)) {
			paramMap.put("queryStr", queryStr);
		}
		List<CsOrderEntity> csOrderEntityList = csOrderRepo.selectByQueryStr(paramMap);
		Map<String, Object> result = new HashMap<>();
		List<OrderDto> orderDtoList = BeanUtils.transformList(OrderDto.class, csOrderEntityList);
		if (CollectionUtils.isNotEmpty(csOrderEntityList)) {
			List<String> orderNos = csOrderEntityList.stream().map(CsOrderEntity::getOrderNo).collect(Collectors.toList());
			List<CsOrderPartEntity> csOrderAmountList = csOrderPartRepo.statAmountByOrderNos(orderNos);
			orderDtoList.stream().forEach(orderDto -> {
				if (CollectionUtils.isEmpty(csOrderAmountList)) {
					orderDto.setAmount(BigDecimalUtil.scaleOf(BigDecimal.ZERO));
				} else {
					CsOrderPartEntity csOrderAmountEntity = csOrderAmountList.stream().filter(csOrderAmount -> Objects
							.equals(csOrderAmount.getOrderNo(), orderDto.getOrderNo())).findFirst().orElse(null);
					orderDto.setAmount(BigDecimalUtil.scaleOf(csOrderAmountEntity == null ? BigDecimal.ZERO : csOrderAmountEntity.getAmount()));
				}
			});
		}
		result.put("data", orderDtoList);
		result.put("total", csOrderRepo.countByQueryStr(paramMap));
		return result;
	}

	@Override public OrderDto getOrderDetail(CloudSupplierUserDto cloudSupplierUserDto, String orderNo) {

		CsOrderEntity csOrderEntity = new CsOrderEntity();
		csOrderEntity.setOrderNo(orderNo);
		csOrderEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csOrderEntity.setIsValid(true);
		csOrderEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		List<CsOrderEntity> csOrderEntityList = csOrderRepo.select(csOrderEntity);
		if (CollectionUtils.isEmpty(csOrderEntityList)) {
			throw new ApiException("", "无效的订单编码");
		}
		OrderDto orderDtoResult = BeanUtils.transform(OrderDto.class, csOrderEntityList.get(0));

		CsOrderPartEntity csOrderPartEntityParam = new CsOrderPartEntity();
		csOrderPartEntityParam.setOrderNo(orderDtoResult.getOrderNo());
		csOrderPartEntityParam.setIsValid(true);
		List<OrderPartDto> orderPartDtoList = BeanUtils.transformList(OrderPartDto.class, csOrderPartRepo.select(csOrderPartEntityParam));
		orderDtoResult.setOrderPartDtoList(orderPartDtoList);

		initDeliverAddressForOrderDto(orderDtoResult);

		return orderDtoResult;
	}


	@Override public OrderDto getOrderById(Integer orderId) {

		CsOrderEntity csOrderEntity = new CsOrderEntity();
		csOrderEntity.setId(orderId);
		csOrderEntity.setIsValid(true);
		List<CsOrderEntity> csOrderEntityList = csOrderRepo.select(csOrderEntity);
		if (CollectionUtils.isEmpty(csOrderEntityList)) {
			throw new ApiException("", "无效的订单编码");
		}
		OrderDto orderDtoResult = BeanUtils.transform(OrderDto.class, csOrderEntityList.get(0));

		return orderDtoResult;
	}

	@Override public OrderDto getOrderDetailForPay(String payUrlCode) {

		CsOrderEntity csOrderEntity = new CsOrderEntity();
		csOrderEntity.setPayUrlCode(payUrlCode);
		csOrderEntity.setIsValid(true);
		List<CsOrderEntity> csOrderEntityList = csOrderRepo.select(csOrderEntity);
		if (CollectionUtils.isEmpty(csOrderEntityList)) {
			throw new ApiException("", "无效的订单编码");
		}
		OrderDto orderDtoResult = BeanUtils.transform(OrderDto.class, csOrderEntityList.get(0));

		CsOrderPartEntity csOrderPartEntityParam = new CsOrderPartEntity();
		csOrderPartEntityParam.setOrderNo(orderDtoResult.getOrderNo());
		csOrderPartEntityParam.setIsValid(true);
		List<OrderPartDto> orderPartDtoList = BeanUtils.transformList(OrderPartDto.class, csOrderPartRepo.select(csOrderPartEntityParam));
		orderDtoResult.setOrderPartDtoList(orderPartDtoList);
		initDeliverAddressForOrderDto(orderDtoResult);

		return orderDtoResult;
	}

	private void initDeliverAddressForOrderDto(OrderDto orderDto) {
		CsIndentEntity csIndentEntityParam = new CsIndentEntity();
		csIndentEntityParam.setIndentNo(orderDto.getIndentNo());
		csIndentEntityParam.setIsValid(true);
		List<CsIndentEntity> csIndentEntityList = csIndentRepo.select(csIndentEntityParam);
		if (CollectionUtils.isEmpty(csIndentEntityList)) {
			throw new ApiException("", "无效的配货单编码");
		}
		CsIndentEntity csIndentEntity = csIndentEntityList.get(0);
		orderDto.setProvince(csIndentEntity.getProvince());
		orderDto.setCity(csIndentEntity.getCity());
		orderDto.setDistrict(csIndentEntity.getDistrict());
		orderDto.setAddress(csIndentEntity.getAddress());
	}

	@Transactional
	@Override public String modifyOrder(CloudSupplierUserDto cloudSupplierUserDto, String orderNo) {
		CsOrderEntity csOrderEntity = new CsOrderEntity();
		csOrderEntity.setOrderNo(orderNo);
		csOrderEntity.setSalesmanId(cloudSupplierUserDto.getId());
		csOrderEntity.setIsValid(true);
		csOrderEntity.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		List<CsOrderEntity> csOrderEntityList = csOrderRepo.select(csOrderEntity);
		if (CollectionUtils.isEmpty(csOrderEntityList)) {
			throw new ApiException("", "无效的订单编码");
		}
		String indentNO = csOrderEntityList.get(0).getIndentNo();
		indentService.updateStatusByIndentNo(indentNO, IndentStatus.Created.getCode());
		deleteOrder(cloudSupplierUserDto, orderNo);
		return indentNO;
	}

	@Override public void deleteOrder(CloudSupplierUserDto cloudSupplierUserDto, String orderNo) {
		//TODO 校验权限
		CsOrderEntity csOrderEntityParam = new CsOrderEntity();
		csOrderEntityParam.setSellerCode(indentHelper.getSellerCode(cloudSupplierUserDto.getSupplierId()));
		csOrderEntityParam.setOrderNo(orderNo);
		csOrderEntityParam.setSalesmanId(cloudSupplierUserDto.getId());
		if (csOrderRepo.delete(csOrderEntityParam) <= 0) {
			throw new ApiException("", "删除订单失败");
		}
	}

	@Override public String copyOrderH5(CloudSupplierUserDto cloudSupplierUserDto, String orderNo) {
		return null;
	}

	private void updateOrderToStatus(Integer orderId, Integer status) {
		//TODO 增加日志
		CsOrderEntity csOrderEntityParam = new CsOrderEntity();
		csOrderEntityParam.setId(orderId);
		csOrderEntityParam.setStatus(status);
		csOrderRepo.updateBySelective(csOrderEntityParam);
	}

	@Override
	public void updateStatusToPaid(Integer orderId) {
		updateOrderToStatus(orderId, OrderStatus.Paid.getCode());
		csOrderStatusChangeNotifyService.notifyFinished(orderId);
	}
}

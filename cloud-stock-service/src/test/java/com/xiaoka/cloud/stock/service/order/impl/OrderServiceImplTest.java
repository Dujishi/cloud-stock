package com.xiaoka.cloud.stock.service.order.impl;

import com.xiaoka.cloud.stock.service.order.BigDecimalUtil;
import com.xiaoka.cloud.stock.service.order.IndentService;
import com.xiaoka.cloud.stock.service.order.OrderService;
import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.IndentPartDto;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context-order.xml"
})
public class OrderServiceImplTest extends ContainerTest{


	@Resource
	OrderService orderService;

	@Resource
	IndentService indentService;

	@Test
	public void getOrderDetail() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(1);
		cloudSupplierUserDto.setSupplierId(32825);
		System.out.println(TestUtil.success(orderService.getOrderDetail(cloudSupplierUserDto, "DD100051")));
	}

	@Test
	public void getOrderDetailForPay() throws Exception {
		System.out.println(TestUtil.success(orderService.getOrderDetailForPay("9398486e723745489ddf13649e10a4f1")));
	}

	@Test
	public void modifyOrder() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		System.out.println(TestUtil.success(orderService.modifyOrder(cloudSupplierUserDto, "DD100000")));
	}

	@Test
	public void deleteOrder() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		orderService.deleteOrder(cloudSupplierUserDto, "DD100000");
		System.out.println(TestUtil.success(null));
	}

	@Test
	public void copyOrderH5() throws Exception {
	}


	@Test
	public void createOrder() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		List<IndentDto> indentDtoList = indentService.getIndentList(cloudSupplierUserDto);
		IndentDto indentDtoParam = new IndentDto();
		//		indentDtoParam.setId(indentDtoList.get(0).getId());
		indentDtoParam.setIndentNo(indentDtoList.get(0).getIndentNo());
		indentDtoParam.setName("奥迪");
		indentDtoParam.setCustomerName("荣意汽修");
		indentDtoParam.setContact("王经理");
		indentDtoParam.setContactPhone("13588888888");
		indentDtoParam.setVin("WP1AG2921ELA11593");
		indentDtoParam.setProvince("浙江省");
		indentDtoParam.setCity("杭州市");
		indentDtoParam.setDistrict("西湖区");
		indentDtoParam.setAddress("文一西路512号");
		indentDtoParam.setCarModel("保时捷卡宴");
		indentDtoParam.setDiscountPrice(BigDecimalUtil.scaleOf(new BigDecimal(0)));
		indentDtoParam.setIndentPartDtoList(new ArrayList<>());

		IndentDto indentDtoInDb = indentService.getIndentDetail(cloudSupplierUserDto, indentDtoParam.getIndentNo());
		List<IndentPartDto> indentPartDtoInDbList = indentDtoInDb.getIndentPartDtoList();
		for(IndentPartDto indentPartDtoInDb : indentPartDtoInDbList){
			IndentPartDto indentPartDto = new IndentPartDto();
			indentPartDto.setId(indentPartDtoInDb.getId());
			indentPartDto.setUnitPrice(indentPartDtoInDb.getUnitPrice());
			indentPartDto.setAmount(indentPartDtoInDb.getAmount());
			indentDtoParam.getIndentPartDtoList().add(indentPartDto);
		}
//		indentDtoParam.getIndentPartDtoList().remove(0);
		System.out.println(Jackson.mobile().writeValueAsString(indentDtoParam));
		IndentDto indentDto = indentService.saveIndentDetail(cloudSupplierUserDto, indentDtoParam);
		System.out.println(Jackson.mobile().writeValueAsString(indentDtoParam));
		System.out.println(TestUtil.success(orderService.createOrder(cloudSupplierUserDto, indentDto)));
	}

	@Test
	public void getOrderList() throws Exception {
		CloudSupplierUserDto cloudSupplierUserDto = new CloudSupplierUserDto();
		cloudSupplierUserDto.setId(666666);
		cloudSupplierUserDto.setSupplierId(11111);
		Map<String, Object> orderDtoList = orderService.getOrderList(cloudSupplierUserDto, null, null, 1, null);
		System.out.println(TestUtil.success(orderDtoList));

		orderDtoList = orderService.getOrderList(cloudSupplierUserDto, "13588888887", null, 1, null);
		System.out.println(TestUtil.success(orderDtoList));

		orderDtoList = orderService.getOrderList(cloudSupplierUserDto, "13588888887", 3000, 1, null);
		System.out.println(TestUtil.success(orderDtoList));
	}

	public static void main(String[] args) {
		String s = "06020433137281354019=YX58cWJcjrMrYkyne5EJCZF5ZTJ2XrJmXGPZ6Ajre882bFYtC6; ASPSESSIONIDCADBBBRC=JNILDHPBOGFLMJHGEGFDOIKC; ASPSESSIONIDACAABASA=EKFNFIGBPOKLBHELEFDDAGEK; ASPSESSIONIDCAABCCQA=LJBNMPBCGAGMAELKHEIFIKGO; __guid=118320526.2701608163924503600.1514428871075.7378; ASPSESSIONIDCCDCBCSB=GMOPGOMBGLKPPPJFFHCBIDOM";

	}

}
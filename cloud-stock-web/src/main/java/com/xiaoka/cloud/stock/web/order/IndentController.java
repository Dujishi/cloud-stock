package com.xiaoka.cloud.stock.web.order;

import com.xiaoka.cloud.stock.service.order.IndentService;
import com.xiaoka.cloud.stock.service.order.OrderService;
import com.xiaoka.cloud.stock.service.order.dto.IndentDto;
import com.xiaoka.cloud.stock.service.order.dto.IndentPartDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.cloud.stock.web.util.SessionUtil;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 云仓配货单相关的请求
 */
@Controller
public class IndentController {

	@Resource
	IndentService indentService;

	@Resource
	OrderService orderService;

	@RequestMapping(value = "/indent/getIndentList", method = RequestMethod.GET)
	@ResponseBody
	public String getIndentList() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		List<IndentDto> indentDtoList = indentService.getIndentList(cloudSupplierUserDto);
		return ApiResultWrapper.success(indentDtoList);
	}

	@RequestMapping(value = "/indent/getIndentDetail", method = RequestMethod.GET)
	@ResponseBody
	public String getIndentDetail(@RequestParam String indentNo) {
		if (StringUtils.isBlank(indentNo)) {
			throw new ApiException("", "配货单号不能为空");
		}
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		IndentDto indentDto = indentService.getIndentDetail(cloudSupplierUserDto, indentNo);
		return ApiResultWrapper.success(indentDto);
	}

	@RequestMapping(value = "/indent/saveIndentDetail", method = RequestMethod.POST)
	@ResponseBody
	public String saveIndentDetail(@RequestBody IndentDto indentDto) {
		if (StringUtils.isBlank(indentDto.getCustomerName())) {
			throw new ApiException("", "公司名称不能为空");
		}
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		IndentDto indentDtoResult = indentService.saveIndentDetail(cloudSupplierUserDto, indentDto);
		return ApiResultWrapper.success(indentDtoResult);
	}

	@RequestMapping(value = "/indent/addIndentPart", method = RequestMethod.POST)
	@ResponseBody
	public String addIndentPart(@RequestBody IndentPartDto indentPartDto) {
		//配货单单号，oe码，零件名称不允许为空
		if (StringUtils.isBlank(indentPartDto.getIndentNo())) {
			throw new ApiException("", "配货单单号不能为空");
		}
		if (StringUtils.isBlank(indentPartDto.getOeNo())) {
			throw new ApiException("", "OE码不能为空");
		}
//		if (StringUtils.isBlank(indentPartDto.getPartName())) {
//			throw new ApiException("", "零件名称不能为空");
//		}
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		IndentPartDto indentPartResult = indentService.addIndentPart(cloudSupplierUserDto, indentPartDto);
		return ApiResultWrapper.success(indentPartResult);
	}


	@RequestMapping(value = "/indent/addIndent", method = RequestMethod.POST)
	@ResponseBody
	public String addIndent() {
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		IndentDto indentDto = indentService.addIndent(cloudSupplierUserDto, null);
		return ApiResultWrapper.success(indentDto);
	}

	@RequestMapping(value = "/indent/updateIndentName", method = RequestMethod.POST)
	@ResponseBody
	public String updateIndentName(@RequestBody IndentDto indentDto) {
		if (StringUtils.isBlank(indentDto.getName())) {
			throw new ApiException("", "配货单名称不能为空");
		}
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		indentService.updateIndentName(cloudSupplierUserDto, indentDto.getIndentNo(), indentDto.getName());
		return ApiResultWrapper.success(null);
	}

	@RequestMapping(value = "/indent/deleteIndent", method = RequestMethod.POST)
	@ResponseBody
	public String deleteIndent(@RequestBody IndentDto indentDto) {
		if (StringUtils.isBlank(indentDto.getIndentNo())) {
			throw new ApiException("", "配货单号不能为空！");
		}
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		indentService.deleteIndent(cloudSupplierUserDto, indentDto.getIndentNo());
		return ApiResultWrapper.success(null);
	}

	@RequestMapping(value = "/order/submitIndentDetail", method = RequestMethod.POST)
	@ResponseBody
	public String submitIndentDetail(@RequestBody IndentDto indentDto) {
//		if(StringUtils.isBlank(indentDto.get))
		CloudSupplierUserDto cloudSupplierUserDto = SessionUtil.getCurrentUser();
		String orderNo = orderService.createOrder(cloudSupplierUserDto, indentDto);
		return ApiResultWrapper.success(orderNo);
	}


}

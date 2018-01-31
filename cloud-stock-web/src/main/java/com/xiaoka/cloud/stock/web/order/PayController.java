package com.xiaoka.cloud.stock.web.order;

import com.xiaoka.cloud.stock.service.order.OrderService;
import com.xiaoka.cloud.stock.service.order.dto.OrderDto;
import com.xiaoka.cloud.stock.web.util.ApiResultWrapper;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.pay.center.api.dto.notify.PayNotifyDto;
import com.xiaoka.pay.center.api.enums.PayMethodEnum;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

@Controller
public class PayController {

	private Logger logger = LoggerFactory.getLogger(PayController.class);

	@Resource OrderService orderService;

	@RequestMapping(value = "/pay/getOrderDetail", method = RequestMethod.GET)
	@ResponseBody
	public String getOrderDetailForPay(@RequestParam String payUrlCode) {
		if (StringUtils.isBlank(payUrlCode)) {
			throw new ApiException("", "订单编号不能为空");
		}
		OrderDto orderDto = orderService.getOrderDetailForPay(payUrlCode);
		return ApiResultWrapper.success(orderDto);
	}

	/**
	 * <pre>
	 * 功能描述:
	 * 网关通知养车系统支付成功
	 *
	 * @param payNotifyDto 支付通知DTO
	 * @return success-成功; 其它字符-失败
	 */
	@RequestMapping(value = "/centerpay/notify/3.5", method = RequestMethod.POST)
	public @ResponseBody String notify(@RequestBody PayNotifyDto payNotifyDto) {

		// 记录回调信息
		logger.info("centerpay:{}", Jackson.base().writeValueAsString(payNotifyDto));

		// 空值校验
		if (null == payNotifyDto || (null == payNotifyDto.getOrderId() && null == payNotifyDto.getOrderIdList())) {
			logger.error("centerpay:notify is null ");
			return "fail";
		}

		// 业务处理
		try {
			if(Objects.equals(PayMethodEnum.UN_BOX_PAY.getCode(), payNotifyDto.getPayMethod())
					|| Objects.equals(PayMethodEnum.BoxPay.getCode(), payNotifyDto.getPayMethod())
					|| Objects.equals(PayMethodEnum.BOX_PAY_MERCHANT.getCode(), payNotifyDto.getPayMethod())){
				//TODO
			}else{
				//TODO
			}
		} catch (Exception e) {
			logger.error("centerpay notify error", e);
			return "fail";
		}

		return "success";
	}
}

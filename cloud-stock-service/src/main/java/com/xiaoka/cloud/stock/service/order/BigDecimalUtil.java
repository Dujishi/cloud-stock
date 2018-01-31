package com.xiaoka.cloud.stock.service.order;

import java.math.BigDecimal;

public class BigDecimalUtil {

	public static BigDecimal scaleOf(BigDecimal bigDecimal) {
		bigDecimal.setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal;
	}
}

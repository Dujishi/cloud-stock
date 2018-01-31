package com.xiaoka.cloud.stock.soa.api.pay;

import com.xiaoka.cloud.stock.soa.api.pay.dto.PayNotifyDto;

public interface PayNotifySoaService {

	String notify(PayNotifyDto payNotifyDto);
}

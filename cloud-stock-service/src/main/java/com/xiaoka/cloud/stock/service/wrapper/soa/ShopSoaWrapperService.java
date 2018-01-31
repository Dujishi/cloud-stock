package com.xiaoka.cloud.stock.service.wrapper.soa;

import com.xiaoka.shop.care.soa.api.shop.ShopCareSoaService;
import com.xiaoka.shop.care.soa.api.shop.result.ShopResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 商家SOA服务包装类
 *
 * @author gancao 2017/11/14 14:10
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ShopSoaWrapperService {

	private static final Logger logger = LoggerFactory.getLogger(ShopSoaWrapperService.class);

	@Resource
	private ShopCareSoaService shopCareSoaService;

	public ShopResult searchShopById(Integer shopId){
		try {
			return shopCareSoaService.getShopById(shopId);
		}catch (Exception e){
			logger.error("调com.xiaoka.shop.care.soa.api.shop.ShopCareSoaService.getShopById服务报错", e);
		}
		return null;
	}
}

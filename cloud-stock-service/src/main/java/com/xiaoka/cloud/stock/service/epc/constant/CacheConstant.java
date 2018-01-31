package com.xiaoka.cloud.stock.service.epc.constant;

/**
 * Do something
 *
 * @author gancao 2017/12/1 14:52
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CacheConstant {

	//车型相关缓存时间三分钟
	int MODEL_EXPIRE_TIME = 180;
	//配件相关缓存时间五分钟
	int PART_EXPIRE_TIME = 300;
	//EPC的接口半个小时缓存
	int EPC_EXPIRE_TIME = 1800;
}

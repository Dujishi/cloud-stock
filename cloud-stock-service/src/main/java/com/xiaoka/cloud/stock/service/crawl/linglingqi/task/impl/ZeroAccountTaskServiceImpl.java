package com.xiaoka.cloud.stock.service.crawl.linglingqi.task.impl;

import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.service.crawl.core.util.ZeroSevenLoginUtil;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.manger.ZeroAccountManagerService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.manger.constant.ManagerTypeEnum;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroRedisService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.ZeroAccountTaskService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.ZeroDataCrawlTaskService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;
import com.xiaoka.mid.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/12/28 17:52
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("zeroAccountTaskService")
@Task
public class ZeroAccountTaskServiceImpl implements ZeroAccountTaskService {

	private static final Logger logger = LoggerFactory.getLogger(ZeroDataCrawlTaskService.class);

	@Resource
	private ZeroRedisService zeroRedisService;
	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	private ZeroAccountManagerService zeroAccountManagerService;

	@Override
	public void execute() {
		//检测异常IP帐号
		logger.info("开始执行异常帐号检测....");
		Map<Integer, AccountMsgDto> errorMap =  zeroRedisService.getAccountMap(ManagerTypeEnum.待检测帐号.getType());
		if (Objects.nonNull(errorMap) && !errorMap.isEmpty()){
			Map<Integer, AccountMsgDto> copyMap = Maps.newHashMap();
			copyMap.putAll(errorMap);
			copyMap.forEach((k ,v) -> {
				//检测请求是否可调用
				int retryTimes = 0;
				boolean result;
				do {
					result = ZeroSevenLoginUtil.checkValid(v);
				}while (++retryTimes < 3 && !result);//请求失败
				//帐号IP有效重新
				zeroRedisService.removeAccount(k, ManagerTypeEnum.待检测帐号.getType());
				if (result){
					logger.info("帐号:{}经检测有效...");
					zeroRedisService.putAccount(k, v, ManagerTypeEnum.有效帐号.getType());
				}else {
					// TODO: 2018/1/10  ip无效重新分配一个代理ip
					logger.info("帐号:{}经检测无效去除代理ip...");
					zeroChooseService.updateErrorIp(v.getZeroAccountEntity().getId());
					zeroAccountManagerService.sendEmailMessage(v, 1);
				}
			});
		}
	}


}

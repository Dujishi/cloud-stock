package com.xiaoka.cloud.stock.service.core.util;

import com.xiaoka.freework.help.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;


/**
 * 重试操作
 *
 * @author zhouze
 * @date 2017/11/22
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RetryOperateUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(RetryOperateUtil.class);

	/**
	 * 重试操作
	 *
	 * @param retryCall  业务函数
	 * @param retryCount 重试次数
	 */
	public static void doRetry(RetryCall retryCall, Integer retryCount) {
		int flag = 0;
		for (; ; ) {
			try {
				flag++;
				if (flag <= retryCount) {
					retryCall.retry();
				} else {
					LOGGER.info("超过尝试次数，不再进行尝试");
					throw new ApiException("1", "服务器繁忙");
				}
				return;
			} catch (DuplicateKeyException e) {
				LOGGER.info("尝试执行第{}次失败", flag);
			}
		}
	}

	@FunctionalInterface
	public interface RetryCall {

		/**
		 * 重试
		 */
		void retry();

	}

	private RetryOperateUtil() {
	}
}

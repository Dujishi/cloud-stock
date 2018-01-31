package com.xiaoka.cloud.stock.service.crawl.linglingqi.manger;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.service.core.util.HttpUtil;
import com.xiaoka.cloud.stock.service.crawl.core.util.ZeroSevenLoginUtil;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.manger.constant.ManagerTypeEnum;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.BaseZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroChooseService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroRedisService;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.json.Jackson;
import com.xiaoka.freework.utils.lock.ClusterLock;
import com.xiaoka.platform.api.tool.sms.message.SendMessageService;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Do something
 *
 * @author gancao 2017/12/28 17:44
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class ZeroAccountManagerService {

	private static final Logger logger = LoggerFactory.getLogger(ZeroAccountManagerService.class);

	private static final String ZERO_URL = "/zero/url";
	private static final int maxRetryTimes = 3;
	private static final int retrySleepMillis = 500;
	private static final int WAIT_TIME = 1000;
	@Resource
	private ZeroChooseService zeroChooseService;
	@Resource
	private ZeroRedisService zeroRedisService;
	@Resource
	private SendMessageService sendMessageService;

	public String execute(String uri, boolean isLock, boolean retry, CloudSupplierUserDto cloudSupplierUserDto) {
		AccountMsgDto accountMsgDto = this.getAccountMsgDto(cloudSupplierUserDto);
		if (Objects.isNull(accountMsgDto) || Objects.isNull(accountMsgDto.getZeroAccountEntity()) || StringUtils
				.isBlank(accountMsgDto.getZeroAccountEntity().getPhone())) {
			return null;
		}
		if (isLock){//请求是否支持并发
			ClusterLock.Locker locker = Utils.get(ClusterLock.class).transiantLock(ZERO_URL, accountMsgDto.getZeroAccountEntity().getPhone());
			try {
				//同一个帐号同一时间只能有一个请求访问
				if (locker.acquire(WAIT_TIME, TimeUnit.SECONDS)) {//锁时间10s
					return locker.execute(() -> executeRequest(uri, retry, cloudSupplierUserDto));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			return executeRequest(uri, retry,cloudSupplierUserDto);
		}
		return null;
	}

	private String executeRequest(String uri, boolean retry, CloudSupplierUserDto cloudSupplierUserDto) {
		int retryTimes = 0;
		if (!retry){
			//不重试，只执行一次
			try {
				return executeInternal(uri, cloudSupplierUserDto);
			}catch (Exception e){
				logger.error("007url:{}执行出错", uri, e);
				return null;
			}
		}
		do {
			try {
				return executeInternal(uri, cloudSupplierUserDto);
			} catch (ApiException e) {
				String errorCode = e.getErrCode();
				/**
				 * 0 请求失败重试,IP异常的，重刷三次无效帐号移除到待检测管理中
				 * -1 重新请求, 500ms后重试
				 * -2 帐号异常
				 */
				AccountMsgDto accountMsgDto = this.getAccountMsgDto(cloudSupplierUserDto);
				if ("0".equals(errorCode)) {
					int sleepMillis = retrySleepMillis * (1 << retryTimes);
					try {
						logger.info("请求异常，{}ms 后重试(第{}次)", sleepMillis, retryTimes + 1);
						Thread.sleep(sleepMillis);
						if (retryTimes == 2) {
							//已重试3次，将帐号信息移除
							this.removeValidRedisAccount(this.getAccountMsgDto(cloudSupplierUserDto));
							return null;
						}
					} catch (InterruptedException e1) {
						throw new RuntimeException(e1);
					}
				} else if ("-1".equals(errorCode)) {
					//请求失败
					logger.info("登录异常，重试(第{}次)", retryTimes + 1);
				} else if ("-2".equals(errorCode)) {
					//帐号异常
					this.setInvalidAccount(accountMsgDto);
					this.sendEmailMessage(accountMsgDto, 2);
					return null;
				}else if ("-3".equals(errorCode)) {
					//帐号未付费
					this.setInvalidAccount(accountMsgDto);
					this.sendEmailMessage(accountMsgDto, 3);
					return null;
				}
			} catch (Exception e) {
				logger.info("007执行请求出错", e);
				return null;
			}
		} while (++retryTimes < maxRetryTimes);
		return null;
	}

	private String executeInternal(String uri, CloudSupplierUserDto cloudSupplierUserDto) {
		Integer type = 0;//返回类型
		AccountMsgDto accountMsgDto = this.getAccountMsgDto(cloudSupplierUserDto);
		if (Objects.isNull(accountMsgDto) || Objects.isNull(accountMsgDto.getZeroAccountEntity()) || StringUtils
				.isBlank(accountMsgDto.getZeroAccountEntity().getPhone())) {
			return null;
		}
		BaseZeroResp zeroResp = null;
		String response = null;
		try {
			//uri = ZeroUrlUtil.authEnCode(uri);
			response = HttpUtil.doRespString(uri, accountMsgDto,cloudSupplierUserDto);
			if (StringUtils.isNotBlank(response)) {
				logger.info("supplierId:{},url:{}的请求结果:{}", accountMsgDto.getZeroAccountEntity().getSupplierId(), uri, response);
				zeroResp = Jackson.base().readValue(response, new TypeReference<BaseZeroResp>() {
				});
			}
		}catch (IOException e){
			//IO异常，代理IP出问题
			type = -1;
			logger.info("请求出现异常", e);
		} catch (Exception e) {
			response = null;
			logger.info("请求出现异常", e);
		}
		if (Objects.nonNull(zeroResp)) {
			type = zeroResp.getCode();
		}
		if (Objects.equals(type, -1)) {
			//请求异常重试
			throw new ApiException("0", "请求异常");
		} else if (Objects.equals(type, 401) || Objects.equals(type, 400)) {
			//登录信息异常重新登录一次
			logger.error("帐号:{}请求异常,{}", accountMsgDto.getZeroAccountEntity().getPhone(), Objects.nonNull(zeroResp) ? zeroResp.getMsg() : null);
			Header[] headers = ZeroSevenLoginUtil.singleInstance().getHeaders(false, accountMsgDto.getZeroAccountEntity());
			this.putAccount(accountMsgDto.getZeroAccountEntity(), headers);
			throw new ApiException("-1", "登录异常");
		} else if (Objects.equals(type, 0) && Objects.nonNull(zeroResp) && StringUtils.isNotBlank(zeroResp.getMsg()) &&
				zeroResp.getMsg().contains("安全隐患")) {
			//帐号异常存在安全隐患,并移除设置无效
			logger.error("帐号:{}异常,{}", accountMsgDto.getZeroAccountEntity().getPhone(), zeroResp.getMsg());
			throw new ApiException("-2", "帐号已被封");
		}else if (Objects.equals(type, -999) && Objects.nonNull(zeroResp)){
			//帐号没有查询权限
			logger.error("帐号:{}未付费,{}", accountMsgDto.getZeroAccountEntity().getPhone(), zeroResp.getMsg());
			throw new ApiException("-3", "帐号没有查询权限");
		}
		return response;
	}

	private AccountMsgDto getAccountMsgDto(CloudSupplierUserDto cloudSupplierUserDto) {
		AccountMsgDto accountMsgDto = null;
		if (Objects.nonNull(cloudSupplierUserDto)) {
			//先查询有效帐号，没有再查待检测帐号
			accountMsgDto = zeroRedisService.getAccountBySupplierId(cloudSupplierUserDto.getSupplierId(), ManagerTypeEnum.有效帐号.getType());
			if (Objects.isNull(accountMsgDto)) {
				accountMsgDto = zeroRedisService.getAccountBySupplierId(cloudSupplierUserDto.getSupplierId(), ManagerTypeEnum.待检测帐号.getType());
				if (Objects.isNull(accountMsgDto)) {
					//redis中帐号信息为空从DB中取数据
					ZeroAccountEntity zeroAccountEntity = zeroChooseService.getZeroAccountEntityBySupplierId(cloudSupplierUserDto.getSupplierId());
					if (Objects.nonNull(zeroAccountEntity)) {
						Header[] headers = ZeroSevenLoginUtil.singleInstance().getHeaders(false, zeroAccountEntity);
						accountMsgDto = this.putAccount(zeroAccountEntity, headers);
					}
				} else {
					//帐号IP异常，暂时不能访问
					logger.info("supplierId:{}存在异常帐号...", cloudSupplierUserDto.getSupplierId());
					return null;
				}
			}
			if (Objects.isNull(accountMsgDto)){
				logger.info("supplierId:{}没有有效的可执行帐号信息...", cloudSupplierUserDto.getSupplierId());
			}
		}
		return accountMsgDto;
	}

	private AccountMsgDto putAccount(ZeroAccountEntity entity, Header[] headers) {
		if (ArrayUtils.isEmpty(headers) || Objects.isNull(entity)) {
			return null;
		}
		Map<String, String> map = ZeroSevenLoginUtil.getCookiesMap(headers);
		if (!map.isEmpty() && Objects.nonNull(entity.getSupplierId())) {
			AccountMsgDto accountMsgDto = new AccountMsgDto();
			accountMsgDto.setCookieMap(map);
			accountMsgDto.setZeroAccountEntity(entity);
			zeroRedisService.putAccount(entity.getSupplierId(), accountMsgDto, ManagerTypeEnum.有效帐号.getType());
			return accountMsgDto;
		}
		return null;
	}

	private void removeValidRedisAccount(AccountMsgDto accountMsgDto) {
		if (Objects.isNull(accountMsgDto)){
			return;
		}
		zeroRedisService.removeAccount(accountMsgDto.getZeroAccountEntity().getSupplierId(), ManagerTypeEnum.有效帐号.getType());
		zeroRedisService.putAccount(accountMsgDto.getZeroAccountEntity().getSupplierId(), accountMsgDto, ManagerTypeEnum.待检测帐号.getType());
	}

	private void setInvalidAccount(AccountMsgDto accountMsgDto){
		if (Objects.isNull(accountMsgDto)){
			return;
		}
		//移除redis
		zeroRedisService.removeAccount(accountMsgDto.getZeroAccountEntity().getSupplierId(), ManagerTypeEnum.有效帐号.getType());
		//设置帐号无效
		ZeroAccountEntity zeroAccountEntity = accountMsgDto.getZeroAccountEntity();
		zeroAccountEntity.setIsValid(0);
		zeroChooseService.updateZeroAccountEntity(zeroAccountEntity);
	}

	public void sendEmailMessage(AccountMsgDto accountMsgDto, int type) {
		if (Objects.isNull(accountMsgDto)){
			return;
		}
		ZeroAccountEntity accountEntity = accountMsgDto.getZeroAccountEntity();
		String errMailContent = "帐号:" + accountEntity.getPhone();
		if (StringUtils.isNotBlank(accountEntity.getIp()) && Objects.nonNull(accountEntity.getPort())) {
			errMailContent += ",代理IP:" + accountEntity.getIp() + ":" + accountEntity.getPort();
		}
		if (type == 1) {
			errMailContent += ",IP异常请检查";
		} else if (type == 2) {
			errMailContent += ",帐号异常被封已设置无效状态";
		} else if (type == 3) {
			errMailContent += ",帐号未付费已设置无效状态";
		}
		String emailPath = "gancao@xiaokakeji.com," +
				"sabo@xiaokakeji.com," +
				"chuhan@xiaokakeji.com," +
				"gangbeng@xiaokakeji.com";
		String titleStr = "007帐号管理异常提示！";
		String level = "紧急";
		boolean result = sendMessageService.sendMail(emailPath, titleStr, level, errMailContent, null);
		if (!result) {
			logger.error("邮件发送失败");
		}
	}


}

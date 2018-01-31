package com.xiaoka.cloud.stock.service.supplier.impl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudCompanyStrategyAuthorizedRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudCompanyStrategyPropertyRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudCompanyStrategyRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudCompanyStrategyAuthorizedEntity;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudCompanyStrategyEntity;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudCompanyStrategyPropertyEntity;
import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.smsmessage.SendSmsMessageService;
import com.xiaoka.cloud.stock.service.smsmessage.constant.SmsStatusEnum;
import com.xiaoka.cloud.stock.service.smsmessage.constant.SmsTypeEnum;
import com.xiaoka.cloud.stock.service.smsmessage.dto.param.SaveSmsMessageParam;
import com.xiaoka.cloud.stock.service.smsmessage.dto.result.SmsMessageResult;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierUserService;
import com.xiaoka.cloud.stock.service.supplier.UserService;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.cloud.stock.service.supplier.dto.ErpCollectStrategyDto;
import com.xiaoka.cloud.stock.service.wrapper.soa.MemberSoaWrapperService;
import com.xiaoka.freework.container.context.AppEnvironment;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.platform.api.user.dictionary.AppType;
import com.xiaoka.platform.api.user.dictionary.NotifyType;
import com.xiaoka.platform.api.user.param.LoginPasswordParam;
import com.xiaoka.platform.api.user.param.PreLoginParam;
import com.xiaoka.platform.api.user.result.LoginResult;
import com.xiaoka.platform.api.user.result.UserInfoResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/15 16:03
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class UserServiceImpl implements UserService {

	private static final String SMD_CONTEXT = "验证码%s,您正在修改典典云仓登录密码，需进行验证，请不要向任何人泄漏。如非本人操作，请联系客服。";

	@Resource
	private MemberSoaWrapperService            memberSoaWrapperService;
	@Resource
	private SendSmsMessageService              smsMessageService;
	@Resource
	private CloudSupplierUserService           cloudSupplierUserService;
	@Resource
	private CloudCompanyStrategyRepo           cloudCompanyStrategyRepo;
	@Resource
	private CloudCompanyStrategyPropertyRepo   cloudCompanyStrategyPropertyRepo;
	@Resource
	private CloudCompanyStrategyAuthorizedRepo cloudCompanyStrategyAuthorizedRepo;

	@Override
	public Integer getUserIdAndCreateIfNull(String phone) {
		UserInfoResult userInfo = memberSoaWrapperService.getUserInfoByPhone(phone, null);
		if (userInfo == null) {
			//不存在就新增
			PreLoginParam preLoginParam = new PreLoginParam();
			preLoginParam.setPhone(phone);
			preLoginParam.setNotifyType(NotifyType.OTHERS.getCode());
			preLoginParam.setAppType(AppType.CLOUD_STOCK.getCode());
			memberSoaWrapperService.preLogin(preLoginParam);
			userInfo = memberSoaWrapperService.getUserInfoByPhone(phone, null);
		}
		AssertUtil.assertNotNullWithApiException(userInfo, "9002", "新增用户" + phone + "异常");
		return userInfo.getUserId();
	}

	@Override
	public boolean modifyPassword(String phone, String password, String code) {
		SmsMessageResult smsMessageResult = smsMessageService.selectSmsMessageByPhoneAndType(phone, SmsTypeEnum.UPDATE_PASSWORD);
		if (null == smsMessageResult || !smsMessageResult.getSmsCode().equals(code))
			throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, "验证码错误，请重新输入！");
		long createLong = smsMessageResult.getCreateTime().getTime();
		long nowLong    = new Date().getTime();
		int  c          = (int) ((nowLong - createLong) / 1000);
		//判断短信验证码是否过期
		if (c > 60) {
			smsMessageService.updateSmsMessageStatus(smsMessageResult.getId(), SmsStatusEnum.IS_TIMEOUT);
			throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, "验证码已超时，请重新获取！");
		}
		//修改验证码状态为已修改
		smsMessageService.updateSmsMessageStatus(smsMessageResult.getId(), SmsStatusEnum.IS_VALIDATE);
		//修改密码
		LoginPasswordParam loginPasswordParam = new LoginPasswordParam();
		loginPasswordParam.setUserId(this.getUserIdAndCreateIfNull(phone));
		//String md5Pwd = MD5.encryptHex(password);
		loginPasswordParam.setPassword(password);
		loginPasswordParam.setAppType(AppType.CLOUD_STOCK.getCode());
		return memberSoaWrapperService.setPassword(loginPasswordParam);
	}

	@Override
	public String getCode(String phone) {
		AssertUtil.assertTrueWithApiException(cloudSupplierUserService.checkPhone(phone), "该手机号无对应供应商");
		String              sixCode = getSixNumberCode();
		SaveSmsMessageParam param   = new SaveSmsMessageParam();
		param.setPhone(phone);
		param.setSmsCode(sixCode);
		param.setSmsContext(String.format(SMD_CONTEXT, sixCode));
		param.setSmsType(SmsTypeEnum.UPDATE_PASSWORD.getId());
		if (!smsMessageService.sendSMSMessage(param))
			throw new ApiException("-1", "短信验证码发送失败！");
		//验证是否正式环境（正式环境不返回验证码）
		if (AppEnvironment.get().getEnvironment().isProdEnv()
				|| AppEnvironment.get().getEnvironment() == AppEnvironment.Environment.PRE
				|| AppEnvironment.get().getEnvironment() == AppEnvironment.Environment.ADT
				|| AppEnvironment.get().getEnvironment() == AppEnvironment.Environment.LAST) {
			sixCode = "";
		}
		return sixCode;
	}

	@Override
	public CloudSupplierUserDto doLogin(String userPhone, String password) {
		AssertUtil.assertNotNullWithApiException(userPhone, "登录手机号不能为空");
		AssertUtil.assertNotNullWithApiException(password, "登录密码不能为空");
		LoginResult loginResult = memberSoaWrapperService.login(userPhone, password);//获取登录帐号;
		AssertUtil.assertNotNullWithApiException(loginResult, "登录失败");
		CloudSupplierUserDto cloudSupplierUserDto = cloudSupplierUserService.getCloudSupplierUserDtoByPhone(userPhone);
		cloudSupplierUserDto.setUserId(loginResult.getUserId());
		return cloudSupplierUserDto;
	}

	@Override
	public ErpCollectStrategyDto getCompanyStrategy(Integer companyId) {
		Preconditions.checkArgument(null != companyId);

		List<CloudCompanyStrategyEntity> cloudCompanyStrategyList = cloudCompanyStrategyRepo.selectByCompanyId(companyId);

		ErpCollectStrategyDto collectData = new ErpCollectStrategyDto();
		if (CollectionUtils.isEmpty(cloudCompanyStrategyList)) {
			return null;
		}
		CloudCompanyStrategyEntity companyStrategy = cloudCompanyStrategyList.get(0);
		collectData.setFlag(companyStrategy.getStrategyIdentity());

		List<CloudCompanyStrategyPropertyEntity> propertyEntities
				= cloudCompanyStrategyPropertyRepo.selectByStrategyId(companyStrategy.getId());
		if (CollectionUtils.isNotEmpty(propertyEntities)) {
			Map<String, String> addInfo = Maps.newHashMap();
			propertyEntities.forEach(p -> addInfo.put(p.getProName(), p.getProValue()));
			collectData.setAddInfo(addInfo);
		}
		return collectData;
	}

	@Override
	public boolean isErpAuthorized(String phone) {
		Preconditions.checkArgument(StringUtils.isNotBlank(phone));

		List<CloudCompanyStrategyAuthorizedEntity> cloudCompanyStrategyAuthorizedEntities = cloudCompanyStrategyAuthorizedRepo.selectByPhone(phone);
		if (CollectionUtils.isEmpty(cloudCompanyStrategyAuthorizedEntities)) {
			return false;
		}

		return cloudCompanyStrategyAuthorizedEntities.stream().anyMatch(p -> Objects.equals(p.getFlag(), 1));
	}

	/**
	 * 生成6位随机数
	 *
	 * @return
	 */
	private String getSixNumberCode() {
		String str = String.valueOf(Math.round(Math.random() * 1000000));
		if (str.length() != 6)
			return getSixNumberCode();
		else
			return str;
	}
}

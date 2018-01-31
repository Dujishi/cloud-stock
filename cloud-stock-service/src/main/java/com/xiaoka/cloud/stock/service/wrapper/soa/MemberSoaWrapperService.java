package com.xiaoka.cloud.stock.service.wrapper.soa;

import com.xiaoka.cloud.stock.service.core.exception.CloudStockErrorCode;
import com.xiaoka.freework.help.api.ApiException;
import com.xiaoka.platform.api.user.dictionary.AppType;
import com.xiaoka.platform.api.user.param.LoginPasswordParam;
import com.xiaoka.platform.api.user.param.PreLoginParam;
import com.xiaoka.platform.api.user.result.LoginResult;
import com.xiaoka.platform.api.user.result.PreLoginResult;
import com.xiaoka.platform.api.user.result.UserInfoResult;
import com.xiaoka.platform.api.user.service.LoginSOAService;
import com.xiaoka.platform.api.user.service.UserCenterService;
import com.xiaoka.platform.api.user.service.UserLoginSOAService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户中心SOA的包装类
 *
 * @author gancao 2017/11/14 13:40
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class MemberSoaWrapperService {

	private static final Logger logger = LoggerFactory.getLogger(MemberSoaWrapperService.class);

	@Resource
	private UserLoginSOAService userLoginSOAService;
	@Resource
	private UserCenterService userCenterService;
	@Resource
	private LoginSOAService loginSOAService;

	public LoginResult login(String phone, String password) {
		LoginPasswordParam param = new LoginPasswordParam();
		param.setPhone(phone);
		param.setPassword(password);
		param.setAppType(AppType.CLOUD_STOCK.getCode());
		try {
			return loginSOAService.login(param);//获取登录帐号
		} catch (ApiException e) {
			if ("uc_user_password_not_exists".equals(e.getErrCode())) {
				throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, "密码错误,请重新输入");
			} else if ("uc_user_not_exists".equals(e.getErrCode())) {
				throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, "手机账号不存在");
			} else if ("uc_user_password_error".equals(e.getErrCode())) {
				throw new ApiException(CloudStockErrorCode.CLOUD_STOCK_INVALID, "账号密码错误");
			} else {
				throw new ApiException(e.getErrCode(), e.getMessage());
			}
		} catch (Exception e) {
			logger.error("调用户中心接口com.xiaoka.platform.api.user.service.UserLoginSOAService.login报错", e);
		}
		return null;
	}

	public PreLoginResult preLogin(PreLoginParam preLoginParam) {
		try {
			return userCenterService.preLogin(preLoginParam);
		} catch (Exception e) {
			logger.error("调用户中心接口com.xiaoka.platform.api.user.service.UserCenterService.preLogin报错", e);
		}
		return null;
	}

	public UserInfoResult getUserInfoByPhone(String phone, Integer appType) {
		try {
			return userCenterService.getUserInfoByPhone(phone, appType);
		} catch (Exception e) {
			logger.error("调用户中心接口com.xiaoka.platform.api.user.service.UserCenterService.getUserInfoByPhone报错", e);
		}
		return null;
	}

	public UserInfoResult getUserInfoById(Integer userId, Integer appType) {
		try {
			return userCenterService.getUserInfoById(userId, appType);
		} catch (Exception e) {
			logger.error("调用户中心接口com.xiaoka.platform.api.user.service.UserCenterService.getUserInfoById报错", e);
		}
		return null;
	}

	public boolean setPassword(LoginPasswordParam params) {
		try {
			loginSOAService.setPassword(params);
			return true;
		} catch (Exception e) {
			logger.error("调用户中心接口com.xiaoka.platform.api.user.service.LoginSOAService.setPassword报错", e);
		}
		return false;
	}

}

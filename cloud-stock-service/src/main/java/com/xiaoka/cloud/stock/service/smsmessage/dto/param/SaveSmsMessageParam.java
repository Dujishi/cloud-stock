package com.xiaoka.cloud.stock.service.smsmessage.dto.param;

import java.io.Serializable;

/**
 * @author chengyi
 * @date 2017/2/22
 */
public class SaveSmsMessageParam implements Serializable {
    /**
     * 验证码
     */
    private String smsCode;
    /**
     * 短信内容
     */
    private String smsContext;
    /**
     * 短信类型
     */
    private Integer smsType;
    /**
     * 接收手机号码
     */
    private String phone;

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getSmsContext() {
        return smsContext;
    }

    public void setSmsContext(String smsContext) {
        this.smsContext = smsContext;
    }

    public Integer getSmsType() {
        return smsType;
    }

    public void setSmsType(Integer smsType) {
        this.smsType = smsType;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

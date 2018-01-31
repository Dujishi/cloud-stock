package com.xiaoka.cloud.stock.service.smsmessage.dto.result;

import java.io.Serializable;
import java.util.Date;

/**
 * @author chengyi
 * @date 2017/2/22
 */
public class SmsMessageResult implements Serializable {
    /**
     * 短信ID
     */
    private Integer id;
    /**
     * 验证码
     */
    private String smsCode;
    /**
     * 短信内容
     */
    private String smsContext;
    /**
     * 短信状态（0.待发送，1.已发送,2.已验证,3.已过期）
     */
    private Integer smsStatus;
    /**
     * 短信类型
     */
    private Integer smsType;
    /**
     * 接收手机号码
     */
    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Integer getSmsStatus() {
        return smsStatus;
    }

    public void setSmsStatus(Integer smsStatus) {
        this.smsStatus = smsStatus;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}

package com.xiaoka.cloud.stock.service.smsmessage;
import com.xiaoka.cloud.stock.service.smsmessage.constant.SmsStatusEnum;
import com.xiaoka.cloud.stock.service.smsmessage.constant.SmsTypeEnum;
import com.xiaoka.cloud.stock.service.smsmessage.dto.param.SaveSmsMessageParam;
import com.xiaoka.cloud.stock.service.smsmessage.dto.result.SmsMessageResult;

/**
 * 短信服务
 *
 * @author chengyi
 * @date 2017/2/21
 */
public interface SendSmsMessageService {
    /**
     * 发送短信
     *
     * @param param
     * @return
     */
    boolean sendSMSMessage(SaveSmsMessageParam param);

    /**
     * 根据手机号码短信类型获取最近的一条短信发送记录
     *
     * @param phone
     * @return
     */
    SmsMessageResult selectSmsMessageByPhoneAndType(String phone, SmsTypeEnum smsTypeEnum);

    /**
     * 修改短信状态
     * @param id
     * @param smsStatusEnum
     */
    void updateSmsMessageStatus(Integer id, SmsStatusEnum smsStatusEnum);

    /**
     * 关闭状态为已发送的短信记录
     * @param phone
     * @param smsTypeEnum
     */
    void closeOldSmsMessageStatusByPhoneAndType(String phone, SmsTypeEnum smsTypeEnum, SmsStatusEnum smsStatusEnum);
}

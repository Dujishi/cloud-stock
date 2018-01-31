package com.xiaoka.cloud.stock.service.smsmessage.impl;

import com.xiaoka.cloud.stock.core.smsmessage.entity.SmsMessageEntity;
import com.xiaoka.cloud.stock.core.smsmessage.repo.SmsMessageRepo;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.service.smsmessage.SendSmsMessageService;
import com.xiaoka.cloud.stock.service.smsmessage.constant.SmsStatusEnum;
import com.xiaoka.cloud.stock.service.smsmessage.constant.SmsTypeEnum;
import com.xiaoka.cloud.stock.service.smsmessage.dto.param.SaveSmsMessageParam;
import com.xiaoka.cloud.stock.service.smsmessage.dto.result.SmsMessageResult;
import com.xiaoka.platform.api.tool.sms.message.SendMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chengyi
 * @date 2017/2/21
 */
@Service
public class SendSmsMessageServiceImpl implements SendSmsMessageService {
    @Resource
    SendMessageService sendMessageService;
    @Resource
    SmsMessageRepo smsMessageRepo;

    @Override
    public boolean sendSMSMessage(SaveSmsMessageParam param) {
        boolean isSend = false;
        if (param.getPhone().lastIndexOf("1011234") >= 0)
            isSend = true;
            //发送短信
        else
            isSend = sendMessageService.sendMessage(param.getPhone(), param.getSmsContext());
        if (isSend) {
            //记录发送内容
            Integer smsId = SaveSMSMessage(param);
            if (null != smsId && smsId > 0)
                return true;
        }
        return false;
    }

    @Override
    public SmsMessageResult selectSmsMessageByPhoneAndType(String phone, SmsTypeEnum smsTypeEnum) {
        SmsMessageEntity smsMessageEntity = new SmsMessageEntity();
        smsMessageEntity.setPhone(phone);
        smsMessageEntity.setSmsType(smsTypeEnum.getId());
        SmsMessageEntity entity = smsMessageRepo.selectSmsMessageByPhoneAndType(smsMessageEntity);
        if (null != entity)
            return BeanUtils.transform(SmsMessageResult.class, entity);
        else
            return null;
    }

    @Override
    public void updateSmsMessageStatus(Integer id, SmsStatusEnum smsStatusEnum) {
        SmsMessageEntity smsMessageEntity = new SmsMessageEntity();
        smsMessageEntity.setId(id);
        smsMessageEntity.setSmsStatus(smsStatusEnum.getId());
        smsMessageRepo.updateSmsMessageStatus(smsMessageEntity);
    }

    @Override
    public void closeOldSmsMessageStatusByPhoneAndType(String phone, SmsTypeEnum smsTypeEnum, SmsStatusEnum smsStatusEnum) {
        SmsMessageEntity smsMessageEntity = new SmsMessageEntity();
        smsMessageEntity.setPhone(phone);
        smsMessageEntity.setSmsType(smsTypeEnum.getId());
        smsMessageEntity.setSmsStatus(smsStatusEnum.getId());
        smsMessageRepo.closeOldSmsMessageStatusByPhoneAndType(smsMessageEntity);
    }

    /**
     * 保存短信发送数据
     *
     * @param param
     */
    private Integer SaveSMSMessage(SaveSmsMessageParam param) {
        SmsMessageEntity smsMessageEntity = BeanUtils.transform(SmsMessageEntity.class, param);
        smsMessageEntity.setSmsStatus(SmsStatusEnum.SEND.getId());
        return smsMessageRepo.insert(smsMessageEntity);
    }
}

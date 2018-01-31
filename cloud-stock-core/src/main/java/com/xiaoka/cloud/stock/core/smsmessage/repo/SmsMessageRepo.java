package com.xiaoka.cloud.stock.core.smsmessage.repo;

import com.xiaoka.cloud.stock.core.smsmessage.entity.SmsMessageEntity;
import com.xiaoka.freework.container.dao.CommonDao;
import com.xiaoka.freework.data.datasource.RoutingDataSourceDecision;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * 短信操作
 *
 * @author chengyi
 * @date 2017/2/22
 */
@Repository
public class SmsMessageRepo {
    @Resource
    private CommonDao commonDao;

    /**
     * 添加短信发送记录
     *
     * @param entity
     */
    public Integer insert(SmsMessageEntity entity) {
        return commonDao.mapper(SmsMessageEntity.class).source(RoutingDataSourceDecision.Source.MASTER)
                      .sql("insertSmsMessage").session().insert(entity);
    }

    /**
     * 修改短信状态
     *
     * @param entity
     */
    public void updateSmsMessageStatus(SmsMessageEntity entity) {
        commonDao.mapper(SmsMessageEntity.class).source(RoutingDataSourceDecision.Source.MASTER)
                .sql("updateSmsMessageStatus").session().update(entity);
    }

    /**
     * 修改旧短信记录状态
     *
     * @param entity
     */
    public void closeOldSmsMessageStatusByPhoneAndType(SmsMessageEntity entity) {
        commonDao.mapper(SmsMessageEntity.class).source(RoutingDataSourceDecision.Source.MASTER)
                .sql("closeOldSmsMessageStatusByPhoneAndType").session().update(entity);
    }

    /**
     * 查询该手机号码最后一次短信记录
     *
     * @param smsMessageEntity
     * @return
     */
    public SmsMessageEntity selectSmsMessageByPhoneAndType(SmsMessageEntity smsMessageEntity) {
        return commonDao.mapper(SmsMessageEntity.class).source(RoutingDataSourceDecision.Source.SLAVE)
                .sql("selectSmsMessageByPhoneAndType").session().selectOne(smsMessageEntity);
    }
}

/*
 * Copyright (C), 2014-2018, 杭州小卡科技有限公司
 * FileName: CsOrderStatusEvent.java
 * Author:   Cheng Zhujiang
 * Date:     2018/1/16 14:18
 * Description: 
 */
package com.xiaoka.cloud.stock.service.mqmessage.dto;

import com.xiaoka.ddyc.tool.mq.XkMessageEvent;

import java.util.Date;

/**
 * <pre>
 * 仓数订单状态事件
 *
 * @author Cheng Zhujiang
 * @date 2018/1/16
 */
public class CsOrderStatusEvent implements XkMessageEvent {

    private static final String _EVENT_NAME = "CsOrderStatusEvent";

    private Integer id;

    /**
     * 订单编号
     */
    private Integer orderId;

    /**
     * 消息处理状态
     */
    private Integer status;

    private Date createTime;

    private Date updateTime;

    @Override
    public String name() {
        return _EVENT_NAME;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

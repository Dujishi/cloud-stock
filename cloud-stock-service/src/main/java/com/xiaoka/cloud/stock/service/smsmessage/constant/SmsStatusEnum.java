package com.xiaoka.cloud.stock.service.smsmessage.constant;

/**
 * 短信状态
 * @author chengyi
 * @date 2017/2/22
 */
public enum SmsStatusEnum {
    BEGIN_SEND(0,"待发送"),
    SEND(1,"已发送"),
    IS_VALIDATE(2,"已验证"),
    IS_TIMEOUT(3,"已过期");

    private Integer id;
    private String name;

    SmsStatusEnum(Integer id,String name){
        this.id=id;
        this.name=name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

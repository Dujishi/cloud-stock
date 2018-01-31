package com.xiaoka.cloud.stock.service.smsmessage.constant;

/**
 * 短信类型
 *
 * @author chengyi
 * @date 2017/2/22
 */
public enum SmsTypeEnum {
    SMS_DEFAULT(0, "一般短信"),
    UPDATE_PASSWORD(1, "修改密码短信验证");

    private Integer id;
    private String name;

    SmsTypeEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
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

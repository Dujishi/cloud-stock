package com.xiaoka.cloud.stock.service.open.constant;

/**
 * 操作
 *
 * @author zhouze
 * @date 2017/11/15
 * @see [相关类/方法]
 * @since [版本号]
 */
public enum OperateModeEnum {
	MODE_INSERT(1, "添加"),
	MODE_MODIFY(2, "修改"),
	MODE_DELETE(3, "删除");

	private int code;

	private String desc;

	public int getCode() {
		return code;
	}

	public String getDesc() {
		return desc;
	}

	OperateModeEnum(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}
}

package com.xiaoka.cloud.stock.service.open.validation.inspect;

/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ValidationContext {

	private ObjectInspectState state;

	public ValidationContext(ObjectInspectState state) {
		this.state = state;
	}

	public void handle(Object object) {
		state.checkObjectParameter(object);
	}

}

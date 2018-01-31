package com.xiaoka.cloud.stock.core.stock.annotion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 特殊函数标识，非通用函数
 *
 * @author zhouze
 * @date 2018/1/2
 * @see [相关类/方法]
 * @since [版本号]
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecialFunction {
}

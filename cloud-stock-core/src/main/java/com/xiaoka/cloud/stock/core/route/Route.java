package com.xiaoka.cloud.stock.core.route;

import java.lang.annotation.*;

/**
 * Created by junmo on 16/4/21.
 *
 * 加在mybatis的mapper类上
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Route {

    /**
     * 要分表的原始表名
     * @return
     */
    String tableName();

    /**
     * 分表策略，目前只支持取模 可以自己实现接口
     * eg:  %10
     * @return
     */
    String shardType();

    /**
     * 根据哪个字段分别
     * @return
     */
    String shardBy();
}

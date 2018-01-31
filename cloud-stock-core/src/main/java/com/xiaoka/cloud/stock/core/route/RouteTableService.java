package com.xiaoka.cloud.stock.core.route;

/**
 * Created by junmo on 16/4/25.
 */
public interface RouteTableService {

    String getActualSql(String originalSql, Route route);
}

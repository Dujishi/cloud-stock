package com.xiaoka.cloud.stock.core.route;

/**
 * Created by junmo on 16/4/25.
 */
public interface ShardService {


    String getShardValue(String shardType, String shardBy, Object shardValue);
}

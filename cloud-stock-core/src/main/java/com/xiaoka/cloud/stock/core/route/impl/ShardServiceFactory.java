package com.xiaoka.cloud.stock.core.route.impl;

import com.xiaoka.cloud.stock.core.route.ShardService;

/**
 * Created by junmo on 16/4/28.
 */
public class ShardServiceFactory {


    private static final String SHARD_MOD = "%";

    public static ShardService getShardService(String shardType){
        ShardService shardService = null;
        if (shardType != null && shardType.startsWith(SHARD_MOD)) {// 取模

            shardService=new ModShardServiceImpl();

        }else{
            //default
        }

        return shardService;

    }
}

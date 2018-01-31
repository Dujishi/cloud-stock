package com.xiaoka.cloud.stock.core.route.impl;

import com.xiaoka.cloud.stock.core.route.ShardService;
import com.xiaoka.freework.help.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by junmo on 16/4/25.
 */
public class ModShardServiceImpl implements ShardService {

    private static  final Logger logger = LoggerFactory.getLogger(ModShardServiceImpl.class);

    @Override
    public String getShardValue(String shardType, String shardBy,Object shardValue) {

        //shardType %10; shardBy: businessId

        //获取取模方式分表的参数
        logger.debug("shardType:{},shardBy:{},shadValue:{}", shardType, shardBy, shardValue);
        String routeParam = shardType.substring(1,shardType.length());
        if(null==shardValue){
            logger.error("分表参数为空!");
            throw new ApiException("-1", "分表参数缺失");
        }
        Integer i_routeValue = shardValue.hashCode();
        if (i_routeValue<0){
            i_routeValue = -i_routeValue;
        }
        String suffix = "" + (i_routeValue % (Integer.parseInt(routeParam)));

        return suffix;
    }

    public static void main(String[] args) {
        Integer v = 5280;
        System.out.println(v.hashCode());
        System.out.println(v.hashCode()%50);
    }

}

package com.xiaoka.cloud.stock.core.route.impl;

import com.xiaoka.cloud.stock.core.route.Route;
import com.xiaoka.cloud.stock.core.route.RouteTableService;
import com.xiaoka.cloud.stock.core.route.ShardService;
import com.xiaoka.freework.help.api.ApiException;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by junmo on 16/4/25.
 */
public class RouteTableServiceImpl implements RouteTableService {
    
    private static  final Logger logger = LoggerFactory.getLogger(RouteTableServiceImpl.class);

    private  Object parameterObject;
    
    private BoundSql boundSql;

    private Configuration configuration;

    public RouteTableServiceImpl(MetaObject metaStatementHandler) {
        this.boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        this.configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        this.parameterObject = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
    }


    @Override
    public String getActualSql(String originalSql, Route route) {
        String newSql = null;
        if (route == null)
            return newSql;
        String tableName = route.tableName().trim();
        String shardType = route.shardType().trim();
        String shardBy = route.shardBy().trim();
        ShardService shardService = ShardServiceFactory.getShardService(shardType);

        String suffix=shardService.getShardValue(shardType, shardBy, getShardValue(shardBy,shardType));

        if(suffix==null||suffix.equals("")){
            logger.warn("分表失败，获取不到值或者参数名称不匹配");
            return newSql;
        } else{
            //替换新表名
            String newTableName=tableName+"_"+suffix;
            logger.debug("newTableName:{}", newTableName);
            //表名尽量保持唯一，不要与字段重名
            newSql=originalSql.replaceAll(tableName, newTableName);
        }
        return newSql;

    }

    private Object getShardValue(String propertyName, String shardType) {
        MetaObject metaObject = parameterObject == null ? null : configuration
                .newMetaObject(parameterObject);

        Object value = null;

        //批量插入的处理 需要保证集合内是同一个分表参数
        if (parameterObject instanceof DefaultSqlSession.StrictMap){
            DefaultSqlSession.StrictMap tempMap = (DefaultSqlSession.StrictMap)parameterObject;
            if (null!=tempMap.get("list")){
                List paramList = (List) tempMap.get("list");
                if (paramList.size()>=1){
                    //fast fail
                    checkBatchProcess(paramList, propertyName, shardType);

                    return getValue(paramList.get(0),propertyName);
                }
            }
        }
        TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();

        if (boundSql.hasAdditionalParameter(propertyName)) { // issue #448 ask first for additional params
            value = boundSql.getAdditionalParameter(propertyName);
        } else if (parameterObject == null) {
            value = null;
        } else if (typeHandlerRegistry.hasTypeHandler(parameterObject
                .getClass())) {
           // if(isMutiPara)//多个参数，这情况就不应该匹配了
             //   return null;
            value = parameterObject;
        } else {
            value = metaObject == null ? null : metaObject
                    .getValue(propertyName);
        }
        return value;
    }


    private Object getValue(Object param, String propertyName){
        BeanInfo beanInfo = null;
        try {
            beanInfo = Introspector.getBeanInfo(param.getClass());
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor property : propertyDescriptors) {
                String key = property.getName();

                if (key.equals(propertyName)){
                    // 得到property对应的getter方法
                    Method getter = property.getReadMethod();
                    Object tempValue = getter.invoke(param);
                    return  tempValue;
                }
            }
        }catch (Exception e){
            logger.error("get value error",e);
        }

        return null;

    }

    /**
     *  检查分表参数是否冲突，即可能影响多张表
     * @param paramList
     * @param propertyName
     * @param shardType
     */
    private void checkBatchProcess(List paramList, String propertyName, String shardType) {

        ShardService  shardService = ShardServiceFactory.getShardService(shardType);

        Map<String,Object> listMap = new HashMap<>();
        for (Object o : paramList) {
//            Integer firstValue = (Integer)getValue(o,propertyName);
            Object firstValue = getValue(o,propertyName);
            String suffix=shardService.getShardValue(shardType, propertyName, firstValue);
            listMap.put(suffix,o);
        }
        if (listMap.size()!=1){
            logger.error("conflict route param");
            throw new ApiException("-1","conflict route param");
        }
    }
}

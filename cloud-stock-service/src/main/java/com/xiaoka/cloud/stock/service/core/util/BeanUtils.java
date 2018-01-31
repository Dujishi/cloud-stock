package com.xiaoka.cloud.stock.service.core.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xiaoka.freework.help.api.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Bean对象转换工具类
 *
 * @author gancao 2017/11/15 16:55
 * @see [相关类/方法]
 * @since [版本号]
 */
public class BeanUtils extends org.springframework.beans.BeanUtils{

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    /**
     * 普通对象转换
     * <p>
     *
     * @param targetClass 目标对象类型，必须含有无参构造函数，且目标对象和被转换对象如果有相同名称字段，则2个字段的类型必须一致
     * @param source 被转换的对象
     * @param <T>
     * @return
     */
    public static <T> T transform(Class<T> targetClass, Object source) {
        if (source == null) {
            return null;
        }
        try {
            String json_source = JSON.toJSONString(source);
            T dest = JSONObject.parseObject(json_source, targetClass);
            return dest;
        } catch (Exception e) {
            logger.error("对象转换出错：目标对象类型:{}, 被转换的对象类型:{}, 被转换的对象值:{}", targetClass, source.getClass(), JSON.toJSONString(source), e);
            throw new ApiException(e);
        }
    }

    public static <T> List<T> transformList(Class<T> targetClass, List<?> listSource) {
        if (listSource == null) {
            return null;
        }
        try {
            String json_source = JSON.toJSONString(listSource);
            List<T> dest = JSONArray.parseArray(json_source, targetClass);
            return dest;
        } catch (Exception e) {
            logger.error("对象转换出错：目标对象类型:{}, 被转换的对象类型:{}, 被转换的对象值:{}", targetClass, listSource.getClass(), JSON.toJSONString(listSource), e);
            throw new ApiException(e);
        }
    }
}

package com.xiaoka.cloud.stock.web.util;

import com.xiaoka.cloud.stock.service.core.util.dto.ApiResult;
import com.xiaoka.freework.utils.json.Jackson;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 *
 * api对象处理返回
 *
 * @author gancao 2017/11/13 14:12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ApiResultWrapper {

    private ApiResultWrapper(){}

    /**
     * 页面返回错误数据
     *
     * @param error
     * @param message
     * @return
     */
    public static String fail(String error, String message) {
        HashMap<String, Object> map = new LinkedHashMap();
        map.put("success", false);
        map.put("error", error);
        map.put("message", message);
        return Jackson.mobile().writeValueAsString(map);
    }

    /**
     * 页面成功返回数据
     *
     * @param success
     * @param message
     * @return
     */
    public static String success(Boolean success, String message) {
        return success(success, message, null);
    }

    /**
     * 页面成功返回数据
     *
     * @param data
     * @return
     */
    public static String success(Object data) {
        return success(true, null, data);
    }

    /**
     * 页面成功返回数据
     *
     * @param success 成功
     * @param message 消息
     * @param obj     传输数据
     * @param <T>     传输数据类型
     * @return
     */
    public static <T> String success(Boolean success, String message, final T obj) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(obj);
        apiResult.setSuccess(success);
        apiResult.setMessage(message);
        if (Objects.nonNull(obj) && obj instanceof List){//返回数据是集合则将size加上
            apiResult.setSize(((List) obj).size());
        }
        return Jackson.mobile().writeValueAsString(apiResult);
    }

    /**
     * 页面成功返回实体
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String successTrue(final T obj) {
        ApiResult<T> apiResult = new ApiResult<>();
        apiResult.setData(obj);
        apiResult.setSuccess(true);
        apiResult.setMessage("成功");
        return Jackson.mobile().writeValueAsString(apiResult);
    }

    /***
     * 页面返回
     */
    public static String result(Boolean success, String message, String result) {
        LinkedHashMap map = new LinkedHashMap();
        map.put("success", success);
        map.put("message", message);
        map.put("result", result);
        return Jackson.mobile().writeValueAsString(map);

    }

}

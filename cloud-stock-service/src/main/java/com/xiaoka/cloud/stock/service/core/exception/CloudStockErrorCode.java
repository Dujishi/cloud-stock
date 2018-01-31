package com.xiaoka.cloud.stock.service.core.exception;

/**
 * 云仓应用业务异常定义
 *
 * @author gancao 2017/11/14 11:16
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface CloudStockErrorCode {

    String USER_NO_LOGIN = "USER_NO_LOGIN";

    String CLOUD_STOCK_INVALID = "CLOUD_STOCK_INVALID";//errCode为此码的ApiException记录日志级别为INFO,同时用于客户端提示

    String INDENT_NO_INVALID = "INDENT_NO_INVALID";
    String INDENT_PART_NO_INVALID = "INDENT_PART_NO_INVALID";
    String CLOUD_STOCK_BRAND_FORBID = "CLOUD_STOCK_BRAND_FORBID";
}

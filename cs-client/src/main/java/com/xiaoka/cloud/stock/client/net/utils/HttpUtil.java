package com.xiaoka.cloud.stock.client.net.utils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.Map;

/**
 * @author zhouze
 * @date 2017/11/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 编码格式
	 */
	public static final String  CHARSET_NAME = "UTF-8";
	public static final Charset CHARSET      = Charset.forName(CHARSET_NAME);
	/**
	 * 报文类型
	 */
	public static final String  CONTENT_TYPE = "application/json;charset=UTF-8";

	/**
	 * POST 请求
	 *
	 * @param url         请求地址
	 * @param requestBody 请求体
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, String requestBody, Map<String, String> headers) throws Exception {
		logger.debug("request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost            httpPost   = new HttpPost(url);
			httpPost.addHeader("Content-Type", CONTENT_TYPE);
			if (null != headers && headers.size() > 0) {
				headers.forEach(httpPost::setHeader);
			}
			httpPost.setEntity(new StringEntity(requestBody, CHARSET));
			httpResponse = httpClient.execute(httpPost);
			String responseText = EntityUtils.toString(httpResponse.getEntity(), CHARSET);
			logger.debug("response text :\n" + responseText);
			return responseText;
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}
		}
	}

	/**
	 * GET 请求
	 *
	 * @param url 请求地址
	 * @return
	 * @throws Exception
	 */
	public static String doGet(String url, Map<String, String> headers) throws Exception {
		logger.debug("request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet             httpGet    = new HttpGet(url);
			httpGet.addHeader("Content-Type", CONTENT_TYPE);
			if (null != headers && headers.size() > 0) {
				headers.forEach(httpGet::setHeader);
			}
			httpResponse = httpClient.execute(httpGet);
			String responseText = EntityUtils.toString(httpResponse.getEntity(), CHARSET);
			logger.debug("response text :\n" + responseText);
			return responseText;
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}
		}
	}

	private HttpUtil() {
	}
}

package com.xiaoka.cloud.stock.service.core.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.core.crawl.repo.entity.ZeroAccountEntity;
import com.xiaoka.cloud.stock.service.crawl.core.log.EpcLogUtil;
import com.xiaoka.cloud.stock.service.crawl.core.log.constant.EpcLogTypeEnum;
import com.xiaoka.cloud.stock.service.crawl.core.log.dto.EpcLogDto;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.resp.ZeroResp;
import com.xiaoka.cloud.stock.service.crawl.linglingqi.task.dto.AccountMsgDto;
import com.xiaoka.cloud.stock.service.supplier.dto.CloudSupplierUserDto;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhouze
 * @date 2017/12/12
 * @see [相关类/方法]
 * @since [版本号]
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 编码格式
	 */
	public static final String CHARSET_NAME = "UTF-8";
	public static final Charset CHARSET = Charset.forName(CHARSET_NAME);
	/**
	 * 报文类型
	 */
	public static final String CONTENT_TYPE = "application/json;charset=UTF-8";

	/**
	 * POST 请求
	 *
	 * @param url         请求地址
	 * @param requestBody 请求体
	 * @return
	 * @throws Exception
	 */
	public static String doPost(String url, String requestBody) throws Exception {
		logger.debug("doPost -> request url : " + url);
		CloseableHttpResponse httpResponse = doPostResponse(url, requestBody, null);
		String responseText = EntityUtils.toString(httpResponse.getEntity(), CHARSET);
		logger.debug("response text :\n" + responseText);
		return responseText;
	}

	public static CloseableHttpResponse doPostResponse(String url, String requestBody, Map<String, String> headers) throws Exception {
		logger.debug("doPostResponse -> request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", CONTENT_TYPE);
			if (MapUtils.isNotEmpty(headers)) {
				headers.forEach(httpPost::setHeader);
			}
			if (StringUtils.isNotBlank(requestBody)) {
				httpPost.setEntity(new StringEntity(requestBody, CHARSET));
			}
			httpResponse = httpClient.execute(httpPost);
			return httpResponse;
		} finally {
			if (httpResponse != null) {
				httpResponse.close();
			}
		}
	}

	public static CloseableHttpResponse doProxyPostResponse(String url, String requestBody, Map<String, String> headers, String ip, Integer port)
			throws IOException {
		logger.debug("doPostResponse -> request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient;
			if (StringUtils.isNotBlank(ip) && Objects.nonNull(port)) {
				//设置代理IP、端口、协议（请分别替换）
				HttpHost proxy = new HttpHost(ip, port, "http");
				//把代理设置到请求配置
				RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setProxy(proxy).build();
				//实例化CloseableHttpClient对象
				httpClient = HttpClients.custom().setDefaultRequestConfig(defaultRequestConfig).build();
			} else {
				httpClient = HttpClients.createDefault();
			}
			HttpPost httpPost = new HttpPost(url);
			httpPost.addHeader("Content-Type", CONTENT_TYPE);
			if (MapUtils.isNotEmpty(headers)) {
				headers.forEach(httpPost::setHeader);
			}
			if (StringUtils.isNotBlank(requestBody)) {
				httpPost.setEntity(new StringEntity(requestBody, CHARSET));
			}
			httpResponse = httpClient.execute(httpPost);
			String result = EntityUtils.toString(httpResponse.getEntity());
			ZeroResp<Void> zeroResp = null;
			try {
				zeroResp = Jackson.base().readValue(result, new TypeReference<ZeroResp<Void>>() {
				});
			} catch (Exception e) {
				logger.error("对象转换异常:", e);
			}
			if (Objects.nonNull(zeroResp) && zeroResp.getCode() == 1) {//登录成功
				System.out.println(ip + ":" + port + " success");
				return httpResponse;
			} else {
				logger.error(Objects.nonNull(zeroResp) ? zeroResp.getMsg() : "登录失败");
			}
			return null;
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
	public static String doGet(String url) throws Exception {
		logger.debug("request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(url);
			httpGet.addHeader("Content-Type", CONTENT_TYPE);
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

	public static String doRespString(String url, AccountMsgDto accountMsgDto, CloudSupplierUserDto cloudSupplierUserDto) throws IOException {
		logger.debug("request url : " + url);
		CloseableHttpResponse httpResponse;
		CookieStore cookieStore = getCookieStore(accountMsgDto.getCookieMap());
		if (Objects.isNull(cookieStore)) {
			return null;
		}
		CloseableHttpClient httpClient;
		if (StringUtils.isNotBlank(accountMsgDto.getZeroAccountEntity().getIp()) && Objects.nonNull(accountMsgDto.getZeroAccountEntity().getPort())) {
			//设置代理IP、端口、协议（请分别替换）
			HttpHost proxy = new HttpHost(accountMsgDto.getZeroAccountEntity().getIp(), accountMsgDto.getZeroAccountEntity().getPort(), "http");
			//把代理设置到请求配置
			RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setProxy(proxy).build();
			//实例化CloseableHttpClient对象
			httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).setDefaultRequestConfig(defaultRequestConfig).build();
		} else {
			httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore).build();
		}
		HttpUriRequest httpUriRequest;
		if (url.indexOf("parts/search") > 0){
			//post请求
			httpUriRequest = new HttpPost(url);
		}else {
			httpUriRequest = new HttpGet(url);
		}
		httpUriRequest.addHeader("Content-Type", CONTENT_TYPE);
		long start = System.currentTimeMillis();
		httpResponse = httpClient.execute(httpUriRequest);
		//添加请求LOG
		requestLog(accountMsgDto, httpResponse, url, start,cloudSupplierUserDto);
		String responseText = EntityUtils.toString(httpResponse.getEntity(), CHARSET);

		logger.debug("response text :\n" + responseText);
		return responseText;

	}

	private HttpUtil() {
	}

	private static CookieStore getCookieStore(Map<String, String> cookieMap) {
		if (cookieMap.isEmpty()) {
			return null;
		}
		CookieStore cookieStore = new BasicCookieStore();
		cookieMap.forEach((k, v) -> {
			BasicClientCookie cookie = new BasicClientCookie(k, v);
			cookie.setDomain("www.007vin.com");
			cookieStore.addCookie(cookie);
		});
		return cookieStore;
	}

	private static void requestLog(AccountMsgDto accountMsgDto, CloseableHttpResponse response, String url, long start, CloudSupplierUserDto cloudSupplierUserDto){
		if (Objects.nonNull(response)){
			EpcLogDto epcLogDto = new EpcLogDto();
			ZeroAccountEntity zeroAccountEntity = accountMsgDto.getZeroAccountEntity();
			if (url.indexOf("?") > 0){
				epcLogDto.setUrl(url.substring(0, url.indexOf("?")));
				epcLogDto.setArgs(url.substring(url.indexOf("?") + 1));
			}else {
				epcLogDto.setUrl(url);
			}
			epcLogDto.setType(EpcLogTypeEnum.零零汽.getType());
			//请求状态
			epcLogDto.setStatus(Objects.nonNull(response.getStatusLine()) ? response.getStatusLine().getStatusCode()+"" : null);
			epcLogDto.setSource(zeroAccountEntity.getPhone());
			epcLogDto.setExecTime(System.currentTimeMillis() - start);
			epcLogDto.setRealIp(zeroAccountEntity.getRealIp());
			if (StringUtils.isNotBlank(zeroAccountEntity.getIp()) && Objects.nonNull(zeroAccountEntity.getPort())){
				epcLogDto.setIp(zeroAccountEntity.getIp().concat(":").concat(zeroAccountEntity.getPort().toString()));
			}
			if (Objects.nonNull(cloudSupplierUserDto)){
				epcLogDto.setUserId(cloudSupplierUserDto.getId());//供应商用户id
				epcLogDto.setSupplierId(cloudSupplierUserDto.getSupplierId());//供应商id
			}
			EpcLogUtil.logTrace(epcLogDto);
		}
	}

	public static void main(String[] args) {
		String url = "https://www.007vin.com/ppyvin/vingroup?code=maserati&vin=ZAMSS57E8E1103195";
		System.out.println(url.substring(0, url.indexOf("?")));
		System.out.println(url.substring(url.indexOf("?") + 1));
	}
}

package util;

import com.sun.istack.internal.NotNull;
import constant.NormalApiConstants;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zhouze
 * @date 2017/11/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class HttpUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * POST 请求
	 *
	 * @param url         请求地址
	 * @param requestBody 请求体
	 * @return
	 * @throws Exception
	 */
	public static String doPost(@NotNull String url, String requestBody) throws Exception {
		logger.debug("request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost            httpPost   = new HttpPost(url);
			httpPost.addHeader("Content-Type", NormalApiConstants.CONTENT_TYPE);
			httpPost.setEntity(new StringEntity(requestBody, NormalApiConstants.CHARSET));
			httpResponse = httpClient.execute(httpPost);
			String responseText = EntityUtils.toString(httpResponse.getEntity(), NormalApiConstants.CHARSET);
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
	public static String doGet(@NotNull String url) throws Exception {
		logger.debug("request url : " + url);
		CloseableHttpResponse httpResponse = null;
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpGet             httpGet    = new HttpGet(url);
			httpGet.addHeader("Content-Type", NormalApiConstants.CONTENT_TYPE);
			httpResponse = httpClient.execute(httpGet);
			String responseText = EntityUtils.toString(httpResponse.getEntity(), NormalApiConstants.CHARSET);
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

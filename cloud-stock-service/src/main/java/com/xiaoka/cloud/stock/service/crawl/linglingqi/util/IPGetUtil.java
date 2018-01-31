package com.xiaoka.cloud.stock.service.crawl.linglingqi.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 获取公网ip
 *
 * @author gancao 2017/12/27 21:45
 * @see [相关类/方法]
 * @since [版本号]
 */
public class IPGetUtil {

	public static String getV4IP(){
		String ip = "";
		String url = "http://www.taobao.com/help/getip.php";

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpGet    = new HttpGet(url);
		httpGet.addHeader("Content-Type", "UTF-8");
		String responseText = null;
		try {
			CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
			responseText = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(responseText)){
			Pattern p = Pattern.compile("ip:\"(.*?)\"");
			Matcher m = p.matcher(responseText);
			if(m.find()){
				ip = m.group(1);
			}
		}
		return ip;
	}

	public static void main(String[] args) {
		System.out.println(getV4IP());
	}
}

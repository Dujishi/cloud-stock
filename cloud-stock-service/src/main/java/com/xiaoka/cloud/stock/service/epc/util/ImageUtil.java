package com.xiaoka.cloud.stock.service.epc.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Do something
 *
 * @author gancao 2017/11/30 17:46
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ImageUtil {

	private static final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

	public static String enCodeUrl(String url) {
		if (StringUtils.isNotBlank(url)) {
			try {
				url = url.replaceAll("\\[", "").replaceAll("]", "");
				int i = url.lastIndexOf("/");
				if (i > 0) {
					url = url.substring(0, i + 1) + URLEncoder.encode(url.substring(i + 1, url.length()), "UTF-8");
				}
				//#特许处理
				url = url.replaceAll("#", URLEncoder.encode("#", "UTF-8"));
				return url;
			} catch (UnsupportedEncodingException e) {
				logger.error("图片路径编码报错:{}", url);
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void main(String[] args) {
		try {
			System.out.println(URLEncoder.encode("A8#M01090", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

}

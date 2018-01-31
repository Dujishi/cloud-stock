package com.xiaoka.cloud.stock.service.open.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * 为了保证签名与客户端签名一致，该类不依赖任何第三方框架
 *
 * @author zhouze
 * @date 2017/11/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class ApiSignUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApiSignUtil.class);

	/**
	 * 编码格式
	 */
	private static final String CHARSET_NAME = "UTF-8";
	/**
	 * 加密算法,默认使用MD5摘要算法
	 */
	private static final String ALGORITHM    = "MD5";
	/**
	 * 十六进制定义
	 */
	private static final char[] HEX_DIGIT    = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};


	/**
	 * 签名方法
	 * 签名方式：sign = Encode（MD5（UPPER（MD5（UPPER（param）））））
	 *
	 * @param param 这里只按签名方式签名，参数在进入此方法前需要保证按接口指定的参数顺序拼装，
	 *              如无指定接口签名的参数顺序，则按参数字典顺序拼装.
	 * @return sign 已加密完成的签名字符串
	 * @throws IllegalArgumentException 必须有效的参数
	 */
	public static String generateApiSign(final String param) {
		if (isBlank(param)) {
			LOGGER.info("参数不能为空");
			throw new IllegalArgumentException("Parameter 'param' shouldn't be null or empty");
		}

		String sign = encryptSignOnce(encryptSignOnce(param));
		try {
			return URLEncoder.encode(sign, CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			LOGGER.info("不支持的编码类型,{}", CHARSET_NAME, e);
			return null;
		}
	}

	private static String encryptSignOnce(String param) {
		return encryptHex(param, CHARSET_NAME).toUpperCase(Locale.CHINA);
	}

	private static boolean isBlank(final CharSequence cs) {
		int strLen;
		if (cs == null || (strLen = cs.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(cs.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	private static String encryptHex(String strInput, String charset) {
		byte[] b;
		try {
			b = strInput.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			b = strInput.getBytes();
		}
		return encryptHex(b);
	}

	private static String encryptHex(byte[] byteInput) {
		return binToHex(encrypt(byteInput));
	}

	private static String binToHex(byte[] bin) {
		if (bin == null) {
			throw new IllegalArgumentException("Parameter bin shouldn't be null");
		}
		int    len = bin.length;
		char[] str = new char[len * 2];
		int    k   = 0;
		for (int i = 0; i < len; i++) {
			byte byte0 = bin[i];
			str[k++] = HEX_DIGIT[byte0 >>> 4 & 0x0f];
			str[k++] = HEX_DIGIT[byte0 & 0x0f];
		}
		return new String(str);
	}

	private static byte[] encrypt(byte[] byteInput) {
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM);
			md.update(byteInput);
			return md.digest();
		} catch (NoSuchAlgorithmException e) {
			LOGGER.info("不支持的算法类型,{}", ALGORITHM, e);
			return new byte[]{};
		}
	}

}

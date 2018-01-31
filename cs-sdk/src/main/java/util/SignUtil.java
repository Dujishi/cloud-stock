package util;

import constant.NormalApiConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Locale;

/**
 * 签名算法工具类
 * 签名方式：sign = Encode（UPPER（MD5（UPPER（MD5（param）））））
 * 注：该工具类未依赖任何第三方jar，可单独使用
 *
 * @author zhouze
 * @date 2017/11/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SignUtil {

	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

	/**
	 * 十六进制定义
	 */
	public static final char[] HEX_DIGIT = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

	/**
	 * 加密算法,默认使用MD5摘要算法
	 */
	public static final String ALGORITHM = "MD5";


	/**
	 * 签名方法
	 *
	 * @param param 待签名的字符串
	 * @return sign 签名后的字符串
	 * @throws IllegalArgumentException 必须有效的参数
	 */
	public static String generateSign(String param) {
		if (isBlank(param)) {
			logger.info("parameter 'param' is null or empty");
			throw new IllegalArgumentException("parameter 'param' shouldn't be null or empty");
		}

		String sign = encryptSignOnce(encryptSignOnce(param));
		try {
			return URLEncoder.encode(sign, NormalApiConstants.CHARSET_NAME);
		} catch (UnsupportedEncodingException e) {
			logger.info("charset '{}' unsupported encoding", NormalApiConstants.CHARSET_NAME);
			return null;
		}
	}

	private static String encryptSignOnce(String param) {
		return encryptHex(param, NormalApiConstants.CHARSET_NAME).toUpperCase(Locale.CHINA);
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
			throw new IllegalArgumentException("parameter bin shouldn't be null");
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
			logger.info("no such algorithm in digest");
			return new byte[]{};
		}
	}

	private SignUtil() {
	}
}

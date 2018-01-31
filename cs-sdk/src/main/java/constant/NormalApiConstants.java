package constant;

import java.nio.charset.Charset;

/**
 * @author zhouze
 * @date 2017/11/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class NormalApiConstants {
	private NormalApiConstants() {
	}

	/**
	 * 访问者授权的编号
	 *  - 由典典单独提供
	 */
	public static final String APP_ID = "xk100000000002";
	/**
	 * 秘钥
	 *  - 由典典单独提供
	 */
	public static final String SECURITY_KEY = "488002672c5d4d8d892d331ff2ab4a5e";

	/**
	 * 访问域名
	 *  - 由典典单独提供
	 */
	public static final String HOST = "http://localhost:8080/cloud-stock";

	/**
	 * 编码格式
	 */
	public static final String  CHARSET_NAME = "UTF-8";
	public static final Charset CHARSET      = Charset.forName(CHARSET_NAME);
	/**
	 * 报文类型
	 */
	public static final String  CONTENT_TYPE = "application/json;charset=UTF-8";


}

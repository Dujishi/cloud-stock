package util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhouze
 * @date 2017/11/10
 * @see [相关类/方法]
 * @since [版本号]
 */
public class HttpUtilFixture {

	@Test
	public void doPostTest() throws Exception {
		String response =HttpUtil.doPost("","");

		Assert.assertNotNull(response);
	}

	@Test
	public void doGetTest() throws Exception {
		String response =HttpUtil.doGet("http://www.baidu.com?t=1");

		Assert.assertNotNull(response);
	}

}

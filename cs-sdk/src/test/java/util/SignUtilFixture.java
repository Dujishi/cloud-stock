package util;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author zhouze
 * @date 2017/11/10
 * @see [相关类/方法]
 * @since [版本号]
 */
public class SignUtilFixture {

	@Test
	public void generateSignTest() {
		String sign = SignUtil.generateSign("https://cs.ddyc.com/openApi/sync/initDataKEYAPPID1510033712968a3c2d1");

		Assert.assertNotNull(sign);
		Assert.assertEquals(32, sign.length());

		System.out.println(sign);
	}
}

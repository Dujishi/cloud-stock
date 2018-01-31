package com.xiaoka.cloud.stock.core.tmp;

import org.junit.BeforeClass;
import org.junit.Test;

import com.xiaoka.freework.container.ContainerConstant;
import com.xiaoka.freework.container.test.ContainerTest;

//@ContextConfiguration(locations = { "classpath:com/xiaoka/cloud/stock/core/tmp/RedisClientTest.xml" })
public class RedisClientTest extends ContainerTest {
//	@Resource(name = "containerRedisClient")
//	private RedisClient redisClient;
//	@Resource(name = "containerRedisTemplate")
//	private RedisTemplate<String, String> redisTemplateA;
	
//	@Resource(name = "containerRedisTemplate")
//	private RedisTemplate<String,String> redisTemplate;

	@BeforeClass
	public static void beforeClass() {
		System.setProperty(ContainerConstant.APP_ENV, "int");
		// System.setProperty(ContainerConstant.APP_RUN_MODE, "server");
		// System.setProperty(ContainerConstant.PROJECT_ZONE_NAME,
		// "winner-test");
		// System.setProperty(ContainerConstant.PROJECT_ZONE_APPS, "fuxi");
	}
	
	@Test
	public void testBuild() {
		
//		String value = "test_value_a";
//		BoundValueOperations<String, String> ops = redisTemplateA.boundValueOps("RedisClientTest.key");
//		ops.set(value);
//		String result = ops.get();
//		assert value.equals(result);
//		System.out.println(result);
//		
//		RedisTemplate<String, String> redisTemplateB = redisClient.redisTemplate();
//		
//		RedisTemplate<String, String> redisTemplateC = Utils.get(RedisClient.class).redisTemplate();

		System.out.println("End");
	}
}

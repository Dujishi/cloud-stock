package com.xiaoka.cloud.stock.service.crawl.linglingqi;

import com.xiaoka.cloud.stock.service.crawl.linglingqi.service.ZeroDataProcessService;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

/**
 * @author zhouze
 * @date 2017/12/21
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/cloud-stock-context.xml",
		"classpath:spring/cloud-stock-soa-reference.xml",
		"classpath:spring/memcache.xml",
		"classpath:spring/mongo.xml",
		"classpath*:spring/tool-service.xml"
})
public class ZeroDataProcessServiceTest extends ContainerTest {

	@Resource
	ZeroDataProcessService zeroDataProcessService;

	@Test
	public void uploadAllImgToUpyunTest(){
		zeroDataProcessService.uploadAllImgToUpyun();
	}

}

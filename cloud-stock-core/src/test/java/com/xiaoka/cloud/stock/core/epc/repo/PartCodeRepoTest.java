package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author zhouze
 * @date 2017/11/24
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class PartCodeRepoTest extends ContainerTest {

	@Resource
	PartCodeRepo partCodeRepo;

	@Test
	public void selectCount() throws Exception {
		int all = partCodeRepo.selectCount();

		System.out.println(all);
	}

	@Test
	public void selectPage() throws Exception {
	}

}
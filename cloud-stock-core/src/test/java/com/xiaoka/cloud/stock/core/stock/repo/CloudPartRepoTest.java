package com.xiaoka.cloud.stock.core.stock.repo;

import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.awt.*;

import static org.junit.Assert.*;

/**
 * Created by sabo on 25/11/2017.
 */

@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class CloudPartRepoTest extends ContainerTest{

	@Resource
	CloudPartRepo cloudPartRepo;

	@Test
	public void selectByOeNo() throws Exception {
		cloudPartRepo.selectByOeNo(null);
	}

}
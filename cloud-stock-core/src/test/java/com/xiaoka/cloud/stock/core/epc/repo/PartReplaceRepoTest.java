package com.xiaoka.cloud.stock.core.epc.repo;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author zhouze
 * @date 2017/11/27
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class PartReplaceRepoTest extends ContainerTest {


	@Resource
	PartReplaceRepo partReplaceRepo;

	@Test
	public void selectByCodeList() throws Exception {
		List<PartReplaceEntity> partReplaceEntityList = Lists.newArrayList();
		PartReplaceEntity       p1                    = new PartReplaceEntity();
		p1.setPartCode("01290139119");
		p1.setReplacePartCode("01290415578");
		partReplaceEntityList.add(p1);
		PartReplaceEntity p2 = new PartReplaceEntity();
		p2.setPartCode("014301485");
		p2.setReplacePartCode("L014 301 485");
		partReplaceEntityList.add(p2);

		List<PartReplaceEntity> partReplaceEntities = partReplaceRepo.selectByCodeList(partReplaceEntityList);

		Assert.assertNotNull(partReplaceEntities);

	}

}
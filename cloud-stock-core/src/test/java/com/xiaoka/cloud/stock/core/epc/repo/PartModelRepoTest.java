package com.xiaoka.cloud.stock.core.epc.repo;

import com.xiaoka.cloud.stock.core.epc.repo.entity.PartModelEntity;
import com.xiaoka.freework.container.test.ContainerTest;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by sabo on 24/11/2017.
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class PartModelRepoTest extends ContainerTest{

	@Resource
	PartModelRepo partModelRepo;
	@Test
	public void insertList() throws Exception {
		/**
		 * "brandId": 44,
		 "brandName": "福特",
		 "epcNo": 21,
		 "makeId": 132,
		 "makeName": "长安福特",
		 "modelId": 8000,
		 "modelName": "蒙迪欧 2013款 2.0T GTDi200 A/MT 豪华版",
		 "partCode": "DS73F02523AF",
		 "seriesId": 1565,
		 "seriesName": "蒙迪欧（2013-",
		 "structure": "三厢车",
		 "timerType": "CAF488WQ3前轮驱动6档手自一体变速器",
		 "type": "原厂"
		 */
		List<PartModelEntity> insertList = new ArrayList<>();
		PartModelEntity partModelEntity = new PartModelEntity();
		partModelEntity.setBrandId(44);
		partModelEntity.setBrandName("福特");
		partModelEntity.setEpcNo(21);
		partModelEntity.setMakeId(132);
		partModelEntity.setMakeName("长安福特");
		partModelEntity.setModelId(8000);
		partModelEntity.setModelName("蒙迪欧 2013款 2.0T GTDi200 A/MT 豪华版");
		partModelEntity.setPartCode("DS73F02523AF");
		partModelEntity.setSeriesId(1565);
		partModelEntity.setSeriesName("蒙迪欧（2013-");
		partModelEntity.setStructure("三厢车");
		partModelEntity.setTimerType("CAF488WQ3前轮驱动6档手自一体变速器");
		partModelEntity.setType("原厂");
		partModelEntity.setIsValid(1);
		insertList.add(partModelEntity);
		partModelRepo.insertList(insertList);
	}

}
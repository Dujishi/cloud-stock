package com.xiaoka.cloud.stock.core.supplier;

import com.xiaoka.cloud.stock.core.supplier.repo.*;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.*;
import com.xiaoka.freework.container.test.ContainerTest;
import com.xiaoka.freework.utils.json.Jackson;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.Resource;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/14 17:18
 * @see [相关类/方法]
 * @since [版本号]
 */
@ContextConfiguration(locations = {
		"classpath:spring/cloud-stock-ds.xml",
		"classpath:spring/cloud-stock-orm.xml",
		"classpath:spring/content.xml"
})
public class SupplierServiceTest extends ContainerTest {

	@Resource
	private CloudSupplierCompanyRepo cloudSupplierCompanyRepo;
	@Resource
	private CloudSupplierCompanySubRepo cloudSupplierCompanySubRepo;
	@Resource
	private CloudSupplierUserRepo cloudSupplierUserRepo;
	@Resource
	private CloudSupplierAuthorizeIpRepo cloudSupplierAuthorizeIpRepo;
	@Resource
	private CloudErpFirmRepo cloudErpFirmRepo;
	@Resource
	private CloudErpSupplierMapperRepo cloudErpSupplierMapperRepo;

	@Test
	public void insertSupplierCompany(){
		CloudSupplierCompanyEntity entity = new CloudSupplierCompanyEntity();
		entity.setCompanyName("杭州测试供应商总公司");
		cloudSupplierCompanyRepo.insert(entity);
	}

	@Test
	public void insertSubSupplier(){
		CloudSupplierCompanySubEntity companySubEntity = new CloudSupplierCompanySubEntity();
		companySubEntity.setCompanyId(1);
		companySubEntity.setSupplierId(32825);
		cloudSupplierCompanySubRepo.insert(companySubEntity);
	}

	@Test
	public void selectAllSubSupplierIdById(){
		List<Integer> idList = cloudSupplierCompanySubRepo.selectAllSubSupplierIdById(1);
		System.out.println(idList);
	}

	@Test
	public void insertUser(){
		CloudSupplierUserEntity cloudSupplierUserEntity = new CloudSupplierUserEntity();
		cloudSupplierUserEntity.setSupplierId(32825);
		cloudSupplierUserEntity.setPhone("10112341234");
		cloudSupplierUserEntity.setName("甘草君");
		cloudSupplierUserEntity.setRoleId(1);
		cloudSupplierUserRepo.insert(cloudSupplierUserEntity);
	}

	@Test
	public void getUserByPhone(){
		CloudSupplierUserEntity userEntity = cloudSupplierUserRepo.selectByPhone("10112341234");
		System.out.println(Jackson.base().writeValueAsString(userEntity));
	}

	@Test
	public void deleteUser(){
		cloudSupplierUserRepo.deleteSupplierUser(10);
	}

	@Test
	public void insertIp(){
		CloudSupplierAuthorizeIpEntity entity = new CloudSupplierAuthorizeIpEntity();
		entity.setSupplierId(32825);
		entity.setIp("192.168.15.137");
		entity.setRemark("测试备注");
		cloudSupplierAuthorizeIpRepo.insert(entity);
	}

	@Test
	public void getIp(){
		int count = cloudSupplierAuthorizeIpRepo.getCountByIpAndSupplierId("192.168.15.137", 32825);
		System.out.println(count);
	}

	@Test
	public void checkIp(){
		int count = cloudSupplierAuthorizeIpRepo.getCountByIp("192.168.15.137");
		System.out.println(count);
	}

	@Test
	public void insertErp(){
		CloudErpFirmEntity entity = new CloudErpFirmEntity();
		entity.setErpFirmName("科众");
		cloudErpFirmRepo.insert(entity);
	}

	@Test
	public void insertErpSupplier(){
		CloudErpSupplierMapperEntity entity = new CloudErpSupplierMapperEntity();
		entity.setSupplierId(32825);
		entity.setErpId(1);
		entity.setOutSupplierId("1000");
		cloudErpSupplierMapperRepo.insert(entity);
	}

	@Test
	public void getKeyByAppId(){
		String key = cloudErpFirmRepo.getErpKeyByAppId("xk100000000004");
		System.out.println(key);
	}

	@Test
	public void getSupplierIdByAppIdAndOutSupplierId(){
		Integer supplierId = cloudErpSupplierMapperRepo.getSupplierId("xk100000000004", "1000");
		System.out.println(supplierId);
	}

}

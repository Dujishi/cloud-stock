package com.xiaoka.cloud.stock.soa.impl.supplier;

import com.google.common.base.Preconditions;
import com.xiaoka.cloud.stock.core.supplier.repo.*;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.*;
import com.xiaoka.cloud.stock.service.core.util.AssertUtil;
import com.xiaoka.cloud.stock.service.core.util.BeanUtils;
import com.xiaoka.cloud.stock.soa.api.supplier.CloudSupplierDataSoaService;
import com.xiaoka.cloud.stock.soa.api.supplier.dto.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/16 14:49
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service("cloudSupplierDataSoaService")
public class CloudSupplierDataSoaServiceImpl implements CloudSupplierDataSoaService {

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

	@Override
	public void saveSupplierCompany(CloudSupplierCompanyDto dto) {
		Preconditions.checkNotNull(dto, "参数不能为空");
		CloudSupplierCompanyEntity entity = BeanUtils.transform(CloudSupplierCompanyEntity.class, dto);
		if (Objects.nonNull(dto.getId())) {//更新
			cloudSupplierCompanyRepo.update(entity);
		} else {//新增
			cloudSupplierCompanyRepo.insert(entity);
		}
	}

	@Override
	public void saveSupplierCompanySub(CloudSupplierCompanySubDto dto) {
		Preconditions.checkNotNull(dto, "参数不能为空");
		CloudSupplierCompanySubEntity entity = BeanUtils.transform(CloudSupplierCompanySubEntity.class, dto);
		if (Objects.nonNull(dto.getId())) {//更新
			cloudSupplierCompanySubRepo.update(entity);
		} else {//新增
			//查询该子供应商是否已关联其他供应商总公司
			CloudSupplierCompanySubEntity companySubEntity = cloudSupplierCompanySubRepo.selectBySupplierId(entity.getSupplierId());
			AssertUtil.assertTrueWithApiException(Objects.isNull(companySubEntity), "该子供应商已关联供应商总公司");
			cloudSupplierCompanySubRepo.insert(entity);
		}
	}

	@Override
	public void saveSupplierAuthorizeIp(CloudSupplierAuthorizeIpDto dto) {
		Preconditions.checkNotNull(dto, "参数不能为空");
		CloudSupplierAuthorizeIpEntity entity = BeanUtils.transform(CloudSupplierAuthorizeIpEntity.class, dto);
		if (Objects.nonNull(dto.getId())) {//更新
			cloudSupplierAuthorizeIpRepo.update(entity);
		} else {//新增
			int count = cloudSupplierAuthorizeIpRepo.getCountByIpAndSupplierId(dto.getIp(), dto.getSupplierId());
			AssertUtil.assertTrueWithApiException(count == 0, "该供应商已添加过该权限IP");
			cloudSupplierAuthorizeIpRepo.insert(entity);
		}
	}

	@Override
	public void saveSupplierUser(CloudSupplierUserDto dto) {
		Preconditions.checkNotNull(dto, "参数不能为空");
		CloudSupplierUserEntity entity = BeanUtils.transform(CloudSupplierUserEntity.class, dto);
		if (Objects.nonNull(dto.getId())) {//更新
			cloudSupplierUserRepo.update(entity);
		} else {//新增
			CloudSupplierUserEntity userEntity = cloudSupplierUserRepo.selectByPhone(dto.getPhone());
			AssertUtil.assertTrueWithApiException(Objects.isNull(userEntity), "该手机号已被供应商关联");
			cloudSupplierUserRepo.insert(entity);
		}
	}

	@Override
	public void saveErpFirm(CloudErpFirmDto dto) {
		Preconditions.checkNotNull(dto, "参数不能为空");
		CloudErpFirmEntity entity = BeanUtils.transform(CloudErpFirmEntity.class, dto);
		if (Objects.nonNull(dto.getId())) {//更新
			cloudErpFirmRepo.update(entity);
		} else {//新增
			cloudErpFirmRepo.insert(entity);
		}
	}

	@Override
	public void saveErpSupplierMapper(CloudErpSupplierMapperDto dto) {
		Preconditions.checkNotNull(dto, "参数不能为空");
		CloudErpSupplierMapperEntity entity = BeanUtils.transform(CloudErpSupplierMapperEntity.class, dto);
		if (Objects.nonNull(dto.getId())) {//更新
			cloudErpSupplierMapperRepo.update(entity);
		} else {//新增
			cloudErpSupplierMapperRepo.insert(entity);
		}
	}
}

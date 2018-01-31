package com.xiaoka.cloud.stock.service.supplier.impl;

import com.xiaoka.cloud.stock.core.supplier.repo.CloudErpFirmRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudErpSupplierMapperRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudSupplierAuthorizeIpRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.CloudSupplierCompanySubRepo;
import com.xiaoka.cloud.stock.core.supplier.repo.entity.CloudSupplierCompanySubEntity;
import com.xiaoka.cloud.stock.service.supplier.CloudSupplierService;
import com.xiaoka.freework.cache.annotation.ServiceCache;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * Do something
 *
 * @author gancao 2017/11/13 21:10
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class CloudSupplierServiceImpl implements CloudSupplierService {

	@Resource
	private CloudErpFirmRepo cloudErpFirmRepo;
	@Resource
	private CloudErpSupplierMapperRepo cloudErpSupplierMapperRepo;
	@Resource
	private CloudSupplierAuthorizeIpRepo cloudSupplierAuthorizeIpRepo;
	@Resource
	private CloudSupplierCompanySubRepo cloudSupplierCompanySubRepo;

	@Override
	public String getKeyByAppId(String appId) {
		if (StringUtils.isBlank(appId)) {
			return null;
		}
		return cloudErpFirmRepo.getErpKeyByAppId(appId);
	}

	@Override
	public Integer getSupplierIdByAppIdAndOutSupplierId(String appId, String outSupplierId) {
		if (StringUtils.isBlank(appId) || StringUtils.isBlank(outSupplierId)) {
			return null;
		}
		return cloudErpSupplierMapperRepo.getSupplierId(appId, outSupplierId);
	}

	@Override
	@ServiceCache(includeKeys = {"ip"}, expire = 180)
	public boolean isValidIp(String ip) {
		if (StringUtils.isBlank(ip)) {
			return false;
		}
		int count = cloudSupplierAuthorizeIpRepo.getCountByIp(ip);
		return count > 0;
	}

	@Override
	public List<Integer> getCompanyAllSubSupplierIdList(Integer supplierId) {
		CloudSupplierCompanySubEntity subEntity = cloudSupplierCompanySubRepo.selectBySupplierId(supplierId);
		if (Objects.nonNull(subEntity)){
			return cloudSupplierCompanySubRepo.selectAllSubSupplierIdById(subEntity.getCompanyId());
		}
		return null;
	}
}

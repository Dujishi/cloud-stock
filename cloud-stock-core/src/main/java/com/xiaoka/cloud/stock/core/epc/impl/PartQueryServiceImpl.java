package com.xiaoka.cloud.stock.core.epc.impl;

import com.xiaoka.cloud.stock.core.epc.PartQueryService;
import com.xiaoka.cloud.stock.core.epc.repo.Part4sPriceRepo;
import com.xiaoka.cloud.stock.core.epc.repo.PartModelRepo;
import com.xiaoka.cloud.stock.core.epc.repo.PartReplaceRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.Part4sPriceEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/28 21:18
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class PartQueryServiceImpl implements PartQueryService {

	@Resource
	private PartModelRepo partModelRepo;
	@Resource
	private Part4sPriceRepo part4sPriceRepo;
	@Resource
	private PartReplaceRepo partReplaceRepo;

	@Override
	public List<PartModelEntity> getCarModelPartEntityByPartCode(String partCode) {
		PartModelEntity param = new PartModelEntity();
		param.setPartCode(partCode);
		param.setIsValid(1);
		return partModelRepo.select(param);
	}

	@Override
	public List<Part4sPriceEntity> getPart4sPriceEntity(String partCode, String brandName, String markName) {
		Part4sPriceEntity param = new Part4sPriceEntity();
		param.setPartCode(partCode);
		param.setBrandName(brandName);
		param.setMakeName(markName);
		param.setIsValid(1);
		return part4sPriceRepo.select(param);
	}

	@Override
	public List<PartReplaceEntity> getPartReplaceEntity(String partCode, String brandName) {
		PartReplaceEntity param = new PartReplaceEntity();
		param.setPartCode(partCode);
		if (StringUtils.isNotBlank(brandName)) {
			param.setBrandName(brandName);
		}
		param.setIsValid(1);
		return partReplaceRepo.select(param);
	}
}

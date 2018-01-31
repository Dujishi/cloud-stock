package com.xiaoka.cloud.stock.core.epc;

import com.xiaoka.cloud.stock.core.epc.repo.entity.Part4sPriceEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartModelEntity;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;

import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/28 21:18
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface PartQueryService {

	List<PartModelEntity> getCarModelPartEntityByPartCode(String partCode);

	List<Part4sPriceEntity> getPart4sPriceEntity(String partCode, String brandName, String markName);

	List<PartReplaceEntity> getPartReplaceEntity(String partCode, String brandName);
}

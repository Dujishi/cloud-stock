package com.xiaoka.cloud.stock.service.open.core;

import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudDepotEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceEntity;
import com.xiaoka.cloud.stock.core.stock.repo.entity.CloudPartPriceUncertainEntity;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierPartSyncParam;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierPartUncertainPrice;

import java.util.function.Predicate;

/**
 * @author zhouze
 * @date 2017/11/16
 * @see [相关类/方法]
 * @since [版本号]
 */
public class AssemblePredicate {

	/**
	 * 确定仓库相同判定条件
	 * 比较 cDepotId、shopId
	 *
	 * @param depotEntity
	 * @return
	 */
	public static Predicate<CloudDepotEntity> depotPredicate(CloudDepotEntity depotEntity) {
		return d -> d.getcDepotId().equals(depotEntity.getcDepotId())
				&& d.getShopId().equals(depotEntity.getShopId());
	}

	/**
	 * 确定仓库相同判定条件
	 * 比较 cDepotId、shopId
	 *
	 * @param cDepotId
	 * @param shopId
	 * @return
	 */
	public static Predicate<CloudDepotEntity> depotIdPredicate(String cDepotId, Integer shopId) {
		return d -> d.getcDepotId().equals(cDepotId) && d.getShopId().equals(shopId);
	}

	/**
	 * 确定配件相同判定条件
	 * 比较 cDepotId、shopId、cPartId
	 *
	 * @param partEntity
	 * @return
	 */
	public static Predicate<CloudPartEntity> partPredicate(CloudPartEntity partEntity) {
		return d -> d.getcPartId().equals(partEntity.getcPartId())
				&& d.getcDepotId().equals(partEntity.getcDepotId())
				&& d.getShopId().equals(partEntity.getShopId());
	}

	/**
	 * 确定配件相同判定条件
	 * 比较 cDepotId、shopId、cPartId
	 *
	 * @param cPart
	 * @return
	 */
	public static Predicate<CloudPartEntity> partParamPredicate(SupplierPartSyncParam cPart, Integer shopId) {
		return p -> p.getcPartId().equals(cPart.getPartId())
				&& p.getcDepotId().equals(cPart.getDepotId())
				&& p.getShopId().equals(shopId);
	}

	/**
	 * 确定价格相同判定条件
	 * 比较 cDepotId、shopId、cPartId
	 *
	 * @param priceEntity
	 * @return
	 */
	public static Predicate<CloudPartPriceEntity> pricePredicate(CloudPartPriceEntity priceEntity) {
		return d -> d.getcPartId().equals(priceEntity.getcPartId())
				&& d.getcDepotId().equals(priceEntity.getcDepotId())
				&& d.getShopId().equals(priceEntity.getShopId());
	}

	/**
	 * 确定价格相同判定条件
	 * 比较 cDepotId、shopId、cPartId
	 *
	 * @param cPart
	 * @param shopId
	 * @return
	 */
	public static Predicate<CloudPartPriceEntity> priceParamPredicate(SupplierPartSyncParam cPart, Integer shopId) {
		return p -> p.getcPartId().equals(cPart.getPartId())
				&& p.getcDepotId().equals(cPart.getDepotId())
				&& p.getShopId().equals(shopId);
	}

	/**
	 * 确定价格相同判定条件
	 * 比较 cDepotId、shopId、cPartId
	 *
	 * @param priceEntity
	 * @return
	 */
	public static Predicate<CloudPartPriceUncertainEntity> uncertainPricePredicate(CloudPartPriceUncertainEntity priceEntity) {
		return d -> d.getcPartId().equals(priceEntity.getcPartId())
				&& d.getcDepotId().equals(priceEntity.getcDepotId())
				&& d.getShopId().equals(priceEntity.getShopId())
				&& d.getPriceCode().equals(priceEntity.getPriceCode());
	}

	/**
	 *
	 * 确定价格相同判定条件
	 * 比较 cDepotId、shopId、cPartId
	 *
	 * @param cPart
	 * @param uncertainPrice
	 * @param shopId
	 * @return
	 */
	public static Predicate<CloudPartPriceUncertainEntity> uncertainPriceParamPredicate(SupplierPartSyncParam cPart,
																						SupplierPartUncertainPrice uncertainPrice,
																						Integer shopId) {
		return p -> p.getcPartId().equals(cPart.getPartId())
				&& p.getcDepotId().equals(cPart.getDepotId())
				&& p.getPriceCode().equals(uncertainPrice.getPriceCode())
				&& p.getShopId().equals(shopId);
	}


	private AssemblePredicate() {
	}
}

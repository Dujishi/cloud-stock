package com.xiaoka.cloud.stock.client.business.core.rongyi.dto;

import com.xiaoka.cloud.stock.client.business.core.IBuilder;

import java.io.Serializable;

/**
 * @author zhouze
 * @date 2018/1/18
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RongyiStockDto implements Serializable {
	private static final long serialVersionUID = -5519869578090749117L;

	/**
	 * 商品编码-外部配件id
	 */
	private String cPartId;
	/**
	 * 编码一
	 */
	private String code1;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 适用车型
	 */
	private String carModel;
	/**
	 * 产地
	 */
	private String originPlace;
	/**
	 * 规格
	 */
	private String standard;
	/**
	 * 单位
	 */
	private String unit;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 仓库
	 */
	private String depot;
	/**
	 * 实际库存
	 */
	private String balanceCount;
	/**
	 * 运营价、成本价
	 */
	private String costPrice;
	/**
	 * 提供给同行价格
	 */
	private String tradePrice;
	/**
	 * 提供给保险公司价
	 */
	private String insurancePrice;
	/**
	 * 提供给修理厂价格
	 */
	private String repairFactoryPrice;
	/**
	 * 维修站价/4S店价格
	 */
	private String repairStationPrice;

	public String getcPartId() {
		return cPartId;
	}

	public String getCode1() {
		return code1;
	}

	public String getName() {
		return name;
	}

	public String getCarModel() {
		return carModel;
	}

	public String getOriginPlace() {
		return originPlace;
	}

	public String getStandard() {
		return standard;
	}

	public String getUnit() {
		return unit;
	}

	public String getBrand() {
		return brand;
	}

	public String getDepot() {
		return depot;
	}

	public String getBalanceCount() {
		return balanceCount;
	}

	public String getCostPrice() {
		return costPrice;
	}

	public String getTradePrice() {
		return tradePrice;
	}

	public String getInsurancePrice() {
		return insurancePrice;
	}

	public String getRepairFactoryPrice() {
		return repairFactoryPrice;
	}

	public String getRepairStationPrice() {
		return repairStationPrice;
	}

	public RongyiStockDto(Builder builder) {
		cPartId = builder.cPartId;
		name = builder.name;
		carModel = builder.carModel;
		originPlace = builder.originPlace;
		standard = builder.standard;
		unit = builder.unit;
		brand = builder.brand;
		depot = builder.depot;
		balanceCount = builder.balanceCount;
		code1 = builder.code1;
		costPrice = builder.costPrice;
		tradePrice = builder.tradePrice;
		insurancePrice = builder.insurancePrice;
		repairFactoryPrice = builder.repairFactoryPrice;
		repairStationPrice = builder.repairStationPrice;
	}

	public static class Builder implements IBuilder<RongyiStockDto> {

		@Override
		public RongyiStockDto build() {
			return new RongyiStockDto(this);
		}

		/**
		 * 商品编码-外部配件id
		 */
		private String cPartId;
		/**
		 * 编码一
		 */
		private String code1;
		/**
		 * 名称
		 */
		private String name;
		/**
		 * 适用车型
		 */
		private String carModel;
		/**
		 * 产地
		 */
		private String originPlace;
		/**
		 * 规格
		 */
		private String standard;
		/**
		 * 单位
		 */
		private String unit;
		/**
		 * 品牌
		 */
		private String brand;
		/**
		 * 仓库
		 */
		private String depot;
		/**
		 * 实际库存
		 */
		private String balanceCount;
		/**
		 * 运营价、成本价
		 */
		private String costPrice;
		/**
		 * 提供给同行价格
		 */
		private String tradePrice;
		/**
		 * 提供给保险公司价
		 */
		private String insurancePrice;
		/**
		 * 提供给修理厂价格
		 */
		private String repairFactoryPrice;
		/**
		 * 维修站价/4S店价格
		 */
		private String repairStationPrice;

		public Builder cPartId(String cPartId) {
			this.cPartId = cPartId;
			return this;
		}

		public Builder code1(String code1) {
			this.code1 = code1;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder carModel(String carModel) {
			this.carModel = carModel;
			return this;
		}

		public Builder originPlace(String originPlace) {
			this.originPlace = originPlace;
			return this;
		}

		public Builder standard(String standard) {
			this.standard = standard;
			return this;
		}

		public Builder unit(String unit) {
			this.unit = unit;
			return this;
		}

		public Builder brand(String brand) {
			this.brand = brand;
			return this;
		}

		public Builder depot(String depot) {
			this.depot = depot;
			return this;
		}

		public Builder balanceCount(String balanceCount) {
			this.balanceCount = balanceCount;
			return this;
		}

		public Builder costPrice(String costPrice) {
			this.costPrice = costPrice;
			return this;
		}

		public Builder tradePrice(String tradePrice) {
			this.tradePrice = tradePrice;
			return this;
		}

		public Builder insurancePrice(String insurancePrice) {
			this.insurancePrice = insurancePrice;
			return this;
		}

		public Builder repairFactoryPrice(String repairFactoryPrice) {
			this.repairFactoryPrice = repairFactoryPrice;
			return this;
		}

		public Builder repairStationPrice(String repairStationPrice) {
			this.repairStationPrice = repairStationPrice;
			return this;
		}
	}
}

package com.xiaoka.cloud.stock.client.business.core.rongyi.dto;


import com.xiaoka.cloud.stock.client.business.core.IBuilder;

import java.io.Serializable;

/**
 * 荣意库存数据
 *
 * @author zhouze
 * @date 2018/1/9
 * @see [相关类/方法]
 * @since [版本号]
 */
public class RongyiPandianDto implements Serializable {
	private static final long serialVersionUID = -7557099410108956168L;

	/**
	 * 外部配件id
	 */
	private String cPartId;
	/**
	 * 零件码
	 */
	private String cOeNo;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 使用车型
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
	 * 金额
	 */
	private String price;
	/**
	 * 编码1
	 */
	private String code1;
	/**
	 * 编码2
	 */
	private String code2;

	public String getcPartId() {
		return cPartId;
	}

	public String getcOeNo() {
		return cOeNo;
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

	public String getPrice() {
		return price;
	}

	public String getCode1() {
		return code1;
	}

	public String getCode2() {
		return code2;
	}

	RongyiPandianDto(Builder builder) {
		cPartId = builder.cPartId;
		cOeNo = builder.cOeNo;
		name = builder.name;
		carModel = builder.carModel;
		originPlace = builder.originPlace;
		standard = builder.standard;
		unit = builder.unit;
		brand = builder.brand;
		depot = builder.depot;
		balanceCount = builder.balanceCount;
		price = builder.price;
		code1 = builder.code1;
		code2 = builder.code2;
	}

	public static class Builder implements IBuilder<RongyiPandianDto> {

		@Override
		public RongyiPandianDto build() {
			return new RongyiPandianDto(this);
		}

		/**
		 * 外部配件id
		 */
		private String cPartId;
		/**
		 * 零件码
		 */
		private String cOeNo;
		/**
		 * 名称
		 */
		private String name;
		/**
		 * 使用车型
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
		 * 金额
		 */
		private String price;
		/**
		 * 编码1
		 */
		private String code1;
		/**
		 * 编码2
		 */
		private String code2;

		public Builder cPartId(String cPartId) {
			this.cPartId = cPartId;
			return this;
		}

		public Builder cOeNo(String cOeNo) {
			this.cOeNo = cOeNo;
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

		public Builder price(String price) {
			this.price = price;
			return this;
		}

		public Builder code1(String code1) {
			this.code1 = code1;
			return this;
		}

		public Builder code2(String code2) {
			this.code2 = code2;
			return this;
		}
	}

}

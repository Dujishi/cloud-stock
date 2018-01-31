package com.xiaoka.cloud.stock.service.stock.vo;

import com.xiaoka.cloud.stock.service.stock.constant.StockFlagEnum;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouze
 * @date 2017/11/17
 * @see [相关类/方法]
 * @since [版本号]
 */
public class CloudStockPageVo implements Serializable {
	private static final long serialVersionUID = 5476837926483936407L;

	/**
	 * 云仓库存
	 */
	private CloudStockInfoVo        stockInfo;
	/**
	 * 有货的其他配件商
	 */
	private RecommendSupplierInfoVo recommendSupplierInfo;
	/**
	 * 替换件编码
	 */
	private List<String>            replaceCodeList;
	/**
	 * 标记 售罄、暂无、正常
	 * {@link StockFlagEnum}
	 */
	private Integer flag;

	public CloudStockInfoVo getStockInfo() {
		return stockInfo;
	}

	public void setStockInfo(CloudStockInfoVo stockInfo) {
		this.stockInfo = stockInfo;
	}

	public RecommendSupplierInfoVo getRecommendSupplierInfo() {
		return recommendSupplierInfo;
	}

	public void setRecommendSupplierInfo(RecommendSupplierInfoVo recommendSupplierInfo) {
		this.recommendSupplierInfo = recommendSupplierInfo;
	}

	public List<String> getReplaceCodeList() {
		return replaceCodeList;
	}

	public void setReplaceCodeList(List<String> replaceCodeList) {
		this.replaceCodeList = replaceCodeList;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}
}

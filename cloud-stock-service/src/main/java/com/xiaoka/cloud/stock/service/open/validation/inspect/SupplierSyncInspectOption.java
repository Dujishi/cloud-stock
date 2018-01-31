package com.xiaoka.cloud.stock.service.open.validation.inspect;

import com.alibaba.fastjson.JSON;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiConstants;
import com.xiaoka.cloud.stock.service.open.constant.OpenApiReturnCodeEnum;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierPartSyncParam;
import com.xiaoka.cloud.stock.service.open.dto.param.SupplierSyncParam;
import com.xiaoka.cloud.stock.service.open.validation.constraint.CheckLegalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author zhouze
 * @date 2017/11/19
 * @see [相关类/方法]
 * @since [版本号]
 */
@Component
public class SupplierSyncInspectOption extends ObjectInspectState {

	private static final Logger LOGGER = LoggerFactory.getLogger(StringInspectOption.class);

	@Override
	void checkObjectParameter(Object object) {
		if (null == object) {
			throw OpenApiReturnCodeEnum.FAIL_PARAM_ILLEGAL.ifApiException();
		}
		if (object instanceof SupplierSyncParam) {
			SupplierSyncParam supplierSyncParam = (SupplierSyncParam) object;
			LOGGER.info("开始校验SupplierSyncParam，对象值为:{}", JSON.toJSONString(supplierSyncParam));

			checkLegalContent(supplierSyncParam);
		} else {
			LOGGER.error("不支持将该类型转成SupplierSyncParam，fail :{}", object.getClass().getTypeName());
			throw OpenApiReturnCodeEnum.FAIL_EXCEPTION.ifApiException();
		}
	}

	/**
	 * 具体内容校验
	 *
	 * @param supplierSyncParam
	 */
	private void checkLegalContent(SupplierSyncParam supplierSyncParam) {
		CheckLegalUtil.stringLegal(supplierSyncParam.getSupplierId(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("supplierId"));
		CheckLegalUtil.listLegal(supplierSyncParam.getParts(), OpenApiConstants.MAX_ARRAY_LENGTH, doLog("parts"));

		int row = 0;
		for (SupplierPartSyncParam partSyncParam : supplierSyncParam.getParts()) {
			int finalIndex = row;
			CheckLegalUtil.stringLegal(partSyncParam.getDepotId(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("depotId", finalIndex));
			CheckLegalUtil.stringLegal(partSyncParam.getPartId(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("partId", finalIndex));
			CheckLegalUtil.stringLegal(partSyncParam.getOeNo(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("oeNo", finalIndex));
			CheckLegalUtil.stringLegal(partSyncParam.getPartName(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("partName", finalIndex));
			CheckLegalUtil.stringLegal(partSyncParam.getPartBrand(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("partBrand", finalIndex));

			CheckLegalUtil.bigDecimalLegal(partSyncParam.getBalanceCount(), doLog("balanceCount", finalIndex));
			CheckLegalUtil.bigDecimalLegal(partSyncParam.getTradePrice(), doLog("tradePrice", finalIndex));

			CheckLegalUtil.stringLengthLegal(partSyncParam.getManufacturer(), OpenApiConstants.MAX_STRING_LENGTH_200, doLog("manufacturer", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getOriginPlace(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("originPlace", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getDepotCode(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("depotCode", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getDepotName(), OpenApiConstants.MAX_STRING_LENGTH_100, doLog("depotName", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getDepotProvince(), OpenApiConstants.MAX_STRING_LENGTH_50, doLog("depotProvince", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getDepotCity(), OpenApiConstants.MAX_STRING_LENGTH_50, doLog("depotCity", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getDepotAddress(), OpenApiConstants.MAX_STRING_LENGTH_200, doLog("depotAddress", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getCreateTime(), OpenApiConstants.MAX_STRING_LENGTH_200, doLog("createTime", finalIndex));
			CheckLegalUtil.stringLengthLegal(partSyncParam.getUpdateTime(), OpenApiConstants.MAX_STRING_LENGTH_200, doLog("updateTime", finalIndex));

			row++;
		}
	}


}

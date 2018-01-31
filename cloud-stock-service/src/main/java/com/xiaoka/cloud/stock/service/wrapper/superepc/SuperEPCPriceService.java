package com.xiaoka.cloud.stock.service.wrapper.superepc;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.core.util.ListUtil;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAdapterModelsResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetOePriceResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.SuperEPCResp;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.http.HttpExecutor;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suqin on 16/11/2017.
 */
@Service
public class SuperEPCPriceService {

	private static final Logger logger = LoggerFactory.getLogger(SuperEPCPriceService.class);

	/**
	 * <pre>
	 *     原厂4S零售价格<br/>
	 *     根据零件号查询原厂4S店的价格
	 *     可以只通过配件号查询，
	 *     也可以加上厂商、品牌来缩小查询范围
	 * </pre>
	 *
	 * @param partCodes
	 * @param carBrand  车品牌
	 * @param carOem    厂商
	 * @return
	 */
	public List<GetOePriceResp> getOEPrice(List<String> partCodes, String carBrand, String carOem) {
		if (CollectionUtils.isEmpty(partCodes)) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		HttpExecutor.GetExecutor executor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_7)
		                                         .addParam(SuperEPCconstant.PARAM_grant_code,
				                                         SuperEPCconstant.GRANT_CODE)
		                                         .addParam(SuperEPCconstant.PARAM_part_code,
				                                         ListUtil.toStringWithoutBracket(partCodes));

		if (StringUtils.isNotEmpty(carBrand)) {
			executor.addParam(SuperEPCconstant.PARAM_car_brand, carBrand);
		}
		if (StringUtils.isNotEmpty(carOem)) {
			executor.addParam(SuperEPCconstant.PARAM_car_oem, carOem);
		}
		String result = executor.executeAsString();
		if (StringUtils.isEmpty(result)) {
			logger.warn("URL3.7 getOEPrice with partCodes={}, carBrand={}, carOem={} return null;", Jackson.base().writeValueAsString(partCodes),
					carBrand, carOem);
			return null;
		}
		SuperEPCResp<List<GetOePriceResp>> getOePriceOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetOePriceResp>>>() {
				});
		return getOePriceOutput.getResult();
	}

	public static void main(String[] args) {

//		System.out.println(
//				Jackson.base().writeValueAsString(getOEPrice(Arrays.asList("Q40510F31", "Q40508"), null, null)));
//		System.out.println(
//				Jackson.base().writeValueAsString(getOEPrice(Arrays.asList("Q40510F31"), "北汽", "北京汽车")));
//		System.out.println(
//				Jackson.base().writeValueAsString(getOEPrice(Arrays.asList("Q40510F31"), null, "北汽制造")));

	}
}

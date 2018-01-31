package com.xiaoka.cloud.stock.service.wrapper.superepc;

import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.core.util.ListUtil;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.*;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.http.HttpExecutor;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sabo on 16/11/2017.
 */
@Service
public class SuperEPCCarModelService {

	private Logger logger = LoggerFactory.getLogger(SuperEPCCarModelService.class);

	/**
	 * 查询厂商、品牌和首字母
	 * @return
	 */
	public List<GetAllSeriesResp> getAllSeries() {
		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_1_2)
		                     .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE).executeAsString();
		SuperEPCResp<List<GetAllSeriesResp>> getAllSeriesOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetAllSeriesResp>>>() {
				});
		return getAllSeriesOutput.getResult();
	}

	/**
	 * <pre>
	 *     根据厂商品牌及其他条件查询车款信息
	 *     <br/>
	 *     oemBrand:车品牌，必填
	 *     oemName:厂商，必填
	 *     series:车系，选填
	 *     makeYear:年款，选填
	 *     transmission:变速器，选填
	 *     displacement:排量，选填
	 * </pre>
	 *
	 * @param oemBrand
	 * @param oemName
	 * @param series
	 * @param makeYear
	 * @param transmission
	 * @param displacement
	 * @return
	 */
	public List<GetCarModelByVinResp> getCarModelByVin(String oemBrand, String oemName, String series,
			String makeYear, String transmission, String displacement) {
		if (StringUtils.isEmpty(oemBrand) || StringUtils.isEmpty(oemName)) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		HttpExecutor.GetExecutor executor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_1_3)
		                                         .addParam(SuperEPCconstant.PARAM_grant_code,
				                                         SuperEPCconstant.GRANT_CODE)
		                                         .addParam(SuperEPCconstant.PARAM_oem_brand, oemBrand)
		                                         .addParam(SuperEPCconstant.PARAM_oem_name, oemName);

		if (StringUtils.isNotEmpty(series)) {
			executor.addParam(SuperEPCconstant.PARAM_series, series);
		}
		if (StringUtils.isNotEmpty(makeYear)) {
			executor.addParam(SuperEPCconstant.PARAM_make_year, makeYear);
		}
		if (StringUtils.isNotEmpty(transmission)) {
			executor.addParam(SuperEPCconstant.PARAM_transmission, transmission);
		}
		if (StringUtils.isNotEmpty(displacement)) {
			executor.addParam(SuperEPCconstant.PARAM_displacement, displacement);
		}
		String result = executor.executeAsString();

		SuperEPCResp<List<GetCarModelByVinResp>> getCarModelByVinOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetCarModelByVinResp>>>() {
				});
		return getCarModelByVinOutput.getResult();
	}

	/**
	 * <pre>
	 *     查询车款配置信息<br/>
	 *     由一个或者多个TID查询车款信息以及车款配置信息
	 * </pre>
	 *
	 * @param tids
	 * @return
	 */
	public List<GetCarModelInfoByTidResp> getCarModelInfoByTid(List<Integer> tids) {
		logger.info("start getCarModelInfoByTid with tids=={}", Jackson.mobile().writeValueAsString(tids));
		if (CollectionUtils.isEmpty(tids)) {
			throw new IllegalArgumentException("必要参数不能为空");
		}

		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_1_4)
		                     .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE)
		                     .addParam(SuperEPCconstant.PARAM_tid, ListUtil.toStringWithoutBracket(tids)).executeAsString();

		logger.info("getCarModelInfoByTid result=={}", result);

		SuperEPCResp<List<GetCarModelInfoByTidResp>> getCarModelInfoByTidOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetCarModelInfoByTidResp>>>() {
				});
		return getCarModelInfoByTidOutput.getResult();
	}

	public static void main(String[] args) {
		//		System.out.println(Jackson.base().writeValueAsString(getAllSeries()));

		//		System.out.println(Jackson.base().writeValueAsString(getCarModelByVin("福特", "长安福特", "蒙迪欧（2013-", "2017", "手自一体变速器", "1.5T")));

//		System.out.println(Jackson.base().writeValueAsString(getCarModelInfoByTid(Arrays.asList(7998, 8000))));
	}

}

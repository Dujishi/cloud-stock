package com.xiaoka.cloud.stock.service.wrapper.superepc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAllCarModelResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTimerAssemblyListResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTimerStandardPartsResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.SuperEPCResp;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.http.HttpExecutor;
import com.xiaoka.freework.utils.json.Jackson;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by suqin on 16/11/2017.
 */
@Service
public class SuperEPCCatalogueService {

	/**
	 * <pre>
	 *     车款查询（整表）<br/>
	 *     分页查询整表车款信息，一次查询1000
	 *     offset为偏移量，limit offset, 1000
	 * </pre>
	 * @param offset 偏移量
	 * @return
	 */
	public List<GetAllCarModelResp> getAllCarModel(Integer offset) {
		if (offset == null) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_2_1)
		                     .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE)
		                     .addParam(SuperEPCconstant.PARAM_offset, String.valueOf(offset)).executeAsString();
		SuperEPCResp<List<GetAllCarModelResp>> getAllCarModelOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetAllCarModelResp>>>() {
				});
		return getAllCarModelOutput.getResult();
	}

	/**
	 * <pre>
	 *     查询正时总成、分总成分类目录<br/>
	 *     获取所有的正时总成分类目录
	 * </pre>
	 * @return
	 */
	public List<GetTimerAssemblyListResp> getTimerAccemblyList() {
		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_2_2)
		                     .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE)
		                     .executeAsString();
		SuperEPCResp<List<GetTimerAssemblyListResp>> getTimerAccemblyListOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetTimerAssemblyListResp>>>() {
				});
		return getTimerAccemblyListOutput.getResult();
	}

	/**
	 * <pre>
	 *     查询正时标准配件分类目录（整合）<br/>
	 *     获取所有的正时标准配件
	 * </pre>
	 * @return
	 */
	public List<GetTimerStandardPartsResp> getTimerStandardParts() {
		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_2_3)
		                     .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE)
		                     .executeAsString();
		SuperEPCResp<List<GetTimerStandardPartsResp>> getTimerStandardPartsOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetTimerStandardPartsResp>>>() {
				});
		return getTimerStandardPartsOutput.getResult();
	}

	public static void main(String[] args) {
		//		System.out.println(Jackson.base().writeValueAsString(getAllCarModel(0)));
		//		System.out.println(Jackson.base().writeValueAsString(getTimerAccemblyList()));
//		System.out.println(Jackson.base().writeValueAsString(getTimerStandardParts()));
	}

}

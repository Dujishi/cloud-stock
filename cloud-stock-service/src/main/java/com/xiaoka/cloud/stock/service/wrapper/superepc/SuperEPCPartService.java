package com.xiaoka.cloud.stock.service.wrapper.superepc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetAdapterModelsResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetJudgePartCodeResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetReplacePartsResp;
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
 * Created by sabo on 16/11/2017.
 */
@Service
public class SuperEPCPartService {

	private static final Logger logger = LoggerFactory.getLogger(SuperEPCPartService.class);

	/**
	 * <pre>
	 *     查询配件适配车款<br/>
	 *     此处分为原厂/品牌
	 *     如果使用原厂配件查询，则返回type=原厂，part_brand值为空
	 *     如果使用品牌配件查询，则返回type=品牌，并返回part_brand
	 * </pre>
	 *
	 * @param partCode
	 * @return
	 */
	public List<GetAdapterModelsResp> getAdapterModels(String partCode) {
		if (StringUtils.isEmpty(partCode)) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_6)
		                     .addParam(SuperEPCconstant.PARAM_grant_code,
				                     SuperEPCconstant.GRANT_CODE)
		                     .addParam(SuperEPCconstant.PARAM_part_code, partCode)
		                     .executeAsString();

		if (StringUtils.isEmpty(result)) {
			logger.warn("URL3.6 getAdapterModels by partCode=={} return null", partCode);
		}
		SuperEPCResp<List<GetAdapterModelsResp>> getAdapterModelsOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetAdapterModelsResp>>>() {
				});
		return getAdapterModelsOutput.getResult();
	}

	/**
	 * 查询原厂及品牌替换件，返回原厂替换件信息以及品牌替换件信息。原厂替换件信息对应结果中type为2的结果，品牌替换件信息对应type为3的结果。
	 *
	 * @param gpId     GP_ID（三选一）
	 * @param partCode 配件号（三选一）
	 * @param bpId     BP_ID（三选一）
	 * @return
	 */
	public GetReplacePartsResp getReplaceParts(String gpId, String partCode, String bpId) {
		if (StringUtils.isBlank(gpId) && StringUtils.isBlank(partCode) && StringUtils.isBlank(bpId)) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		HttpExecutor.GetExecutor getExecutor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_9)
		                                            .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE);
		if (StringUtils.isNotBlank(gpId)) {
			getExecutor.addParam(SuperEPCconstant.PARAM_gp_id, gpId.trim());
		} else if (StringUtils.isNotBlank(partCode)) {
			getExecutor.addParam(SuperEPCconstant.PARAM_part_code, partCode.trim());
		} else if (StringUtils.isNotBlank(bpId)) {
			getExecutor.addParam(SuperEPCconstant.PARAM_bp_id, bpId.trim());
		}
		String result = getExecutor.executeAsString();
		if (StringUtils.isEmpty(result)) {
			logger.warn("URL_3_9 getReplaceParts by gpId={}, partCode=={},bpId={}  return null", gpId, partCode, bpId);
			return null;
		}
		GetReplacePartsResp getReplacePartsResp = Jackson.base().readValue(getExecutor.executeAsString(), GetReplacePartsResp.class);
		return getReplacePartsResp;

	}

	public List<GetJudgePartCodeResp> judgePartCode(String partCode){
		if (StringUtils.isBlank(partCode)) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		HttpExecutor.GetExecutor getExecutor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_4_1)
		                                            .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE);
		getExecutor.addParam(SuperEPCconstant.PARAM_part_code, partCode.trim());
		SuperEPCResp<List<GetJudgePartCodeResp>> getJudgePartCodeRespOutput = Jackson.base().readValue(getExecutor.executeAsString(),
				new TypeReference<SuperEPCResp<List<GetJudgePartCodeResp>>>() {
				});
		return getJudgePartCodeRespOutput.getResult();
	}


	public static void main(String[] args) {

//		System.out.println(Jackson.base().writeValueAsString(getAdapterModels("DG9T 14A094 GA")));
//		System.out.println(Jackson.base().writeValueAsString(getReplaceParts(null, "A0041594903", null)));


	}
}

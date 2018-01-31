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

import java.util.List;
import java.util.Objects;

/**
 * Created by sabo on 16/11/2017.
 */
@Service
public class SuperEPCAdaptationService {

	private Logger logger = LoggerFactory.getLogger(SuperEPCAdaptationService.class);

	/**
	 * <pre>
	 *     查询正时标准配件分类目录
	 *     <br/>
	 *     根据tid查询正时标准配件的分类或原厂分类
	 *     通过type（1:正时 0：原厂）区分
	 * </pre>
	 *
	 * @param tid  车款id
	 * @param type 类型
	 * @return
	 */
	public List<GetAssemblyCatalogueResp> getAssemblyCatalogue(Integer tid, Integer type) {
		if (tid == null || type == null) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		if (type != 0 && type != 1) {
			throw new IllegalArgumentException("不支持的未知类型");
		}
		String result = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_1)
		                     .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE)
		                     .addParam(SuperEPCconstant.PARAM_tid, String.valueOf(tid))
		                     .addParam(SuperEPCconstant.PARAM_type, String.valueOf(type))
		                     .executeAsString();
		logger.info("SuperEPCAdaptationService.getAssemblyCatalogue result==={}", result);
		if (StringUtils.isBlank(result)) {
			return null;
		}
		SuperEPCResp<List<GetAssemblyCatalogueResp>> getAssemblyCatalogueOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetAssemblyCatalogueResp>>>() {
				});
		return getAssemblyCatalogueOutput.getResult();
	}

	/**
	 * <pre>
	 *     查询总成分总成下的图片信息
	 *     <br/>
	 *     tid:车款id，查询配件相关必须带上车款
	 *     type:原厂和正时（1：正时0：原厂）
	 *     assembly:原厂总成名
	 *     sub_assembly:原厂分总成
	 *     sub_assembly_id:正时分总成id
	 *     其中：原厂总成和分总成/正时分总成id，二选一
	 * </pre>
	 *
	 * @param tid           车款id
	 * @param type
	 * @param assembly      原厂总成
	 * @param subAssembly   原厂分总成
	 * @param subAssemblyId 正时分总成id
	 * @return
	 */
	public List<GetPicInfoResp> getPicInfo(Integer tid, Integer type, String assembly,
			String subAssembly, Integer subAssemblyId) {
		if (tid == null || type == null || (subAssemblyId == null && (StringUtils.isEmpty(assembly) || StringUtils
				.isEmpty(subAssembly)))) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		if (type != 0 && type != 1) {
			throw new IllegalArgumentException("不支持的未知类型");
		}
		HttpExecutor.GetExecutor executor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_2)
		                                         .addParam(SuperEPCconstant.PARAM_grant_code,
				                                         SuperEPCconstant.GRANT_CODE)
		                                         .addParam(SuperEPCconstant.PARAM_tid, String.valueOf(tid))
		                                         .addParam(SuperEPCconstant.PARAM_type, String.valueOf(type));

		if (subAssemblyId == null) {
			executor.addParam(SuperEPCconstant.PARAM_assembly, assembly)
			        .addParam(SuperEPCconstant.PARAM_sub_assembly, subAssembly);
		} else {
			executor.addParam(SuperEPCconstant.PARAM_sub_assembly_id, String.valueOf(subAssemblyId));
		}
		String result = executor.executeAsString();

		SuperEPCResp<List<GetPicInfoResp>> getPicInfoOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetPicInfoResp>>>() {
				});
		return getPicInfoOutput.getResult();
	}

	public List<GetPicInfoForAssemblyResp> getPicInfoForAssembly(Integer tid, Integer type, String assembly, Integer assemblyId){
		if (tid == null || type == null || (assemblyId == null && (StringUtils.isEmpty(assembly)))) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		if (type != 0 && type != 1) {
			throw new IllegalArgumentException("不支持的未知类型");
		}
		HttpExecutor.GetExecutor executor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_2_1)
		                                         .addParam(SuperEPCconstant.PARAM_grant_code,
				                                         SuperEPCconstant.GRANT_CODE)
		                                         .addParam(SuperEPCconstant.PARAM_tid, String.valueOf(tid))
		                                         .addParam(SuperEPCconstant.PARAM_type, String.valueOf(type));
		if (Objects.nonNull(assemblyId)){
			executor.addParam(SuperEPCconstant.PARAM_assembly_id, String.valueOf(assemblyId));
		}
		if (StringUtils.isNotBlank(assembly)){
			executor.addParam(SuperEPCconstant.PARAM_assembly, assembly);
		}
		String result = executor.executeAsString();

		SuperEPCResp<List<GetPicInfoForAssemblyResp>> getPicInfoOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetPicInfoForAssemblyResp>>>() {
				});
		return getPicInfoOutput.getResult();
	}

	/**
	 * <pre>
	 *     查询配件信息<br/>
	 *     可以根据：
	 *     timerIds(正时标准配件id)查询正时标准配件对于的配件信息，可多个
	 *     partCode(配件编号)查询具体配件，只能一个
	 *     picNum(图片编码)查询图下所有配件，可多个
	 *     以上条件：三选一
	 * </pre>
	 *
	 * @param tid
	 * @param timerIds 正时标准配件id
	 * @param partCode 配件编号
	 * @param picNums  图号
	 * @return
	 */
	public List<GetPartsInfoResp> getPartsInfo(Integer tid, List<Integer> timerIds, String partCode,
			List<String> picNums) {
		if (tid == null || (CollectionUtils.isEmpty(timerIds) && StringUtils.isEmpty(partCode) && CollectionUtils
				.isEmpty(picNums))) {
			throw new IllegalArgumentException("必要参数不能为空");
		}
		HttpExecutor.GetExecutor executor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_3_3)
		                                         .addParam(SuperEPCconstant.PARAM_grant_code,
				                                         SuperEPCconstant.GRANT_CODE)
		                                         .addParam(SuperEPCconstant.PARAM_tid, String.valueOf(tid));

		if (CollectionUtils.isNotEmpty(timerIds)) {
			executor.addParam(SuperEPCconstant.PARAM_timer_id, ListUtil.toStringWithoutBracket(timerIds));
		} else if (StringUtils.isNotEmpty(partCode)) {
			executor.addParam(SuperEPCconstant.PARAM_part_code, partCode);
		} else {
			executor.addParam(SuperEPCconstant.PARAM_pic_num, ListUtil.toStringWithoutBracket(picNums));
		}
		String result = executor.executeAsString();

		SuperEPCResp<List<GetPartsInfoResp>> getPartsInfoOutput = Jackson.base().readValue(result,
				new TypeReference<SuperEPCResp<List<GetPartsInfoResp>>>() {
				});
		return getPartsInfoOutput.getResult();
	}

	public static void main(String[] args) {
//		SuperEPCAdaptationService a = new SuperEPCAdaptationService();
		//		System.out.println(Jackson.base().writeValueAsString(getAssemblyCatalogue(7998, 1)));

		//		System.out.println(Jackson.base().writeValueAsString(getPicInfo(7998, 1, null, null, 134)));
		//		System.out.println(Jackson.base().writeValueAsString(getPicInfo(7998, 0, "J 电气系统", "保险、保险座与断路器", null)));

//		List<GetAssemblyCatalogueResp> catalogueResps = a.getAssemblyCatalogue(7998, 0);
//		catalogueResps.forEach(x -> {
//			System.out
//					.println(Jackson.base().writeValueAsString(
//							a.getPicInfo(7998, 0, x.getAssembly(), x.getSubAssembly(), null)));
//		});
		//				System.out
		//						.println(Jackson.base().writeValueAsString(a.getPartsInfo(7998, Arrays.asList(1442, 1448), null, null)));
		//		System.out.println(Jackson.base().writeValueAsString(getPartsInfo(7998, null, "DG9T 14A094 GA", null)));
		//		System.out.println(Jackson.base().writeValueAsString(getPartsInfo(7998, null, null, "J02.051")));
	}
}

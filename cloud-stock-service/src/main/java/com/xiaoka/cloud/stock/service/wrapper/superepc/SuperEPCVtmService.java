package com.xiaoka.cloud.stock.service.wrapper.superepc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetTidResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.SuperEPCResp;
import com.xiaoka.freework.utils.Utils;
import com.xiaoka.freework.utils.http.HttpExecutor;
import com.xiaoka.freework.utils.json.Jackson;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sabo on 16/11/2017.
 */
@Service
public class SuperEPCVtmService {

	/**
	 * 根据Vin码查询TID(车款ID)
	 * 针对大众情况无法查询到TID时，用is_original=1，返回车型描述信息，用于原厂配件的查询
	 * @param vin
	 * @param isOriginal
	 * @return
	 *
	 <pre>
	    情况一（在正时车型库范围内的车款）：TID（一个或多个）  对应statusCode: 值： 200
		情况二（大众车款，且为原厂配件查询）：车型描述  对应statusCode: 值： 100
		情况三（05年以前车款，不支持查询！）返回：厂商、品牌、车系、排量、变速器、车身形式、驱动方式、燃油形式    对应statusCode: 值： -50
		情况四（表示平行进口车VIN码，暂不支持查询！）返回： 厂商、品牌、车系、排量 。对应statusCode: 值： -70
		情况五（进口车VIN3）：厂商、品牌 对应statusCode: 值： -80
		情况六（VIN无法识别）：该VIN暂未收入 对应statusCode: 值： -2
		情况七（商用车）：该VIN为商用车暂不开放 对应statusCode: 值：-3
		情况八（VIN错误）：输入的VIN码有误  对应statusCode: 值：-1
	</pre>
	 */
	public SuperEPCResp<List<GetTidResp>> getTid(String vin, String isOriginal) {
		HttpExecutor.GetExecutor getExecutor = Utils.get(HttpExecutor.class).get(SuperEPCconstant.URL_1_1)
		                                .addParam(SuperEPCconstant.PARAM_grant_code, SuperEPCconstant.GRANT_CODE)
		                                .addParam(SuperEPCconstant.PARAM_vin, vin);
		if (StringUtils.isNotBlank(isOriginal)) {
			getExecutor.addParam(SuperEPCconstant.PARAM_is_original, isOriginal);
		}
		String result = getExecutor.executeAsString();
		SuperEPCResp<List<GetTidResp>> getTidRespSuperEPCResp = Jackson.base().readValue(result, new TypeReference<SuperEPCResp<List<GetTidResp>>>(){});
//		if (getTidRespSuperEPCResp.getResult() != null && StringUtils.isNotBlank(getTidRespSuperEPCResp.getResult().getTid())) {
//			String[] tids = getTidRespSuperEPCResp.getResult().getTid().split(SEP);
//			getTidRespSuperEPCResp.getResult().setTids(Arrays.stream(tids).map(Integer::valueOf).collect(Collectors.toList()));
//		}
		return getTidRespSuperEPCResp;
	}


}

package com.xiaoka.cloud.stock.service.epc;

import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.*;

import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/28 22:42
 * @see [相关类/方法]
 * @since [版本号]
 */
public interface SuperEpcService {

	List<GetAssemblyCatalogueResp> getAssemblyCatalogue(Integer tid, Integer type);

	List<GetPicInfoResp> getPicInfo(Integer tid, Integer type, String assembly, String subAssembly, Integer subAssemblyId);

	List<GetPartsInfoResp> getPartsInfo(Integer tid, List<Integer> timerIds, String partCode, List<String> picNums);

	SuperEPCResp<List<GetTidResp>> getTid(String vin, String isOriginal);

	List<GetCarModelInfoByTidResp> getCarModelInfoByTid(List<Integer> tids);

	List<GetAdapterModelsResp> getAdapterModels(String partCode);

	List<GetOePriceResp> getOEPrice(List<String> partCodes, String carBrand, String carOem);

	GetReplacePartsResp getReplaceParts(String gpId, String partCode, String bpId);

	List<GetPicInfoForAssemblyResp> getPicInfoForAssembly(Integer tid, Integer type, String assembly, Integer assemblyId);
}

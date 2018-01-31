package com.xiaoka.cloud.stock.service.epc.impl;

import com.xiaoka.cloud.stock.service.epc.SuperEpcService;
import com.xiaoka.cloud.stock.service.epc.constant.CacheConstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.*;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.*;
import com.xiaoka.freework.cache.annotation.ServiceCache;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Do something
 *
 * @author gancao 2017/11/28 22:43
 * @see [相关类/方法]
 * @since [版本号]
 */
@Service
public class SuperEpcServiceImpl implements SuperEpcService {

	@Resource
	private SuperEPCAdaptationService superEPCAdaptationService;
	@Resource
	private SuperEPCCarModelService superEPCCarModelService;
	@Resource
	private SuperEPCVtmService superEPCVtmService;
	@Resource
	private SuperEPCPriceService superEPCPriceService;
	@Resource
	private SuperEPCPartService superEPCPartService;

	@Override
	public List<GetAssemblyCatalogueResp> getAssemblyCatalogue(Integer tid, Integer type) {
		return superEPCAdaptationService.getAssemblyCatalogue(tid, type);
	}

	@Override
	public List<GetPicInfoResp> getPicInfo(Integer tid, Integer type, String assembly, String subAssembly, Integer subAssemblyId) {
		return superEPCAdaptationService.getPicInfo(tid, type, assembly, subAssembly, subAssemblyId);
	}

	@Override
	//@ServiceCache(includeKeys = {"tid", "type", "timerIds", "partCode", "picNums"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public List<GetPartsInfoResp> getPartsInfo(Integer tid, List<Integer> timerIds, String partCode, List<String> picNums) {
		return superEPCAdaptationService.getPartsInfo(tid, timerIds, partCode, picNums);
	}

	@Override
	//@ServiceCache(includeKeys = {"vin"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public SuperEPCResp<List<GetTidResp>> getTid(String vin, String isOriginal) {
		return superEPCVtmService.getTid(vin, isOriginal);
	}

	@Override
	//@ServiceCache(includeKeys = {"tids"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public List<GetCarModelInfoByTidResp> getCarModelInfoByTid(List<Integer> tids) {
		return superEPCCarModelService.getCarModelInfoByTid(tids);
	}

	@Override
	//@ServiceCache(includeKeys = {"partCode"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public List<GetAdapterModelsResp> getAdapterModels(String partCode) {
		return superEPCPartService.getAdapterModels(partCode);
	}

	@Override
	//@ServiceCache(includeKeys = {"partCodes", "carBrand", "carOem"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public List<GetOePriceResp> getOEPrice(List<String> partCodes, String carBrand, String carOem) {
		return superEPCPriceService.getOEPrice(partCodes, carBrand, carOem);
	}

	@Override
	//@ServiceCache(includeKeys = {"partCode"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public GetReplacePartsResp getReplaceParts(String gpId, String partCode, String bpId) {
		return superEPCPartService.getReplaceParts(gpId, partCode, bpId);
	}

	@Override
	//@ServiceCache(includeKeys = {"tid","type","assembly","assemblyId"}, expire = CacheConstant.EPC_EXPIRE_TIME)
	public List<GetPicInfoForAssemblyResp> getPicInfoForAssembly(Integer tid, Integer type, String assembly, Integer assemblyId) {
		return superEPCAdaptationService.getPicInfoForAssembly(tid, type, assembly, assemblyId);
	}
}

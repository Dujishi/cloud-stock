package com.xiaoka.cloud.stock.service.crawl.superepc;

import com.google.common.collect.Lists;
import com.xiaoka.cloud.stock.core.epc.repo.PartReplaceRepo;
import com.xiaoka.cloud.stock.core.epc.repo.entity.PartReplaceEntity;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCPartService;
import com.xiaoka.cloud.stock.service.wrapper.superepc.SuperEPCconstant;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetReplacePartsResp;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetReplacePartsRespList;
import com.xiaoka.cloud.stock.service.wrapper.superepc.resp.GetReplacePartsRespListResult;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 该服务用于爬取替换件
 * <p>
 * <pre>
 *
 *
 *
 * </pre>
 * <p>
 * <p>
 * Created by sabo on 21/11/2017.
 */
@Service
public class ReplacePartCrawlService {

	private static final Logger logger = LoggerFactory.getLogger(ReplacePartCrawlService.class);

	@Resource
	SuperEPCPartService superEPCPartService;

	@Resource
	PartReplaceRepo partReplaceRepo;

	public void crawlReplacePart(String partCode) {
		logger.info("Prepare craw replace part for partCode=={} ", partCode);
		GetReplacePartsResp getReplacePartsResp = superEPCPartService.getReplaceParts(null, partCode, null);
		if (getReplacePartsResp == null || !SuperEPCconstant.GET_REPLACE_PARTS_RESP_DESC.equals(getReplacePartsResp.getDesc()) || CollectionUtils
				.isEmpty(getReplacePartsResp.getList())) {
			logger.info("No found replace part for part_code=={}", partCode);
			return;
		}
		List<PartReplaceEntity> partReplaceEntityList = convertGetReplacePartsResp(partCode, getReplacePartsResp.getList());
		if (CollectionUtils.isEmpty(partReplaceEntityList)) {
			logger.info("No found replace part for part_code=={}", partCode);
			return;
		}

		List<PartReplaceEntity> insertList = Lists.newArrayList();
		//去重逻辑
		List<PartReplaceEntity> hasExistedList = partReplaceRepo.selectByCodeList(partReplaceEntityList);
		if (CollectionUtils.isNotEmpty(hasExistedList)) {
			insertList.addAll(partReplaceEntityList.stream()
					.filter(p -> hasExistedList.stream().noneMatch(x -> checkReplaceExisted(p, x)))
					.collect(Collectors.toList()));
		} else {
			insertList.addAll(partReplaceEntityList);
		}

		if (CollectionUtils.isNotEmpty(insertList)) {
			int result = partReplaceRepo.batchInsert(insertList);
			logger.info("Successfully batch insert replace part {} rows for partCode=={}!!! ", result, partCode);
		}
	}

	private boolean checkReplaceExisted(PartReplaceEntity p, PartReplaceEntity x) {
		return Objects.equals(x.getPartCode(), p.getPartCode())
				&& Objects.equals(x.getReplacePartCode(), p.getReplacePartCode())
				&& Objects.equals(x.getType(), p.getType())
				&& Objects.equals(x.getBrandName(), p.getBrandName())
				&& Objects.equals(x.getMakeName(), p.getMakeName());
	}

	private List<PartReplaceEntity> convertGetReplacePartsResp(String partCode, List<GetReplacePartsRespList> getReplacePartsRespListList) {
		List<PartReplaceEntity> partReplaceEntityList = new ArrayList<>();
		for (GetReplacePartsRespList replacePartsRespList : getReplacePartsRespListList) {
			List<GetReplacePartsRespListResult> getReplacePartsRespListResultList = replacePartsRespList.getResult();
			if (CollectionUtils.isEmpty(getReplacePartsRespListResultList)) {
				continue;
			}
			for (GetReplacePartsRespListResult getReplacePartsRespListResult : getReplacePartsRespListResultList) {
				if (SuperEPCconstant.GET_REPLACE_TYPE_QP.equals(replacePartsRespList.getType())) {
					partReplaceEntityList.add(convertReplacePartsRespListResultofGp(partCode, getReplacePartsRespListResult));
				} else if (SuperEPCconstant.GET_REPLACE_TYPE_BP.equals(replacePartsRespList.getType())) {
					partReplaceEntityList.add(convertReplacePartsRespListResultofBp(partCode, getReplacePartsRespListResult));
				} else {
					logger.error("unknow replace type=={}", replacePartsRespList.getType());
					continue;
				}
			}

		}
		return partReplaceEntityList;
	}

	private PartReplaceEntity convertReplacePartsRespListResultofBp(String partCode, GetReplacePartsRespListResult getReplacePartsRespListResult) {
		PartReplaceEntity partReplaceEntity = new PartReplaceEntity();
		partReplaceEntity.setPartCode(StringUtils.deleteWhitespace(partCode));
		partReplaceEntity.setType(SuperEPCconstant.GET_REPLACE_TYPE_BP);
		partReplaceEntity.setReplacePartCode(StringUtils.deleteWhitespace(getReplacePartsRespListResult.getKpsCode()));
		partReplaceEntity.setIsValid(1);
		partReplaceEntity.setCreateBy(SuperEPCconstant.CREATE_BY);

		partReplaceEntity.setPartBrand(getReplacePartsRespListResult.getPartBrand());
		partReplaceEntity.setLogoPath(getReplacePartsRespListResult.getLogoPath());
		partReplaceEntity.setRemark(getReplacePartsRespListResult.getMRemark());
		partReplaceEntity.setBpId(String.valueOf(getReplacePartsRespListResult.getBpId()));
		return partReplaceEntity;
	}

	private PartReplaceEntity convertReplacePartsRespListResultofGp(String partCode, GetReplacePartsRespListResult getReplacePartsRespListResult) {
		PartReplaceEntity partReplaceEntity = new PartReplaceEntity();
		partReplaceEntity.setPartCode(StringUtils.deleteWhitespace(partCode));
		partReplaceEntity.setType(SuperEPCconstant.GET_REPLACE_TYPE_QP);
		partReplaceEntity.setReplacePartCode(getReplacePartsRespListResult.getKpsCode());
		partReplaceEntity.setIsValid(1);
		partReplaceEntity.setCreateBy(SuperEPCconstant.CREATE_BY);

		partReplaceEntity.setGpId(getReplacePartsRespListResult.getGpId());
		partReplaceEntity.setMakeName(getReplacePartsRespListResult.getCOemAbbrebiation());
		partReplaceEntity.setBrandName(getReplacePartsRespListResult.getCOemBrand());
		partReplaceEntity.setEpcNo(getReplacePartsRespListResult.getEpcNo());

		return partReplaceEntity;
	}

}
